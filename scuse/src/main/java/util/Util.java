/* (c) https://github.com/MontiCore/monticore */
package util;

import de.monticore.cd.cd4analysis._ast.ASTCD4AnalysisNode;
import de.monticore.cd.prettyprint.CDPrettyPrinterDelegator;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator;

public class Util {
  public static String print(ASTStatechartNode node){
    StatechartPrettyPrinterDelegator p  = new StatechartPrettyPrinterDelegator();
    return p.prettyPrint(node);
  }
  
  public static String print(ASTCD4AnalysisNode node){
    CDPrettyPrinterDelegator p  = new CDPrettyPrinterDelegator(new IndentPrinter());
    return p.prettyprint(node);
  }
}
