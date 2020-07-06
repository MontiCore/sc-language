/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.*;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

/**
 * This test is going to parse a SC model, pretty print it, parse the pretty printed output, and compare the two models
 */

public class StatechartPrettyPrinterParseTest {

  private final StatechartPrettyPrinterDelegator prettyPrinterDelegator = new StatechartPrettyPrinterDelegator(new IndentPrinter());
  private final StatechartWithJavaParser statechartWithJavaParser = new StatechartWithJavaParser();

  @BeforeClass
  public static void init() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Before
  public void setUp() {
    Log.getFindings().clear();
  }

  private ASTSCArtifact parseFile(String filename) throws IOException{
    Optional<ASTSCArtifact> opt = statechartWithJavaParser.parseSCArtifact(filename);
    if (!opt.isPresent())
      throw new IllegalStateException("Failed to parse " + filename);
    return opt.get();
  }

  private ASTSCArtifact parseString(String src) throws IOException{
    Optional<ASTSCArtifact> opt = statechartWithJavaParser.parse(new StringReader(src));
    if (!opt.isPresent())
      throw new IllegalStateException("Failed to parse model from string: " + src);
    return opt.get();
  }


  public void test(String filename) throws IOException {
    Log.info("Testing sc " + filename, getClass().getName());
    ASTSCArtifact originalArtifact = parseFile(filename);
    String prettyPrint = prettyPrinterDelegator.prettyPrint(originalArtifact);
    ASTSCArtifact prettyArtifact = parseString(prettyPrint);
    if (!prettyArtifact.deepEquals(originalArtifact)) {
      Assert.fail("Parsed from PrettyPrinter not equivalent to from file: " + prettyPrint);
    }
  }

  @Test
  public void testWithoutParameterized() throws IOException {
    // No parameterized test
    test("src/test/resources/de/monticore/umlsc/examples/Banking.sc");
    test("src/test/resources/de/monticore/umlsc/examples/Door.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScSimpleState.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransition.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransitionInternal.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScTransitionExt.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScWithClassReference.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScWithOutClassReference.sc");
    test("src/test/resources/de/monticore/umlsc/examples/ScWithStereotypes.sc");
    test("src/test/resources/de/monticore/umlsc/examples/TestInnerTransitions.sc");
  }


}
