/* (c) https://github.com/MontiCore/monticore */
package tool;

import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart.prettyprint.CountAST;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParserTOP;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by
 *
 */
public class SCToolTest {
  
  @BeforeClass
  public static void disaBleFailQuick(){
    Log.enableFailQuick(false);
  }

  @Test
  public void executeMain() {
    SCTool.main(new String[] { "src/test/models/Banking.sc" });
    
    assertTrue(!false);
  }

 
  @Test
  public void perf() throws IOException, InterruptedException {

    List<Optional<ASTSCArtifact>> scList = new LinkedList<>();


    Double start = Double.valueOf(Long.valueOf(System.currentTimeMillis()).toString());

    int rounds = 350;
    for(int i = 0; i<rounds;i++) {
      StatechartWithJavaParserTOP parser = new StatechartWithJavaParserTOP();
      Optional<ASTSCArtifact> sc = parser.parse("src/test/models/Generated.sc");
    }
    Double ende = Double.valueOf(Long.valueOf(System.currentTimeMillis()).toString());
    System.out.println("Total Time");
    System.out.println((ende-start)/1000D);

    System.out.println("Time per Round");
    System.out.println(((ende-start)/1000D)/Double.valueOf(new Double(rounds)));


    scList = new LinkedList<>();

    System.out.println("Sleep");
    Thread.sleep(5000);
    System.out.println("Sleep Ende");

    CountAST counter = new CountAST();
    for(Optional<ASTSCArtifact> a : scList) {
      if(a.isPresent()) {

        a.get().accept(counter);
      }
      else
        System.out.println("a not present");
    }

    System.out.println(counter.count);

  }

  @Ignore
  @Test
  public void createSC() throws IOException {
    File fOut = new File("src/test/models/Generated.sc");
    FileWriter fw = new FileWriter(fOut);

    fw.append("package models;\n");
    fw.append("statechart Generated {\n");
    int max = 100000;
    for(int i = 0;i<max; i++) {
      fw.append("state StateName"+ UUID.randomUUID().toString().replaceAll("-","") + " {\n");
      fw.append("}\n\n");
    }
    fw.append("}");


    fw.close();


  }


}
