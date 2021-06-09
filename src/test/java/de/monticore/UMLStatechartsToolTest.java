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
import java.io.PrintStream;

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
  public void testUMLStatechartsReport(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-r", outputDir + "door"
    });
    assertEquals("Reporting for Door.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File(outputDir + "door/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File(outputDir + "door/reachability.txt").exists());
    assertTrue("stateNames report missing",new File(outputDir + "door/stateNames.txt").exists());
  }
  
  @Test
  public void testUMLStatechartsReportCar(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/uml/Car.sc",
        "-r", outputDir + "/car"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File(outputDir + "car/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File(outputDir + "car/reachability.txt").exists());
    assertTrue("stateNames report missing",new File(outputDir + "car/stateNames.txt").exists());
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