/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechartwithjava._parser;

import com.google.common.io.Files;
import de.monticore.umlsc.statechart.ErrorCodesSC;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.se_rwth.commons.Names;
import de.se_rwth.commons.logging.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by eikermann on 22.06.2017.
 */
public class StatechartWithJavaParser extends StatechartWithJavaParserTOP {


  public Optional<ASTSCArtifact> parseSCArtifact(String file) throws IOException {
    return this.parseSCArtifact(Paths.get(file));

  }

  public Optional<ASTSCArtifact> parseSCArtifact(Path file) throws IOException {

    Optional<ASTSCArtifact> ast = super.parseSCArtifact(file.toString());
    if(ast.isPresent()) {
      String simpleFilename = Files.getNameWithoutExtension(file.toString());

      String packageName = Names.getPackageFromPath(Names.getPathFromFilename(file.toString()));
      String packageDeclaration = Names.getQualifiedName(ast.get().getPackageList());

      if(!packageName.endsWith(packageDeclaration)) {
        Log.error(String.format(ErrorCodesSC.PackageName.toString(),packageDeclaration));
      }

      if(ast.get().getStatechart().isPresentName() && !ast.get().getStatechart().getName().equals(simpleFilename)) {
        Log.error(String.format(ErrorCodesSC.FileName.toString(), ast.get().getStatechart().getName(), simpleFilename));
      }
    }
    return ast;
  }
}
