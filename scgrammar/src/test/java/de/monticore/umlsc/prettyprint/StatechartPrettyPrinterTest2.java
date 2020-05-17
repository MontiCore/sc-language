/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.*;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Log;

import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StatechartPrettyPrinterTest2 {

  @BeforeClass
  public static void init() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Before
  public void setUp() {
    Log.getFindings().clear();
  }


  @Test
  public void testASTInvariant() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTInvariant> ast = parser.parse_StringInvariant("[ n+b ]");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTInvariant invariant = ast.get();
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringInvariant(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(invariant.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCMethodCall() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCMethodCall> ast = parser.parse_StringSCMethodCall("name.some.other ( a , b )");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCMethodCall scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCMethodCall(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCReturnStatement() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCReturnStatement> ast = parser.parse_StringSCReturnStatement("return ( a+ b.sid)");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCReturnStatement scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCReturnStatement(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCCode() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCCode> ast = parser.parse_StringSCCode("code { block; }");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCCode scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCCode(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArguments() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArguments> ast = parser.parse_StringSCArguments("( ab, b+d, abkd.skid )");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArguments scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCArguments(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCInternTransition() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCInternTransition> ast = parser.parse_StringSCInternTransition("<< name = \"stereo\" >> -> / { block.statement; } ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCInternTransition scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCInternTransition(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCTransitionBody() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCTransitionBody> ast = parser.parse_StringSCTransitionBody("[ inv ] / { block.statement; } [ endiv ] ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCTransitionBody scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCTransitionBody(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCTransition() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCTransition> ast = parser.parse_StringSCTransition("<< name = \"somestring\"  >>  Start -> End [ inv ] /");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCTransition scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCTransition(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCStereotype() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCStereotype> ast = parser.parse_StringSCStereotype("<< name = \"somestring\" , name2 = \"other\" >>");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCStereotype scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCStereotype(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCStereoValue() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCStereoValue> ast = parser.parse_StringSCStereoValue("name = \"abcd\"");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCStereoValue scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCStereoValue(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCAction() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCAction> ast = parser.parse_StringSCAction("[ pre ] / { block; } [ post ]");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCAction scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCAction(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCModifier() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCModifier> ast = parser.parse_StringSCModifier("<< name = \"stereo\" >> final");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCModifier scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringSCModifier(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTCompleteness() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTCompleteness> ast = parser.parse_StringCompleteness(" (...) ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTCompleteness scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parse_StringCompleteness(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }


  @Test
  public void testASTSCArtifact1() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/Banking.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }


  @Test
  public void testASTSCArtifact2() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleState.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact3() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransition.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact4() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransitionInternal.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact5() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScTransitionExt.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact6() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/Simple.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    
    String output = StatechartPrettyPrinter.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

}
