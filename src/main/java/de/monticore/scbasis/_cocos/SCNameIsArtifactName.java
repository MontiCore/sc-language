/* (c) https://github.com/MontiCore/monticore */

package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.io.FilenameUtils;

/**
 * Checks if the names of named statecharts are equal to the file names
 * of the artifacts containing the statechart.
 */
public class SCNameIsArtifactName implements SCBasisASTSCArtifactCoCo {
  public static final String ERROR_CODE = "0xCC108";
  protected static final String MESSAGE = " The statechart name %s does not match the artifact name %s";

  @Override
  public void check(ASTSCArtifact node) {
    if(node.getStatechart().isPresentSCName()) {
      String scName = node.getStatechart().getSCName().get();
      String fileName = node.getFilePath().getFileName().toString();
      String baseFileName = FilenameUtils.getBaseName(fileName);
    
      if (!scName.equals(baseFileName)) {
        Log.warn(String.format(ERROR_CODE + MESSAGE, scName, baseFileName), node.get_SourcePositionStart());
      }
    }
  }
  
  
}
