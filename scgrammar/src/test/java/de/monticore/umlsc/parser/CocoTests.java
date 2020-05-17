/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

//import de.monticore.symboltable.CommonScope;
//import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._cocos.TransitionSourceAndTargetExists;
import de.monticore.umlsc.statechartwithjava._cocos.StatechartWithJavaCoCoChecker;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaSymbolTableCreator;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CocoTests {

	@BeforeClass
	public static void setup() {
		Slf4jLog.init();
		Log.enableFailQuick(false);
	}

	@Test
	public void testScUniqueNames() throws RecognitionException, IOException {
		Path model = Paths.get("src/test/resources/de/monticore/umlsc/cocos/Buchungen.sc");
		StatechartWithJavaParser parser = new StatechartWithJavaParser();
		Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());

//		StatechartWithJavaLanguage language = new StatechartWithJavaLanguage();
////		language.se
//		ResolvingConfiguration resolvingConfig = new ResolvingConfiguration();
////		resolvingConfig.addT
//		// resolvingConfiguration.addTopScopeResolvers(language.getResolvingFilters());
//		// EnclosingScopeOfNodesInitializer enclosingScope = new
//		// EnclosingScopeOfNodesInitializer();
//
////		StatechartSymbolTableCreator c = new StatechartSymbolTableCreator(resolvingConfig, new CommonScope());
////		
//		StatechartSymbolTableCreator c = (StatechartSymbolTableCreator) language.getSymbolTableCreator(resolvingConfig, new CommonScope()).get();
//		c.createFromAST(scDef.get());
////		c.create
//		Optional<Symbol> g = c.currentScope().get().resolve("SC1", StatechartSymbol.KIND);
//
//		System.out.println(g);
//
//		scDef.get().getStatechart().getSymbol().get();
//
//		StatechartCoCoChecker checker = new StatechartCoCoChecker();
//		checker.addCoCo(new AtLeastOneInitialStateInFinalStatechartChecker());
//		checker.addCoCo(new UniqueStateNames());
//		checker.handle(scDef.get());
		for(Finding f : Log.getFindings()) {
			System.out.println(f);
		}
		assertFalse(parser.hasErrors());
		assertTrue(scDef.isPresent());
//		assertTrue(Log.getErrorCount() == 1L);
		// System.out.println();
//		assertEquals("Buchungen.sc:<5,1>", Log.getFindings().get(0).getSourcePosition().get().toString());
	}


	@Test
	public void transitionSourceAndTargetExistsTest() throws RecognitionException, IOException {
		Log.enableFailQuick(false);
		Path model = Paths.get("src/test/resources/de/monticore/umlsc/cocos/Buchungen.sc");
		StatechartWithJavaParser parser = new StatechartWithJavaParser();
		Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
		StatechartWithJavaLanguage language = new StatechartWithJavaLanguage();
		//ResolvingConfiguration resolvingConfig = new ResolvingConfiguration();
		//resolvingConfig.addDefaultFilters(language.getResolvingFilters());
		//StatechartWithJavaSymbolTableCreator c = new StatechartWithJavaSymbolTableCreator(new CommonScope());
		//c.createFromAST(scDef.get());
		StatechartWithJavaCoCoChecker checker = new StatechartWithJavaCoCoChecker();
		checker.addCoCo(new TransitionSourceAndTargetExists());
		checker.handle(scDef.get());
		for (Finding f : Log.getFindings()) {
			System.out.println(f);
		}
		assertFalse(parser.hasErrors());
		assertTrue(scDef.isPresent());
	}
}
