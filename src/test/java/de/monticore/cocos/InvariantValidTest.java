/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.StatechartsCLI;
import de.monticore.io.paths.MCPath;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scstateinvariants._cocos.InvariantValid;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.symbols.oosymbols._symboltable.FieldSymbol;
import de.monticore.symbols.oosymbols._symboltable.OOTypeSymbol;
import de.monticore.types.DeriveSymTypeOfUMLStatecharts;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.monticore.types.check.TypeCheck;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvariantValidTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.init();
    UMLStatechartsMill.init();
    BasicSymbolsMill.initializePrimitives();
  }
  
  @Before
  public void setUp() throws Exception {
    Log.clearFindings();
    UMLStatechartsMill.init();
  }
  
  @Test
  public void testCoCoInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/invalid/Invariant.sc");
    assertTrue("Invariant.sc could not be parsed",  ast.isPresent());
    IUMLStatechartsArtifactScope st = new StatechartsCLI().createSymbolTable(ast.get());
    st.setName("Invariant");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new InvariantValid(new TypeCheck(null, new DeriveSymTypeOfUMLStatecharts())));
    try {
      checker.checkAll(ast.get());
    } catch (NoSuchElementException e){ 
      /* catch Optional Exception thrown due to LogStub instead of Log*/ 
    }
    assertEquals(1, Log.getErrorCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains("0xED680")));
    
  }
  
  @Test
  public void testCoCoValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/Invariant2.sc");
    assertTrue("Invariant2.sc could not be parsed",  ast.isPresent());
    IUMLStatechartsArtifactScope st = new StatechartsCLI().createSymbolTable(ast.get());
    st.setName("Invariant2");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new InvariantValid(new TypeCheck(null, new DeriveSymTypeOfUMLStatecharts())));
    checker.checkAll(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValid2() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/Invariant3.sc");
    assertTrue("Invariant3.sc could not be parsed",  ast.isPresent());
  
    IUMLStatechartsArtifactScope st = new StatechartsCLI()
        .createSymbolTable(ast.get());
    st.setName("Invariant3");
    UMLStatechartsMill.globalScope().add(
        UMLStatechartsMill.variableSymbolBuilder().setName("b").setType(
            SymTypeExpressionFactory.createTypeConstant("boolean")
        ).build()
    );
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new InvariantValid(new TypeCheck(null, new DeriveSymTypeOfUMLStatecharts())));
    checker.checkAll(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test 
  public void testCoCoValid3() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/Invariant4.sc");
    assertTrue("Invariant3.sc could not be parsed",  ast.isPresent());
    
    IUMLStatechartsArtifactScope st = new StatechartsCLI().createSymbolTable(ast.get());
    st.setName("Invariant4");
    OOTypeSymbol person = UMLStatechartsMill.oOTypeSymbolBuilder().setName("Person").build();
    person.setSpannedScope(UMLStatechartsMill.scope());
    FieldSymbol age = UMLStatechartsMill.fieldSymbolBuilder().setName("age").build();
    age.setType(SymTypeExpressionFactory.createTypeConstant("int"));
    age.setIsStatic(true);
    person.addFieldSymbol(age);
    UMLStatechartsMill.globalScope().add(person);
    UMLStatechartsMill.globalScope().add((TypeSymbol) person);
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new InvariantValid(new TypeCheck(null, new DeriveSymTypeOfUMLStatecharts())));
    checker.checkAll(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValid4() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/Invariant5.sc");
    assertTrue("Invariant5.sc could not be parsed",  ast.isPresent());
    
    IUMLStatechartsArtifactScope st = new StatechartsCLI().createSymbolTable(ast.get());
    st.setName("Invariant5");
    UMLStatechartsMill.globalScope().setSymbolPath(new MCPath(Paths.get("src/test/resources/symtab")));
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new InvariantValid(new TypeCheck(null, new DeriveSymTypeOfUMLStatecharts())));
    checker.checkAll(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
}
