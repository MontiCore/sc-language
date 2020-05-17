/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTSCTransition;
import de.se_rwth.commons.logging.Log;

/**
 * TODO: Write me!
 *
 *
 */
public class TransitionSourceAndTargetExists implements StatechartASTSCTransitionCoCo {
  
  /**
   * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCTransitionCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCTransition)
   */
  @Override
  public void check(ASTSCTransition node) {
    if(node.getTarget() == null || node.getSource() == null){
      Log.error("Source or Target not a State");
    }
  }
}
