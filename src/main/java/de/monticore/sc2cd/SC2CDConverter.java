package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;
import de.monticore.generating.GeneratorSetup;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class SC2CDConverter {

  /**
   * Convert a SC to a CD
   * @param astscArtifact the SC
   * @return the CD
   */
  public ASTCDCompilationUnit doConvert(ASTSCArtifact astscArtifact, GeneratorSetup config) {
    CD4C.init(config);
    CD4C.getInstance().setEmptyBodyTemplate("de.monticore.sc2cd.gen.EmptyMethod");

    // Phase 1: Work on states
    SC2CDStateVisitor phase1Visitor = new SC2CDStateVisitor();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);
    traverser.add4UMLStatecharts(phase1Visitor);

    CD4CodeMill.init();
    traverser.handle(astscArtifact);

    // Phase 2: Work with transitions
    SC2CDTransitionVisitor phase2Visitor = new SC2CDTransitionVisitor(phase1Visitor.getScClass(),
                                                                      phase1Visitor.getStateToClassMap(),
                                                                      phase1Visitor.getStateSuperClass());
    traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase2Visitor);
    traverser.add4UMLStatecharts(phase2Visitor);
    traverser.add4SCTransitions4Code(phase2Visitor);
    traverser.handle(astscArtifact);

    // voila
    return phase1Visitor.getCdCompilationUnit();
  }
}
