/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.io.FilenameUtils;

/**
 * Checks if an artifact containing an SC model has the common file ending ".sc" of
 * statechart artifacts.
 */
public class SCFileExtension implements SCBasisASTSCArtifactCoCo {

  protected static final String FILE_EXTENSION = "sc";
  
  public static final String ERROR_CODE = "0xCC106";
      
  public static final String MESSAGE = " File extension is '%s', but should be " + FILE_EXTENSION;

  @Override
  public void check(ASTSCArtifact node) {
    if (node.getFilePath() != null) {
      String fileExtension = FilenameUtils.getExtension(node.getFilePath().toString());
      if (!FILE_EXTENSION.equals(fileExtension)) {
        Log.warn(String.format(ERROR_CODE + MESSAGE, fileExtension));
      }
    }
  }
}
