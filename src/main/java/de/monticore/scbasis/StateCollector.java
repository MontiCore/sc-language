/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor2;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Collects all states.
 * Also keeps track of their hierarchical level
 */
public class StateCollector implements SCBasisVisitor2 {


  protected final Map<ASTSCState, Integer> states = new HashMap<>();

  @Override
  public void visit(ASTSCState node) {
    this.states.put(node, this.getHierarchyLevel());
  }

  public Collection<ASTSCState> getStates() {
    return this.states.keySet();
  }

  /**
   * Get all states of a given hierarchy level
   * @param hierarchy the hierarchy level (0 being the root)
   * @return a collection of states
   */
  public Collection<ASTSCState> getStates(int hierarchy) {
    return this.states.entrySet().stream().filter(e -> e.getValue() == hierarchy).map(Map.Entry::getKey).collect(Collectors.toSet());
  }

  public Map<ASTSCState, Integer> getStatesMap() {
    return this.states;
  }

  /**
   * Returns the hierarchy level to keep track of in the {@link #states}
   * By default 0, the root level is returned
   * @return the hierarchy level
   */
  protected int getHierarchyLevel(){
    return 0;
  }
}
