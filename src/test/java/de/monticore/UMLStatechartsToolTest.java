/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsGlobalScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UMLStatechartsToolTest {
  
  String resourcesDir = "src/test/resources/";
  String outputDir = "target/tooltest/";
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.initPlusLog();
    UMLStatechartsMill.init();
  }
  
  @Before
  public void setUp() throws Exception {
    Log.clearFindings();
    IUMLStatechartsGlobalScope gs = UMLStatechartsMill.globalScope();
    gs.clear();
    TypeSymbol stringType = UMLStatechartsMill
        .typeSymbolBuilder()
        .setEnclosingScope(gs)
        .setName("String")
        .build();
    UMLStatechartsMill.globalScope().add(stringType);
    IUMLStatechartsArtifactScope as = UMLStatechartsMill.artifactScope();
    as.setEnclosingScope(gs);
    as.setPackageName("a.b");
    TypeSymbol personType = UMLStatechartsMill
        .typeSymbolBuilder()
        .setEnclosingScope(as)
        .setName("Person")
        .build();
    as.add(personType);
  }
  
  @Test
  public void testUMLStatecharts(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc"
    });
    assertEquals("Door.sc was not processed successfully", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Door.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsConverter(){
    new StatechartsCLI().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-gen", "target/gentest"
    });
    assertEquals("Converting to SD of Door.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsStore(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-s", outputDir + "door/Door.scsym"
    });
    assertEquals("Storing symbol table of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore2(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Car.sc",
        "-s", outputDir + "car/Car.scsym"
    });
    assertEquals("Storing symbol table of Car.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore3(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-s", outputDir + "testsc/Test.scsym"
    });
    assertEquals("Storing symbol table of Test.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsStore4(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "valid/Test2.sc",
        "-s", outputDir + "testsc2/Test2.scsym"
    });
    assertEquals("Storing symbol table of Test2.sc was not successful", Log.getErrorCount(), 0);
  }


  @Test
  public void testUMLStatechartsPP3(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-pp", outputDir + "testsc/Test.sc"
    });
    assertEquals("Pretty printing Test.sc was not successful", Log.getErrorCount(), 0);
  }
  
  
  @Test
  public void testUMLStatechartsReportDoor() throws IOException {
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-r", outputDir + "door"
    });
    assertEquals("Reporting for Door.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "door/branchingDegree.txt"));
    assertEquals("Branching Degree of Opened", Integer.valueOf(1), branchingDegree.getOrDefault("Opened", -1));
    assertEquals("Branching Degree of Closed", Integer.valueOf(2), branchingDegree.getOrDefault("Closed", -1));
    assertEquals("Branching Degree of Locked", Integer.valueOf(1), branchingDegree.getOrDefault("Locked", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "door/reachability.txt"));
    assertEquals("Reachability of Closed", "reachable", reachability.get("Closed"));
    assertEquals("Reachability of Opened", "reachable", reachability.get("Opened"));
    assertEquals("Reachability of Locked", "reachable", reachability.get("Locked"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "door/stateNames.txt"));
    for (String state : Arrays.asList("Closed", "Opened", "Locked"))
      assertTrue("StateNames " + state, stateNames.contains(state));
  }
  
  @Test
  public void testUMLStatechartsReportCar() throws IOException{
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Car.sc",
        "-r", outputDir + "/car"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "car/branchingDegree.txt"));
    assertEquals("Branching Degree of EngineOff", Integer.valueOf(1), branchingDegree.getOrDefault("EngineOff", -1));
    assertEquals("Branching Degree of EngineRunning", Integer.valueOf(1), branchingDegree.getOrDefault("EngineRunning", -1));
    assertEquals("Branching Degree of Parking", Integer.valueOf(0), branchingDegree.getOrDefault("Parking", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "car/reachability.txt"));
    assertEquals("Reachability of EngineOff", "reachable", reachability.get("EngineOff"));
    assertEquals("Reachability of EngineRunning", "reachable", reachability.get("EngineRunning"));
    assertEquals("Reachability of Driving", "unreachable", reachability.get("Driving"));
    assertEquals("Reachability of Parking", "reachable", reachability.get("Parking"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "car/stateNames.txt"));
    for (String state : Arrays.asList("EngineOff", "EngineRunning", "Driving", "Parking"))
      assertTrue("StateNames " + state, stateNames.contains(state));

  }

  /**
   * Loads the generated branching degree report into a map
   * Each state name is mapped to its branching degree
   * @param file the path to the reports text file
   * @return a map with the branching degree report
   */
  private Map<String, Integer> loadBranchingDegree(File file)
      throws IOException {
    assertTrue("branchingDegree report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
        .map(l -> l.split(":", 2))
        .collect(Collectors.toMap(e -> e[0], e -> Integer.parseInt(e[1].trim())));
  }

  /**
   * Loads the generated reachability report into a map
   * Each state name is mapped to either reachable or unreachable
   * @param file the path to the reports text file
   * @return a map with the reachability report
   */
  private Map<String, String> loadReachability(File file)
      throws IOException {
    assertTrue("reachability report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
        .map(l -> l.split(":", 2)) // Split (un)reachable -> S1,S2
        .map(e -> splitCommaSeparatedStream(e[1], e[0].trim()))
        .flatMap(Stream::unordered)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Helper method which splits a comma separated set of keys
   * @param keys comma separated keys
   * @param value the value
   * @return a stream of map entries of each key with the value
   */
  private Stream<Map.Entry<String, String>> splitCommaSeparatedStream(String keys, String value){
    return Stream.of(keys.split(","))
        .map(String::trim)
        .map(k -> new AbstractMap.SimpleImmutableEntry<>(k, value));
  }

  /**
   * Loads the generated state names report into a set
   * @param file the path to the reports text file
   * @return a set with state names report
   */
  private Set<String> loadStateNames(File file)
      throws IOException {
    assertTrue("stateNames report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
        .map(l -> Stream.of(l.split(",")))
        .flatMap(Stream::unordered)
        .map(String::trim)
        .collect(Collectors.toSet());
  }

  @Test
  public void testHelp(){
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    new StatechartsCLI().run(new String[]{    "-h" });
    assertEquals(Log.getErrorCount(), 0);
    String result = out.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
    assertEquals( "usage: UMLSCTool\n"
                          + " -gen,--generate <file>     Prints the state pattern CD-AST to stdout or the\n"
                          + "                            generated java classes to the specified folder\n"
                          + "                            (optional)\n"
                          + " -h,--help                  Prints this help dialog\n"
                          + " -i,--input <file>          Reads the source file (mandatory) and parses the\n"
                          + "                            contents as a statechart\n"
                          + " -path <arg>                Sets the artifact path for imported symbols, space\n"
                          + "                            separated.\n"
                          + " -pp,--prettyprint <file>   Prints the Statechart-AST to stdout or the specified\n"
                          + "                            file (optional)\n"
                          + " -r,--report <dir>          Prints reports of the statechart artifact to the\n"
                          + "                            specified directory. Available reports:\n"
                          + "                            reachable states, branching degree, and state names\n"
                          + " -s,--symboltable <file>    Serialized the Symbol table of the given Statechart\n"
            , result );
  }
}