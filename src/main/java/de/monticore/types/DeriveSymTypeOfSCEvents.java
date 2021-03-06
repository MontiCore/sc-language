/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scevents.SCEventsMill;
import de.monticore.scevents._visitor.SCEventsTraverser;
import de.monticore.scevents._visitor.SCEventsVisitor2;
import de.monticore.types.check.*;

import java.util.Optional;

public class DeriveSymTypeOfSCEvents implements IDerive, SCEventsVisitor2 {
  
  protected TypeCheckResult typeCheckResult;
  protected SCEventsTraverser traverser;
  
  public DeriveSymTypeOfSCEvents() {
    init();
  }
  
  @Override public void init() {
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = SCEventsMill.traverser();
      // initializes visitors used for typechecking
    final DeriveSymTypeOfLiterals deriveSymTypeOfLiterals = new DeriveSymTypeOfLiterals();
    deriveSymTypeOfLiterals.setTypeCheckResult(getTypeCheckResult());
    traverser.add4MCLiteralsBasis(deriveSymTypeOfLiterals);
  
    final DeriveSymTypeOfMCCommonLiterals deriveSymTypeOfMCCommonLiterals = new DeriveSymTypeOfMCCommonLiterals();
    deriveSymTypeOfMCCommonLiterals.setTypeCheckResult(getTypeCheckResult());
    traverser.add4MCCommonLiterals(deriveSymTypeOfMCCommonLiterals);
  
    final DeriveSymTypeOfExpression deriveSymTypeOfExpression = new DeriveSymTypeOfExpression();
    deriveSymTypeOfExpression.setTypeCheckResult(getTypeCheckResult());
    traverser.add4ExpressionsBasis(deriveSymTypeOfExpression);
    
  }
  
  public TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }

  @Override
  public Optional<SymTypeExpression> getResult() {
    if(typeCheckResult.isPresentCurrentResult()){
      return Optional.ofNullable(typeCheckResult.getCurrentResult());
    }
    return Optional.empty();
  }
  
  @Override public SCEventsTraverser getTraverser() {
    return traverser;
  }
}
