/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechart._ast;

import java.util.Optional;

import de.monticore.symboltable.IScope;
import de.monticore.symboltable.ISymbol;
import de.monticore.umlsc.statechart._symboltable.IStatechartScope;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;

public class ASTSCTransition extends ASTSCTransitionTOP {

  public ASTSCTransition() {
    super();
  }

  public ASTSCTransition(
      Optional<de.monticore.umlsc.statechart._ast.ASTSCStereotype> stereotype,
      String sourceName,
      String targetName,
      Optional<de.monticore.umlsc.statechart._ast.ASTSCTransitionBody> sCTransitionBody
  ) {
    super();
  }

  public ASTSCState getTarget() {
    if (getEnclosingScope() != null) {
      IStatechartScope scope = getEnclosingScope();
      String name = getTargetName();
      Optional<SCStateSymbol> symbol = scope.resolveSCState(name);
      if (symbol.isPresent()) {
        return symbol.get().getAstNode();
      }
    }
    return null;
  }

  public ASTSCState getSource() {
    if (getEnclosingScope() != null) {
      IStatechartScope scope = getEnclosingScope();
      String name = getSourceName();
      Optional<SCStateSymbol> symbol = scope.resolveSCState(name);
      if (symbol.isPresent()) {
        return symbol.get().getAstNode();
      }
    }
    return null;
  }

}
