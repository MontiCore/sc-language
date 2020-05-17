/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechart._ast;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.monticore.symboltable.ISymbol;

public class ASTSCState extends ASTSCStateTOP {
  
  public ASTSCState() {
    super();
  }
  
  public ASTSCState(
      Optional<ASTCompleteness> completeness,
      de.monticore.umlsc.statechart._ast.ASTSCModifier sCModifier,
      String name,
      Optional<de.monticore.umlsc.statechart._ast.ASTInvariant> invariant,
      Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> entryAction,
      Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> doAction,
      Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> exitAction,
      java.util.List<de.monticore.umlsc.statechart._ast.ASTSCState> sCStates,
      java.util.List<de.monticore.umlsc.statechart._ast.ASTSCTransition> sCTransitions,
      java.util.List<de.monticore.umlsc.statechart._ast.ASTSCCode> sCCodes,
      java.util.List<de.monticore.umlsc.statechart._ast.ASTSCInternTransition> sCInternTransitions,
      Optional<String> bracket
  )  {
    super();
  }
  
  public Set<ASTSCTransition> getOutgoingTransitions() {
    Set<ASTSCTransition> result = new HashSet<ASTSCTransition>();
    ISymbol spanning = getEnclosingScope().getSpanningSymbol();
    if (spanning.getAstNode() instanceof ASTStatechart) {
      ASTStatechart sc = (ASTStatechart) spanning.getAstNode();
      for (ASTSCTransition t : sc.getSCTransitionList()) {
        if (t.getSourceName().equals(getName())) {
          result.add(t);
        }
      }
      return result;
    } else {
      // TODO in case internal / hierarchical transitions need to be
      // resolved
    }
    return result;
  }
  
}
