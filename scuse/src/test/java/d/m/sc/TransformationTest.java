/* (c) https://github.com/MontiCore/monticore */
package d.m.sc;
import de.monticore.cd.prettyprint.CDPrettyPrinterDelegator;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import mc.tf.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransformationTest {

  String input = "src/test/models/Banking.sc";

  @Test
  public void testRenameSC() throws IOException {
    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    RenameSC rename = new RenameSC(ast.get());

    System.out.println(ast.get().getStatechart().getName());

    assertTrue(rename.doPatternMatching());

    rename.doReplacement();

    assertTrue(ast.get().getStatechart().isPresentName());
    assertEquals("Auftrag2", ast.get().getStatechart().getName());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));
  }

  @Test
  public void testSCToCD() throws IOException {
    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    State2CD state2CD = new State2CD(ast.get());

    System.out.println(ast.get().getStatechart().getName());

    assertTrue(state2CD.doPatternMatching());

    state2CD.doReplacement();

    assertEquals("Banking", state2CD.get_$CD().getName());

    System.out.println(new CDPrettyPrinterDelegator(new IndentPrinter()).prettyprint(state2CD.get_$CD()));

  }

  @Test
  public void testAddState() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    AddState tf = new AddState(ast.get());

    System.out.println("#States: " + ast.get().getStatechart().getSCStateList().size());

    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    System.out.println("#States: " + ast.get().getStatechart().getSCStateList().size());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }

  @Test
  public void testRemoveTransition() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    assertEquals(7, ast.get().getStatechart().getSCTransitionList().size());
    System.out.println("#Transitions: " + ast.get().getStatechart().getSCTransitionList().size());

    RemoveTransition rename = new RemoveTransition(ast.get());

    assertTrue(rename.doPatternMatching());

    rename.doReplacement();

    assertEquals(6, ast.get().getStatechart().getSCTransitionList().size());
    System.out.println("#Transitions: " + ast.get().getStatechart().getSCTransitionList().size());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));
  }

  @Test
  public void testChangeInitial() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    ChangeInitial tf = new ChangeInitial(ast.get());

    assertEquals("Offer", ast.get().getStatechart().getInitialState().getName());

    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    assertEquals("Production", ast.get().getStatechart().getInitialState().getName());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }
  @Test
  public void testChangeTransition() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    ChangeTransition tf = new ChangeTransition(ast.get());


    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    assertEquals("Error", tf.get_$T().getTargetName());

    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }
  @Test
  public void testAddTransition() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    AddTransition tf = new AddTransition(ast.get());

    System.out.println(ast.get().getStatechart().getInitialState().getName());

    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }
  @Test
  public void testIntroduceErrorState() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse(input);

    assertTrue(ast.isPresent());

    AddErrorState tf = new AddErrorState(ast.get());

    assertEquals(6, ast.get().getStatechart().getSCStateList().size());
    System.out.println("#States: " + ast.get().getStatechart().getSCStateList().size());

    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    AddTransition tf_trans = new AddTransition(ast.get());
    while(tf_trans.doPatternMatching()) {
      tf_trans.doReplacement();
      tf_trans = new AddTransition(ast.get());
    }

    assertEquals(7, ast.get().getStatechart().getSCStateList().size());
    System.out.println("#States: " + ast.get().getStatechart().getSCStateList().size());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }

  @Test
  public void testDo() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse("src/test/models/Action.sc");

    assertTrue(ast.isPresent());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

    DoAction tf = new DoAction(ast.get());

//    assertTrue(ast.get().getStatechart().getSCStateList().get(0).doActionIsPresent());

    assertTrue(tf.doPatternMatching());

    tf.doReplacement();

    assertEquals(1, ast.get().getStatechart().getSCTransitionList().size());

    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }



  @Test
  public void testAddFinal() throws IOException {

    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse("src/test/models/FinalState.sc");

    assertTrue(ast.isPresent());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

    AddFinal tf = new AddFinal(ast.get());


    assertTrue(tf.doPatternMatching());

    tf.doReplacement();
    
    tf = new AddFinal(ast.get());
    
    assertFalse(tf.doPatternMatching());
    
    


    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));

  }
  
  @Test
  public void testDeleteStereotype() throws IOException {
    
    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse("src/test/models/Stereotype.sc");
    
    assertTrue(ast.isPresent());
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));
  
    DeleteStereotype tf = new DeleteStereotype(ast.get());
    
    assertTrue(tf.doPatternMatching());
    
    tf.doReplacement();
    
    System.out.println(new StatechartPrettyPrinterDelegator().prettyPrint(ast.get()));
    
  }


}
