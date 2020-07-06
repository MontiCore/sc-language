package de.monticore.umlsc.statechartwithjava.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.MCBasicsPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCExpression;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCInvariantContent;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCStatements;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

public class StatechartWithJavaPrettyPrinter extends MCBasicsPrettyPrinter implements StatechartWithJavaVisitor {


  public StatechartWithJavaPrettyPrinter(IndentPrinter printer) {
    super(printer);
  }

  @Override
  public void setRealThis(StatechartWithJavaVisitor realThis) {
    super.setRealThis(realThis);
  }

  @Override
  public StatechartWithJavaVisitor getRealThis() {
    return (StatechartWithJavaVisitor)super.getRealThis();
  }

  @Override
  public void handle(ASTSCStatements node) {
    node.getMCBlockStatement().accept(getRealThis());
  }

  @Override
  public void handle(ASTSCExpression node) {
    node.getExpression().accept(getRealThis());
  }

  @Override
  public void handle(ASTSCInvariantContent node) {
    node.getExpression().accept(getRealThis());
  }
}
