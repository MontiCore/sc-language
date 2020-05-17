package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.cd.cd4analysis._ast.ASTCD4AnalysisNode;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.expressions.prettyprint.AssignmentExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.javalight._ast.ASTJavaLightNode;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.JavaLightPrettyPrinter;
import de.monticore.prettyprint.MCBasicsPrettyPrinter;
import de.monticore.statements.mcstatementsbasis._ast.ASTMCBlockStatement;
import de.monticore.statements.mcstatementsbasis._ast.ASTMCStatement;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;

import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.types.typesymbols._visitor.TypeSymbolsVisitor;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaDelegatorVisitor;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

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
    
    setStatechartWithJavaVisitor(new StatechartPrettyPrinter(printer));
    setStatechartVisitor(getStatechartWithJavaVisitor().get());
    
  }
  
  
  public String prettyPrint(ASTStatechartNode node){
    getPrinter().clearBuffer();
    node.accept(getRealThis());
    return getPrinter().getContent();
  }


  public String prettyPrint(ASTExpression node){
    getPrinter().clearBuffer();
    node.accept(getRealThis());
    return getPrinter().getContent();
  }
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
