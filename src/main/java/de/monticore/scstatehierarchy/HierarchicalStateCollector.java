/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstatehierarchy;

import de.monticore.scbasis.StateCollector;
import de.monticore.scstatehierarchy._ast.ASTSCHierarchyBody;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyVisitor2;


/**
 * Extends the StateCollector for Hierarchical states
 */
public class HierarchicalStateCollector extends StateCollector
        implements SCStateHierarchyVisitor2 {

  protected int hierarchyLevel = 0;

  @Override
  protected int getHierarchyLevel() {
    return this.hierarchyLevel;
  }

  @Override
  public void visit(ASTSCHierarchyBody node) {
    this.hierarchyLevel++;
  }

  @Override
  public void endVisit(ASTSCHierarchyBody node) {
    this.hierarchyLevel--;
  }
}
