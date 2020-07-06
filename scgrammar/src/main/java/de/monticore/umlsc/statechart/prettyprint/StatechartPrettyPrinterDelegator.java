/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.prettyprint.AssignmentExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.MCBasicsPrettyPrinter;
import de.monticore.statements.mcstatementsbasis._ast.ASTMCBlockStatement;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;

import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaDelegatorVisitor;
import de.monticore.umlsc.statechartwithjava.prettyprint.StatechartWithJavaPrettyPrinter;

public class StatechartPrettyPrinterDelegator extends StatechartWithJavaDelegatorVisitor{
  
  protected StatechartWithJavaDelegatorVisitor realThis = this;
  
  protected de.monticore.prettyprint.IndentPrinter printer;
  
  public StatechartPrettyPrinterDelegator() {
    this(new IndentPrinter());
  }
  
  public StatechartPrettyPrinterDelegator(IndentPrinter printer){
    this.printer = printer;
    
    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
    setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
    setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));
    
    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    setAssignmentExpressionsVisitor(new AssignmentExpressionsPrettyPrinter(printer));
    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));

    setStatechartVisitor(new StatechartPrettyPrinter(printer));
    setStatechartWithJavaVisitor(new StatechartWithJavaPrettyPrinter(printer));
  }

  public String prettyPrint(ASTStatechartNode node){
    getPrinter().clearBuffer();
    node.accept(getRealThis());
    return getPrinter().getContent();
  }

  @Deprecated
  public String prettyPrint(ASTExpression node){
    getPrinter().clearBuffer();
    node.accept(getRealThis());
    return getPrinter().getContent();
  }

  @Deprecated
  public String prettyPrint(ASTMCBlockStatement node){
    getPrinter().clearBuffer();
    node.accept(getRealThis());
    return getPrinter().getContent();
  }

  public IndentPrinter getPrinter(){
    return printer;
  }
  
  @Override
  public StatechartWithJavaDelegatorVisitor getRealThis(){
    return realThis;
  }
  
}
