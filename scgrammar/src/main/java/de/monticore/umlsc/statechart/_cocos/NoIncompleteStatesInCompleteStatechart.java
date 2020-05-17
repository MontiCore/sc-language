/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTStatechart;

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
  
}


class NoIncompleteStates implements StatechartASTSCStateCoCo {
  
  /**
   * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCStateCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCState)
   */
  @Override
  public void check(ASTSCState node) {
    // TODO Auto-generated method stub
    
  }
  
}
