/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import de.monticore.symboltable.ImportStatement;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaDelegatorVisitor;
import de.monticore.utils.Names;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StatechartWithJavaSymbolTableCreatorDelegator extends StatechartWithJavaSymbolTableCreatorDelegatorTOP {

  public StatechartWithJavaSymbolTableCreatorDelegator(StatechartWithJavaGlobalScope globalScope) {
    super(globalScope);
  }

  public StatechartWithJavaArtifactScope createFromAST(de.monticore.umlsc.statechart._ast.ASTSCArtifact rootNode) {
    // todo: ask if this is correct
    StatechartWithJavaArtifactScope as = super.createFromAST(rootNode);
    as.setImportList(getImportStatements(rootNode));
    as.setPackageName(Names.constructQualifiedName(rootNode.getPackageList()));
    if (as.isPresentName()) {
      if (!as.getPackageName().isEmpty()) {
        globalScope.cache(as.getPackageName() + "." + as.getName());
      } else {
        globalScope.cache(as.getName());
      }
    }
    return as;
  }

  private List<ImportStatement> getImportStatements(ASTSCArtifact node) {
    List<ImportStatement> imports = new ArrayList<>();
    if (node.getMCImportStatementList() != null) {
      for (ASTMCImportStatement imp : node.getMCImportStatementList()) {
        String qualifiedImport = imp.getQName();
        imports.add(new ImportStatement(qualifiedImport, imp.isStar()));
      }
    }
    return imports;
  }

}
