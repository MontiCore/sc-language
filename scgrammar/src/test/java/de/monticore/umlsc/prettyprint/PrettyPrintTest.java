/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests whether the pretty printer properly prints everything.
 */
public class PrettyPrintTest {
    @BeforeClass
    public static void setup() {
        Slf4jLog.init();
        Log.enableFailQuick(false);
    }

    @Test
    public void testParseModel() throws RecognitionException, IOException {
        // load the statechart with stereotypes
        StatechartWithJavaParser parser = new StatechartWithJavaParser();
        Optional<ASTSCArtifact> scDef = parser.parse("src/test/resources/de/monticore/umlsc/examples/ScWithStereotypes.sc");
        assertFalse(parser.hasErrors());
        assertTrue(scDef.isPresent());

        // pretty print it
        StatechartPrettyPrinterDelegator prettyPrinter = new StatechartPrettyPrinterDelegator(new IndentPrinter());
        String printedResult = prettyPrinter.prettyPrint(scDef.get());

        // parse the pretty print statechart again
        Optional<ASTSCArtifact> printedScDef = parser.parse_StringSCArtifact(printedResult);
        assertFalse(parser.hasErrors());
        assertTrue(printedScDef.isPresent());

        // make sure the statecharts are equal
        assertTrue(scDef.get().getStatechart().deepEquals(printedScDef.get().getStatechart()));
    }
}
