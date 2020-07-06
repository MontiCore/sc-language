/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.prettyprint;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.*;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StatechartPrettyPrinterTest {
    private StatechartPrettyPrinterDelegator prettyPrinterDelegator = new StatechartPrettyPrinterDelegator(new IndentPrinter());

    @Test
    public void testASTSCTransition() throws IOException {
        //Also tests ASTSCTransitionBody, ASTInvariant, ASTSCEvent
        ASTSCArtifact sc = parse("TransitionTest.sc");

        assertEquals("Transition without body", "From -> Target",
                accept(sc.getStatechart().getSCTransitionList().get(0)).trim());

        //System.out.println(accept(sc.getStatechart().getSCTransitionList().get(1)).trim());

        assertEquals("Transition with precond", "From -> Target [PreCond] /",
                accept(sc.getStatechart().getSCTransitionList().get(1)).trim());

        assertEquals("Transition with event", "From -> Target Event /",
                accept(sc.getStatechart().getSCTransitionList().get(2)).trim());

        assertEquals("Transition with precond and event", "From -> Target [PreCond] Event /",
                accept(sc.getStatechart().getSCTransitionList().get(3)).trim());

        //ToDo: Is the double space wanted?
        assertEquals("Transition with statements", "From -> Target  / {Statements;}".replaceAll("\\s", ""),
                accept(sc.getStatechart().getSCTransitionList().get(4)).replaceAll("\\s", ""));

        //ToDo: Is the missing space between statements and PostCond wanted?
        assertEquals("Transition with statements and postcond", "From -> Target  / {Statements;}[PostCond]".replaceAll("\\s", ""),
               accept(sc.getStatechart().getSCTransitionList().get(5)).replaceAll("\\s", ""));

        assertEquals("Transition with precond, event, statements and postcond", "From -> Target [PreCond] Event / {Statements;}[PostCond]".replaceAll("\\s", ""),
                accept(sc.getStatechart().getSCTransitionList().get(6)).replaceAll("\\s", ""));
    }

    @Test
    public void testASTSCState() throws IOException {
        ASTSCArtifact sc = parse("StateTest.sc");

        //Test for simple state
        assertEquals("state", "state A",
                accept(sc.getStatechart().getSCStateList().get(0)).trim());

        //Test for state with modifier(ASTSCModifier)
        assertEquals("state with initial modifier", "initial state SI",
                accept(sc.getStatechart().getSCStateList().get(1)).trim());

        assertEquals("state with final modifier", "final state SF",
                accept(sc.getStatechart().getSCStateList().get(2)).trim());

        assertEquals("state with local modifier", "local state SL",
                accept(sc.getStatechart().getSCStateList().get(3)).trim());


        ASTSCState outerState = sc.getStatechart().getSCStateList().get(4);
        //Test for intern transitions - body is tested in TransitionTest
        assertEquals("sub state inner transition", "-> InnerTrans /",
                accept(outerState.getSCInternTransitionList().get(0)).trim());

        //Test entire substate (with indentation)
        assertEquals("sub state", "state Outer {\n" +
                        "  state IA\n" +
                        "  state IB\n" +
                        "  IA -> IB \n" +
                        "  -> InnerTrans / \n" +
                        "}",
                accept(outerState).trim());

        //For demonstration purposed
        System.out.println(accept(outerState));
    }

    @Test
    public void testASTStatechart() throws IOException {
        ASTSCArtifact sc = parse("StatechartTest.sc");
        //Test for indentation, name and order of states and transitions
        assertEquals("Statechart", "package de.monticore.umlsc.prettyprint;\nstatechart StatechartTest {\n" +
                        "  state A\n" +
                        "  A -> A \n" +
                        "}",
                accept(sc).trim());
    }

    @Test
    public void testASTSCStatements() throws IOException {
        //TODO: Tests for ASTSCStatements
    }


    /**
     * Helperfunction for parsing a SC from a file
     *
     * @param file the filename
     * @return the artifact
     */
    private ASTSCArtifact parse(String file) throws IOException {
        StatechartWithJavaParser parser = new StatechartWithJavaParser();
        Optional<ASTSCArtifact> scDef = parser.parse("src/test/resources/de/monticore/umlsc/prettyprint/" + file);
        assertFalse(parser.hasErrors());
        assertTrue(scDef.isPresent());
        return scDef.get();
    }

    /**
     * Helper function for printing out a node
     *
     * @param node the SC node
     * @return the generated content by the StatechartPrettyPrinter
     */
    private String accept(ASTStatechartNode node) {
        node.accept(prettyPrinterDelegator);
        String ret = prettyPrinterDelegator.getPrinter().getContent();
        prettyPrinterDelegator.getPrinter().clearBuffer();
        return ret;
    }
}
