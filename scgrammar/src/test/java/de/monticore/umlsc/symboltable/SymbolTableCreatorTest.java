/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.symboltable;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.*;
import de.monticore.symboltable.mocks.languages.statechart.StateKind;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._symboltable.SCStateKind;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;
import de.monticore.umlsc.statechart._symboltable.StatechartKind;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaSymbolTableCreator;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

/**
 * TODO: Write me!
 *
 *
 */
public class SymbolTableCreatorTest {

  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

	@Test
	public void testFromFile() throws IOException {
		StatechartWithJavaLanguage language = new StatechartWithJavaLanguage();
		ResolvingConfiguration resolverConf = new ResolvingConfiguration();
		resolverConf.addDefaultFilters(language.getResolvingFilters());
		ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/"));

		StatechartWithJavaParser parser = language.getParser();

		Optional<ASTSCArtifact> opAst = parser.parse("src/test/resources/de/monticore/umlsc/examples/Banking.sc");

    System.out.println("Hallo");
    for(Finding l : Log.getFindings()) {
      System.out.println(l);
    }



		ASTSCArtifact ast =(ASTSCArtifact) opAst.get();
		
		
		GlobalScope globalScope = new GlobalScope(modelPath, language, resolverConf);
		StatechartWithJavaSymbolTableCreator st = new StatechartWithJavaSymbolTableCreator(resolverConf,globalScope);
		Scope symTab = st.createFromAST(ast);
		//	  StatechartSymbol sc = globalScope.<StatechartSymbol>resolve("Test1",StatechartKind.KIND).orElse(null);
//	  System.out.println(sc);

		Optional<? extends Symbol> sym = ast.getStatechart().getSCStateList().get(0).getSymbolOpt();
		assertTrue(sym.isPresent());
		System.out.println(sym.get().getName());
		//String o =new StatechartPrettyPrinter().prettyPrint(ast);
		//System.out.println(o);



		Optional<Symbol> scResolved = symTab.resolve("Banking", StatechartKind.KIND);
		assertTrue(scResolved.isPresent());

		System.out.println(symTab.getSymbolsSize());

		Optional<SCStateSymbol> stateResolv = symTab.resolve("Offer", SCStateKind.KIND);
		//assertTrue(stateResolv.isPresent()); //TODO: State loading fails

	}
	
	
	
	@Test
	public void testFromAST() {
		
	}

}
