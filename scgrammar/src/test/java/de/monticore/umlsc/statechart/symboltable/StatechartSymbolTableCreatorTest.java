/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart.symboltable;

import de.monticore.io.paths.ModelPath;
//import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.IScopeSpanningSymbol;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._ast.ASTStatechart;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreatorDelegator;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaArtifactScope;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaGlobalScope;
//import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.IScope;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbol;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaSymbolTableCreatorDelegator;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


public class StatechartSymbolTableCreatorTest {

  private StatechartWithJavaGlobalScope globalScope;

  @Before
  public void setup() {
    Log.init();
    Log.enableFailQuick(false);
    final StatechartWithJavaLanguage statechartLanguage = new StatechartWithJavaLanguage();
    final ModelPath modelPath = new ModelPath(Paths.get("src/test/resources"));
    globalScope = new StatechartWithJavaGlobalScope(modelPath, statechartLanguage);
  }

  @Test
  public void testStatechartSymbolTableCreation() {
    StatechartWithJavaGlobalScope globalScope2;
    final StatechartWithJavaLanguage statechartLanguage2 = new StatechartWithJavaLanguage();
    final ModelPath modelPath2 = new ModelPath(Paths.get("src/test/resources/"));
    globalScope2 = new StatechartWithJavaGlobalScope(modelPath2, statechartLanguage2);
    //final StatechartSymbol scSymbol = globalScope.<StatechartSymbol>resolve("Test1",StatechartSymbol.KIND).orElse(null);
    final StatechartSymbol scSymbol = globalScope2.resolveStatechart("Test1").orElse(null);
    System.out.println(scSymbol);
    assertNotNull(scSymbol);
  }

  @Test
  public void testFromAST() throws IOException {
    Optional<ASTSCArtifact> ast = new StatechartWithJavaParser().parse("src/test/resources/de/monticore/umlsc/symboltable/Test1.sc");
    StatechartWithJavaGlobalScope globalScope2;
    final StatechartWithJavaLanguage statechartLanguage2 = new StatechartWithJavaLanguage();
    final ModelPath modelPath2 = new ModelPath(Paths.get("src/test/resources/"));
    globalScope2 = new StatechartWithJavaGlobalScope(modelPath2, statechartLanguage2);
    StatechartWithJavaSymbolTableCreatorDelegator stCreater = new StatechartWithJavaSymbolTableCreatorDelegator(globalScope2);
    StatechartWithJavaArtifactScope r = stCreater.createFromAST(ast.get());
    assertNotNull(r);
  }


  @Test
  public void testStatechartSymbolTableCreationInPackage() {
    final String fullName = "de.monticore.umlsc.symboltable.Test1";
    //final ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/de/monticore/umlsc/symboltable/Test1.sc"));
    //final StatechartSymbol scSymbol = globalScope.<StatechartSymbol>resolve(fullName ,StatechartSymbol.KIND).orElse(null);
    final StatechartSymbol scSymbol = globalScope.resolveStatechart(fullName).orElse(null);
    assertNotNull(scSymbol);
    System.out.println(scSymbol.getFullName());
    assertEquals(fullName, scSymbol.getFullName());
  }


}
