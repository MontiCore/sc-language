/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.io.paths.ModelPath;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scbasis.BranchingDegreeCalculator;
import de.monticore.scbasis.InitialStateCollector;
import de.monticore.scbasis.ReachableStateCollector;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scevents._symboltable.SCEventsSTCompleter;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.types.DeriveSymTypeOfUMLStatecharts;
import de.monticore.types.SynthesizeSymType;
import de.monticore.types.check.TypeCheck;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsScopesGenitorDelegator;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsSymbols2Json;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.monticore.utils.Names;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StatechartsCLI {

  /**
   * Main method that is called from command line and runs the UML Statechart tool.
   *
   * @param args The input parameters for configuring the UML Statechart tool.
   */
  public static void main(String[] args) {
    StatechartsCLI cli = new StatechartsCLI();
    // initialize logging with standard logging
    Log.init();
    UMLStatechartsMill.init();
    cli.run(args);
  
  }
  
  public void run(String[] args){
    Options options = initOptions();
  
    try {
      // create CLI parser and parse input options from command line
      CommandLineParser cliparser = new DefaultParser();
      CommandLine cmd = cliparser.parse(options, args);
    
      // help: when --help
      if (cmd.hasOption("h")) {
        printHelp(options);
        // do not continue, when help is printed
        return;
      }
    
      // if -i input is missing: also print help and stop
      if (!cmd.hasOption("i")) {
        printHelp(options);
        // do not continue, when help is printed
        return;
      }
  
      // we need the global scope for symbols and cocos
      ModelPath modelPath = new ModelPath(Paths.get(""));
      if (cmd.hasOption("path")) {
        modelPath = new ModelPath(Arrays.stream(cmd.getOptionValues("path")).map(x -> Paths.get(x)).collect(Collectors.toList()));
      }
      UMLStatechartsMill.globalScope().setModelPath(modelPath);
      BasicSymbolsMill.initializePrimitives();
    
      // parse input file, which is now available
      // (only returns if successful)
      ASTSCArtifact scartifact = parseFile(cmd.getOptionValue("i"));
  
      IUMLStatechartsArtifactScope scope = createSymbolTable(scartifact);
      String fileName = Paths.get(cmd.getOptionValue("i")).getFileName().toString();
      scope.setName(fileName.substring(0, fileName.lastIndexOf('.')));
  
      if (cmd.hasOption("s")) {
        String path = cmd.getOptionValue("s", StringUtils.EMPTY);
        storeSymbols(scope, path);
      }
    
      // -option pretty print
      if (cmd.hasOption("pp")) {
        String path = cmd.getOptionValue("pp", StringUtils.EMPTY);
        prettyPrint(scartifact, path);
      }
    
      // -option reports
      if (cmd.hasOption("r")) {
        String path = cmd.getOptionValue("r", StringUtils.EMPTY);
        report(scartifact, path);
      }
    
    
    } catch (ParseException e) {
      // ann unexpected error from the apache CLI parser:
      Log.error("0xA5C01 Could not process CLI parameters: " + e.getMessage());
    }
  }
  
  public void storeSymbols(IUMLStatechartsArtifactScope scope, String path) {
    UMLStatechartsSymbols2Json deser = new UMLStatechartsSymbols2Json();
    Path f = Paths.get(path)
        .resolve(Paths.get(Names.getPathFromPackage(scope.getPackageName())))
        .resolve(scope.getName()+".scsym");
    deser.store(scope, f.toString());
  }
  
  /**
   * Creates the symbol table from the parsed AST.
   *
   * @param ast The top statechart model element.
   * @return The artifact scope derived from the parsed AST
   */
  public IUMLStatechartsArtifactScope createSymbolTable(ASTSCArtifact ast) {
  
    // create scope and symbol skeleton
    UMLStatechartsScopesGenitorDelegator genitor = UMLStatechartsMill.scopesGenitorDelegator();
    IUMLStatechartsArtifactScope symTab = genitor.createFromAST(ast);
  
    // complete symbols including type check
    UMLStatechartsTraverser completer = UMLStatechartsMill.traverser();
    TypeCheck typeCheck = new TypeCheck(new SynthesizeSymType(),new DeriveSymTypeOfUMLStatecharts());
    completer.add4SCEvents(new SCEventsSTCompleter(typeCheck));  
    ast.accept(completer);

    return symTab;
  }
  
  /**
   * Creates reports for the Statechart-AST to stdout or a specified file.
   *
   * @param scartifact The Statechart-AST for which the reports are created
   * @param path The target path of the directory for the report artifacts. If
   *          empty, the contents are printed to stdout instead
   */
  public void report(ASTSCArtifact scartifact, String path) {
    // calculate and print reports
    String reachable = reportReachableStates(scartifact);
    print(reachable, path, REPORT_REACHABILITY);

    String branching = reportBranchingDegree(scartifact);
    print(branching, path, REPORT_BRANCHING_DEGREE);

    String stateNames = reportStateNames(scartifact);
    print(stateNames, path, REPORT_STATE_NAMES);
  }

  // names of the reports:
  public static final String REPORT_REACHABILITY = "reachability.txt";
  public static final String REPORT_BRANCHING_DEGREE = "branchingDegree.txt";
  public static final String REPORT_STATE_NAMES = "stateNames.txt";

  public String reportReachableStates(ASTSCArtifact ast) {
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    // collect all states
    StateCollector stateCollector = new StateCollector();
    traverser.add4SCBasis(stateCollector);
    ast.accept(traverser);
    Set<String> statesToBeChecked = stateCollector.getStates()
        .stream().map(e -> e.getName()).collect(Collectors.toSet());
    
    // collect all initial states
    InitialStateCollector initialStateCollector = new InitialStateCollector();
    traverser.add4SCBasis(initialStateCollector);
    ast.accept(traverser);
    Set<String> reachableStates = initialStateCollector.getStates();
    
    // calculate reachable states
    Set<String> currentlyChecked = new HashSet<>(reachableStates);
    statesToBeChecked.removeAll(reachableStates);
    while (!currentlyChecked.isEmpty()) {
      // While the open list is not empty, check which states can be reached from it
      String from = currentlyChecked.iterator().next();
      currentlyChecked.remove(from);
      ReachableStateCollector reachableStateCollector = new ReachableStateCollector(from);
      traverser.add4SCBasis(reachableStateCollector);
      ast.accept(traverser);
      for (String to : reachableStateCollector.getReachableStates()) {
        if (!reachableStates.contains(to)) {
          // In case a new reachable state is found, add it to the open list
          // and mark it as reachable
          reachableStates.add(to);
          currentlyChecked.add(to);
          statesToBeChecked.remove(to);
        }
      }
    }
    return "reachable: " + String.join(",", reachableStates) + System.lineSeparator() 
       + "unreachable: " + String.join(",", statesToBeChecked);
  }

  public String reportBranchingDegree(ASTSCArtifact ast) {
    BranchingDegreeCalculator branchingDegreeCalculator = new BranchingDegreeCalculator();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(branchingDegreeCalculator);
    ast.accept(traverser);
    return branchingDegreeCalculator.getBranchingDegrees().entrySet().stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining(System.lineSeparator()));
  }

  public String reportStateNames(ASTSCArtifact ast) {
    StateCollector stateCollectorVisitor = new StateCollector();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(stateCollectorVisitor);
    ast.accept(traverser);
    return String.join(", ", stateCollectorVisitor.getStates()
        .stream().map(e -> e.getName()).collect( Collectors.toSet()));
  }
  
  /**
   * Formats and prints the help information including parameters an options.
   *
   * @param options The input parameters and options.
   */
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.setWidth(80);
    formatter.printHelp("UMLSCTool", options);
  }
  
  /**
   * Parses the contents of a given file as a Statechart.
   *
   * @param path The path to the Statechart-file as String
   */
  public ASTSCArtifact parseFile(String path) {
    Optional<ASTSCArtifact> sc = Optional.empty();
    try {
      Path model = Paths.get(path);
      UMLStatechartsParser parser = new UMLStatechartsParser();
      sc = parser.parse(model.toString());
    }
    catch (IOException | NullPointerException e) {
      Log.error("0xA5C02 Input file " + path + " not found.");
    }
    return sc.get();
  }
  
  /**
   * Prints the contents of the SC-AST to stdout or a specified file.
   *
   * @param scartifact The SC-AST to be pretty printed
   * @param file The target file name for printing the SC artifact. If empty,
   *          the content is printed to stdout instead
   */
  public void prettyPrint(ASTSCArtifact scartifact, String file) {
    // pretty print AST
    UMLStatechartsFullPrettyPrinter prettyPrinterDelegator
        = new UMLStatechartsFullPrettyPrinter();
    String prettyOutput = prettyPrinterDelegator.prettyprint(scartifact);
    print(prettyOutput, file);
  }

  public void print(String content, String path, String file) {
    print(content, path.isEmpty()?path : path + "/"+ file);
  }

  /**
   * Prints the given content to a target file (if specified) or to stdout (if
   * the file is Optional.empty()).
   *
   * @param content The String to be printed
   * @param path The target path to the file for printing the content. If empty,
   *          the content is printed to stdout instead
   */
  public void print(String content, String path) {
    // print to stdout or file
    if (path.isEmpty()) {
      System.out.println(content);
    } else {
      File f = new File(path);
      // create directories (logs error otherwise)
      f.getAbsoluteFile().getParentFile().mkdirs();

      FileWriter writer;
      try {
        writer = new FileWriter(f);
        writer.write(content);
        writer.close();
      } catch (IOException e) {
        Log.error("0xA7105 Could not write to file " + f.getAbsolutePath());
      }
    }
  }



  /**
   * Initializes the available CLI options for the Statechart tool.
   *
   * @return The CLI options with arguments.
   */
  protected Options initOptions() {
    Options options = new Options();
    
    // help dialog
    options.addOption(Option.builder("h")
        .longOpt("help")
        .desc("Prints this help dialog")
        .build());
    
    // parse input file
    options.addOption(Option.builder("i")
        .longOpt("input")
        .argName("file")
        .hasArg()
        .desc("Reads the source file (mandatory) and parses the contents as a statechart")
        .build());
    
    // pretty print SC
    options.addOption(Option.builder("pp")
        .longOpt("prettyprint")
        .argName("file")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("Prints the Statechart-AST to stdout or the specified file (optional)")
        .build());
  
    // pretty print SC
    options.addOption(Option.builder("s")
        .longOpt("symboltable")
        .argName("file")
        .hasArg()
        .desc("Serialized the Symbol table of the given Statechart")
        .build());
    
    // reports about the SC
    options.addOption(Option.builder("r")
        .longOpt("report")
        .argName("dir")
        .hasArg(true)
        .desc("Prints reports of the statechart artifact to the specified directory. Available reports:"
            + System.lineSeparator() + "reachable states, branching degree, and state names")
        .build());
  
    // model paths
    options.addOption(Option.builder("path")
        .hasArgs()
        .desc("Sets the artifact path for imported symbols, space separated.")
        .build());
    
    return options;
  }
}