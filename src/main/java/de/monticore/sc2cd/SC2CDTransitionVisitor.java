package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cd4code.prettyprint.CD4CodeFullPrettyPrinter;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor2;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.types.mcbasictypes._ast.ASTMCReturnType;
import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor2;
import de.se_rwth.commons.Splitters;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SC2CDTransitionVisitor
        implements UMLStatechartsVisitor2, SCTransitions4CodeVisitor2, SCBasisVisitor2 {

  /**
   * The main class
   */
  protected final ASTCDClass scClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> stateToClassMap;

  protected final ASTMCReturnType voidReturnType;
  protected final CD4CodeFullPrettyPrinter cd4CodeFullPrettyPrinter;

  protected final ASTCDClass stateSuperClass;
  protected final CD4C cd4C;


  protected final List<String> stimuli = new ArrayList<>();


  public SC2CDTransitionVisitor(ASTCDClass scClass,
                                Map<String, ASTCDClass> stateToClassMap,
                                ASTCDClass stateSuperClass) {
    this.scClass = scClass;
    this.stateToClassMap = stateToClassMap;
    this.stateSuperClass = stateSuperClass;
    this.cd4C = CD4C.getInstance();

    this.cd4CodeFullPrettyPrinter = new CD4CodeFullPrettyPrinter(new IndentPrinter());
    this.voidReturnType = CDBasisMill.mCReturnTypeBuilder().setMCVoidType(CDBasisMill.mCVoidTypeBuilder().build()).build();
  }


  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }

  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
            .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }


  protected ASTSCTransition transition;
  protected ASTTransitionBody transitionBody;

  @Override
  public void visit(ASTSCTransition node) {
    this.transition = node;
  }

  @Override
  public void visit(ASTTransitionBody node) {
    this.transitionBody = node;
  }

  @Override
  public void endVisit(ASTSCTransition node) {
    this.transition = null;
  }

  @Override
  public void endVisit(ASTTransitionBody node) {
    this.transitionBody = null;
  }

  @Override
  public void visit(ASTSCUMLEvent event) {
    String stimulus = event.getName().getQName();
    if (!stimuli.contains(stimulus)) {
      // Add stimulus method to the Class
      cd4C.addMethod(scClass, "de.monticore.sc2cd.StateStimulusMethod", stimulus);

      // Add handleStimulus(Class k) method to the StateClass
      stateSuperClass.addCDMember(CD4CodeMill.cDMethodBuilder().setModifier(CDBasisMill.modifierBuilder().build())
                                          .setMCReturnType(voidReturnType)
                                          .setName("handle" + StringUtils.capitalize(stimulus)).addCDParameter(
                      CD4CodeMill.cDParameterBuilder().setName("k").setMCType(qualifiedType(scClass.getName())).build())
                                          .build());
      this.stimuli.add(stimulus);
    }

    if (transition == null || transitionBody == null) return;

    // Add handleStimulus(Class k) method to the source-state StateClass impl
    ASTCDClass stateImplClass = this.stateToClassMap.get(this.transition.getSourceName());
    if (stateImplClass == null) {
      throw new IllegalStateException("No source state " + this.transition.getSourceName() + " found!");
    }
    if (stateImplClass.getCDMethodList().stream()
            .anyMatch(x -> x.getName().equals("handle" + StringUtils.capitalize(stimulus)))) {
      // This might occur due to stimuli with arguments
      throw new IllegalStateException("Duplicate transition " + stimulus + " in " + this.transition.getSourceName() + " found!");
    }

    // Print the action using the UMLStatechartsFullPrettyPrinter
    String action = "/* no action */";
    if (transitionBody.isPresentTransitionAction() && transitionBody.getTransitionAction()
            .isPresentMCBlockStatement()) {
      IndentPrinter printer = new IndentPrinter();
      new UMLStatechartsFullPrettyPrinter(printer).getTraverser().handle(transitionBody.getTransitionAction());
      action = printer.getContent();
    }
    // Print the precondition as an expression, too
    String precondition = "true"; // by default true holds
    if (transitionBody.isPresentPre()) {
      precondition = this.cd4CodeFullPrettyPrinter.prettyprint(transitionBody.getPre());
    }
    // Finally, add the method
    cd4C.addMethod(stateImplClass, "de.monticore.sc2cd.StateClassHandleStimulus", stimulus, scClass.getName(),
                   transition.getTargetName(), action, precondition);
  }


}
