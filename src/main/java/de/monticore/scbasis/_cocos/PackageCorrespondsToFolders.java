/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.utils.Names;
import de.se_rwth.commons.logging.Log;

/**
 * Checks if the package names of the artifacts corresponds to
 * their actual locations in the file system.
 */
public class PackageCorrespondsToFolders implements SCBasisASTSCArtifactCoCo {

  public static final String ERROR_CODE = "0xCC107";
  
  protected static final String MESSAGE = 
          " Package name '%s' does not correspond to the file path '%s'.";

  @Override
  public void check(ASTSCArtifact node) {
    if (node.isPresentPackage()) {
      String packageName = node.getPackage().getQName();
      if (node.getFilePath() != null && !node.getFilePath().getParent().endsWith(Names.getPathFromPackage(packageName))) {
        Log.error(String.format(ERROR_CODE + MESSAGE, packageName, node.getFilePath().toString()));
      }
    }
  }

}
