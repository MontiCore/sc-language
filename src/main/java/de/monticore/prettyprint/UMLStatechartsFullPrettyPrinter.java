/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.literals.prettyprint.MCCommonLiteralsPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.sctransitions4modelling._ast.ASTSCTransitions4ModellingNode;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCReturnStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCVarDeclarationStatementsPrettyPrinter;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._ast.ASTUMLStatechartsNode;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class UMLStatechartsFullPrettyPrinter {
  protected IndentPrinter printer;
  
  protected UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();

  public UMLStatechartsFullPrettyPrinter() {
    this(new IndentPrinter());
  }

  public UMLStatechartsFullPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;

    // SC pretty printer
    traverser.setSCBasisHandler(new SCBasisPrettyPrinter(printer));
    traverser.setUMLStatechartsHandler(new UMLStatechartsPrettyPrinter(printer));
  
    traverser.setSCActionsHandler(new SCActionsPrettyPrinter(printer));
    traverser.setSCDoActionsHandler(new SCDoActionsPrettyPrinter(printer));
    traverser.setSCStateHierarchyHandler(new SCStateHierarchyPrettyPrinter(printer));
    traverser.setSCStateInvariantsHandler(new SCStateInvariantsPrettyPrinter(printer));
    traverser.setSCCompletenessHandler(new SCCompletenessPrettyPrinter(printer));
    traverser.setSCTransitions4ModellingHandler(new SCTransitions4ModellingPrettyPrinter(printer));
    traverser.setSCTransitions4CodeHandler(new SCTransitions4CodePrettyPrinter(printer));
    traverser.setSCEventsHandler(new SCEventsPrettyPrinter(printer));
    
    // UML Stereotypes, MCBasicTypes, ExpressionBasis
    traverser.setUMLStereotypeHandler(new UMLStereotypePrettyPrinter(printer));
    MCCommonLiteralsPrettyPrinter mcCommonLiteralsPP = new MCCommonLiteralsPrettyPrinter(printer);
    traverser.setMCCommonLiteralsHandler(mcCommonLiteralsPP);
    traverser.add4MCCommonLiterals(mcCommonLiteralsPP);
    traverser.setMCBasicTypesHandler(new MCBasicTypesPrettyPrinter(printer));
    traverser.setExpressionsBasisHandler(new ExpressionsBasisPrettyPrinter(printer));
    
    // Common Expressions, MCCommonStatements, MCReturnStatements
    traverser.setCommonExpressionsHandler(new CommonExpressionsPrettyPrinter(printer));
    traverser.setMCCommonStatementsHandler(new MCCommonStatementsPrettyPrinter(printer));
    traverser.setMCVarDeclarationStatementsHandler(new MCVarDeclarationStatementsPrettyPrinter(printer));
    traverser.setMCReturnStatementsHandler(new MCReturnStatementsPrettyPrinter(printer));
    

  }

  public String prettyprint(ASTUMLStatechartsNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCTransitions4ModellingNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCBasisNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public IndentPrinter getPrinter() {
    return printer;
  }

  public UMLStatechartsTraverser getTraverser() {
    return traverser;
  }
}
