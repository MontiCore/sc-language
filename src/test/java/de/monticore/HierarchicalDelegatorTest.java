/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scstatehierarchy.HierarchicalStateCollector;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;

/**
 * Aim of this test is to verify the default delegator
 * visitor behaviour on various reporting utilities.
 * We use a statechart with hierarchical states.
 */
public class HierarchicalDelegatorTest {
  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testHierarchicalStateCollector() throws Exception {
    File file = new File("src/test/resources/examples/uml/Car.sc");
    try (FileReader fw = new FileReader(file)) {
      Optional<ASTSCArtifact> origAstOpt = parser.parse(fw);

      Assert.assertTrue("No ast parsed from file " + file.getName(), origAstOpt.isPresent());

      HierarchicalStateCollector stateCollectorVisitor = new HierarchicalStateCollector();

      UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
      traverser.add4SCBasis(stateCollectorVisitor);
      traverser.add4SCStateHierarchy(stateCollectorVisitor);

      origAstOpt.get().accept(traverser);

      Assert.assertEquals(4, stateCollectorVisitor.getStates().size());
    }
  }

}
