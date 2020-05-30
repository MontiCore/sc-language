/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class NTParseTest {
  
  StatechartWithJavaParser parser = new StatechartWithJavaParser();
  
  @BeforeClass
  public static void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void testStatechart() throws IOException {
    parser.parse_StringStatechart("statechart {\n"
        + "  // …\n"
        + "}\n");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart2() throws IOException {
    parser.parse_StringStatechart("statechart Door {\n"
        + "  // …\n"
        + "}\n");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart3() throws IOException {
    parser.parse_StringStatechart("statechart Door for DoorDiagram {\n"
        + "  // …\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart4() throws IOException {
    parser.parse_StringStatechart("statechart RoomDoor refines Door {\n"
        + "  // …\n"
        + "}\n");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState() throws IOException {
    parser.parse_StringSCState("state Opened");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState2() throws IOException {
    parser.parse_StringSCState("state Opened { /* … */ }");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState3() throws IOException {
    parser.parse_StringSCState("state Opened {\n"
        + "  [!Locked]\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState4() throws IOException {
    parser.parse_StringSCState("state Opened {\n"
        + "  entry / { ringTheDoorBell(); }\n"
        + "  do [timeOpened > threshhold]  / { alert(); }\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState5() throws IOException {
    parser.parse_StringSCState("state Opened {\n"
        + "  state Ajar\n"
        + "  state WideOpen\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  
  @Test
  public void testTransition() throws IOException {
    parser.parse_StringSCTransition("Open -> Close");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition2() throws IOException {
    parser.parse_StringSCTransition("Opened -> Closed close() /");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition3() throws IOException {
    parser.parse_StringSCTransition("Closed -> Opened open() / {ringTheDoorBell();}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition4() throws IOException {
    parser.parse_StringSCTransition("Closed -> Locked timeOut() / \n"
        + "          { lockDoor(); }\n"
        + "          [doorIsLocked]\n");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition5() throws IOException {
    parser.parse_StringSCTransition("Locked -> Closed [isAuthorized] unlock() /");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testInternTransition() throws IOException {
    parser.parse_StringSCInternTransition("-> timeOut() / { lockDoor(); } [doorIsLocked]");
    assertFalse(parser.hasErrors());
  }
}
