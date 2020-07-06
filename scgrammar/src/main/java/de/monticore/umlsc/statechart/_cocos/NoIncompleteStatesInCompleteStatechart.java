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
public class NoIncompleteStatesInCompleteStatechart implements StatechartASTStatechartCoCo {
  
  /**
   * @see de.monticore.umlsc.statechart._cocos.StatechartASTStatechartCoCo#check(de.monticore.umlsc.statechart._ast.ASTStatechart)
   */
  @Override
  public void check(ASTStatechart node) {
    if(node.isPresentCompleteness() && node.getCompleteness().isComplete()) {
      StatechartCoCoChecker c = new StatechartCoCoChecker();
      c.addCoCo(new NoIncompleteStates());
      c.handle(node);
    }
    
  }
  private static class NoIncompleteStates implements StatechartASTSCStateCoCo {

    /**
     * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCStateCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCState)
     */
    @Override
    public void check(ASTSCState node) {
      if (node.isPresentCompleteness() && node.getCompleteness().isIncomplete()){
        Log.error(String.format("State %s is incomplete in a complete statechart", node.getName()), node.get_SourcePositionStart());
      }
    }
  }
}


