/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCState;
import de.se_rwth.commons.logging.Log;

public class CapitalStateNames implements SCBasisASTSCStateCoCo {
  
  public static final String ERROR_CODE = "0xCC105";
  
  @Override public void check(ASTSCState node) {
    if (Character.isLowerCase(node.getName().charAt(0))) {
      Log.warn(ERROR_CODE + " State " + node.getName() + " should start with a capital letter." );
    }
  }
}
