/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart._symboltable;

import de.monticore.symboltable.ImportStatement;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._ast.ASTStatechart;


import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class StatechartSymbolTableCreator extends StatechartSymbolTableCreatorTOP {

  public StatechartSymbolTableCreator(IStatechartScope enclosingScope) {
    super(enclosingScope);
  }

  public StatechartSymbolTableCreator(Deque<? extends IStatechartScope> scopeStack){
    super(scopeStack);
  }

  @Override
  protected IStatechartScope create_SCArtifact(ASTSCArtifact node) {
    // skip scope creation if possible, i.e. an SCArtifact does not produce its own scope
    // but uses the artifact scope
    return getCurrentScope().orElse(createScope(false));
  }


}
