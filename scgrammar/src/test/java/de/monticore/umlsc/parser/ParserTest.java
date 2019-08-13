/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ParserTest {

	@BeforeClass
	public static void setup() {
		Slf4jLog.init();
		Log.enableFailQuick(false);
	}

	@Parameters(name = "Test({index}) - Model:{0}")
	public static List<Object[]> data() throws IOException {
		List<Object[]> fileNames = new ArrayList<>();
		Path p = Paths.get("src/test/resources/de/monticore/umlsc/parser/");

		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(p);
		for (Path path : directoryStream) {
			fileNames.add(new Object[] { path });
		}
		
		p = Paths.get("src/test/resources/de/monticore/umlsc/examples/");
		directoryStream = Files.newDirectoryStream(p);
		
		for (Path path : directoryStream) {
			fileNames.add(new Object[] { path });
		}
		
		return fileNames;

	}

	private Path p;

	/**
	 * Constructor for de.monticore.umlsc.parser.ParserTest.
	 */
	public ParserTest(Path p) {
		this.p = p;
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testParseModel() throws RecognitionException, IOException {
		Path model = p;
		StatechartWithJavaParser parser = new StatechartWithJavaParser();
		Optional<ASTSCArtifact> scDef = parser.parse(model.toString());
		if (parser.hasErrors()) {
			printFindings(Log.getFindings());
		}
		assertFalse(parser.hasErrors());
		assertTrue(scDef.isPresent());
	}




	/**
	 * TODO: Write me!
	 *
	 * @param findings
	 */
	private void printFindings(List<Finding> findings) {
        for(Finding f : findings) {
        	System.out.println(f);
        }
	}
}
