/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTStatechart;
import de.se_rwth.commons.logging.Log;

/**
 * TODO: Write me!
 *
 *
 */
public class AtLeastOneInitialStateInFinalStatechartChecker implements StatechartASTStatechartCoCo {
  
  /**
   * @see de.monticore.umlsc.statechart._cocos.StatechartASTStatechartCoCo#check(de.monticore.umlsc.statechart._ast.ASTStatechart)
   */
  @Override
  public void check(ASTStatechart node) {
    if(node.isPresentCompleteness() && node.getCompleteness().isComplete()) {
      for(ASTSCState s : node.getSCStateList()) {
        if(s!=null) {
          return;
        }
      }
      Log.error("no initial State in complete Statechart", node.get_SourcePositionStart());
    }
  }
  
}
