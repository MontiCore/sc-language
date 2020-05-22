/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import de.monticore.symboltable.ImportStatement;
import de.monticore.utils.Names;

import java.util.stream.Collectors;

public class StatechartWithJavaSymbolTableCreatorDelegator extends StatechartWithJavaSymbolTableCreatorDelegatorTOP {

  public StatechartWithJavaSymbolTableCreatorDelegator(StatechartWithJavaGlobalScope globalScope) {
    super(globalScope);
  }

  public StatechartWithJavaArtifactScope createFromAST(de.monticore.umlsc.statechart._ast.ASTSCArtifact rootNode) {
    StatechartWithJavaArtifactScope as = super.createFromAST(rootNode);
    // transfer imports and package to symbol table
    as.setImportList(rootNode.getMCImportStatementList().stream()
        .map(imp -> new ImportStatement(imp.getQName(), imp.isStar()))
        .collect(Collectors.toList()));
    as.setPackageName(Names.constructQualifiedName(rootNode.getPackageList()));
    return as;
  }
}
