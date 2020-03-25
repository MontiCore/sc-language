/* (c) https://github.com/MontiCore/monticore */
package tool;

import de.monticore.generating.GeneratorEngine;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.io.paths.ModelPath;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.symboltable.*;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCDDefinition;
import de.monticore.umlcd4a.prettyprint.CDPrettyPrinterConcreteVisitor;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._cocos.AtLeastOneInitialStateInFinalStatechartChecker;
import de.monticore.umlsc.statechart._cocos.StatechartCoCoChecker;
import de.monticore.umlsc.statechart._cocos.TransitionSourceAndTargetExists;
import de.monticore.umlsc.statechart._cocos.UniqueStateNames;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._cocos.StatechartWithJavaCoCoChecker;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaSymbolTableCreator;
import de.se_rwth.commons.logging.Log;
import mc.tf.AddState;
import mc.tf.State2CD;
import org.antlr.v4.runtime.RecognitionException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by
 *
 */
public class SCTool {
  
  
  /**
   * Use the single argument for specifying the single input automaton file.
   *
   * @param args
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      Log.error("Please specify only one single path to the input model.");
      return;
    }
    String model = args[0];
    
    // setup the language infrastructure
    final StatechartWithJavaLanguage lang = new StatechartWithJavaLanguage();
    
    // parse the model and create the AST representation
    final ASTSCArtifact ast = parse(model);
    Log.info(model + " parsed successfully!", SCTool.class.getName());
    
    // setup the symbol table
    Scope modelTopScope = createSymbolTable(lang, ast);
    // can be used for resolving things in the model
    Optional<Symbol> aSymbol = modelTopScope.resolve("Ping", SCStateSymbol.KIND);
    if (aSymbol.isPresent()) {
      Log.info("Resolved state symbol \"Ping\"; FQN = " + aSymbol.get().toString(),
          SCTool.class.getName());
    }
    
    // execute default context conditions
    runDefaultCoCos(ast);
    
    // execute a custom set of context conditions
    StatechartWithJavaCoCoChecker customCoCos = new StatechartWithJavaCoCoChecker();
    customCoCos.addCoCo(new TransitionSourceAndTargetExists());
    customCoCos.checkAll(ast);



    // execute a pretty printer
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    Log.info("Pretty printing the parsed statechart into console:", SCTool.class.getName());
    System.out.println(pp.prettyPrint(ast));
    
    // ## transform ##
    
    // apply transformations
    AddState addState = new AddState(ast);
    
    if(addState.doPatternMatching()) {
      addState.doReplacement();
    }
  
    // execute a pretty printer
    pp.getPrinter().clearBuffer();
    Log.info("Pretty printing the transformed statechart into console:", SCTool.class.getName());
    System.out.println(pp.prettyPrint(ast));
    
    
    // translate to cd
    State2CD state2CD = new State2CD(ast);
  
    state2CD.doAll();
    
    ASTCDDefinition cd = state2CD.get_$CD();
  
    CDPrettyPrinterConcreteVisitor cdPrettyPrinter = new CDPrettyPrinterConcreteVisitor(new IndentPrinter());
    // execute a pretty printer
    Log.info("Pretty printing the class diagram into console:", SCTool.class.getName());
    cd.accept(cdPrettyPrinter);
    System.out.println(cdPrettyPrinter.getPrinter().getContent());
    
    
    
    //  ##  generate ##
    
    // configure target folder
    String outputDir = "target/out";
    File out = Paths.get(outputDir).toFile();
  
    // handle Template replacements and global values
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    glex.setGlobalValue("classname", "Example");
    
    
    GeneratorSetup setup = new GeneratorSetup();
    setup.setOutputDirectory(out);
    setup.setGlex(glex);
    
    // start generator
    Path generatedFile = Paths.get("Example" + ".java");
    GeneratorEngine e = new GeneratorEngine(setup);
    e.generate("my.OwnTemplate", generatedFile, cd);
    
  }
  
  /**
   * Parse the model contained in the specified file.
   *
   * @param model - file to parse
   * @return
   */
  public static ASTSCArtifact parse(String model) {
    try {
      StatechartWithJavaParser parser = new StatechartWithJavaParser() ;
      Optional<ASTSCArtifact> optAutomaton = parser.parse(model);
      
      if (!parser.hasErrors() && optAutomaton.isPresent()) {
        return optAutomaton.get();
      }
      Log.error("Model could not be parsed.");
    }
    catch (RecognitionException | IOException e) {
      Log.error("Failed to parse " + model, e);
    }
    return null;
  }
  
  /**
   * Create the symbol table from the parsed AST.
   *
   * @param lang
   * @param ast
   * @return
   */
  public static Scope createSymbolTable(StatechartWithJavaLanguage lang, ASTSCArtifact ast) {
    final ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
    resolverConfiguration.addDefaultFilters(lang.getResolvingFilters());
    
    GlobalScope globalScope = new GlobalScope(new ModelPath(), lang, resolverConfiguration);
    
    StatechartWithJavaSymbolTableCreator symbolTable = new StatechartWithJavaSymbolTableCreator(resolverConfiguration,globalScope);
    return symbolTable.createFromAST(ast);
  }
  
  /**
   * Run the default context conditions
   *
   * @param ast
   */
  public static void runDefaultCoCos(ASTSCArtifact ast) {
    StatechartWithJavaCoCoChecker checker = new StatechartWithJavaCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialStateInFinalStatechartChecker());
    checker.addCoCo(new UniqueStateNames());
    checker.checkAll(ast);
  }
  
  
  
}
