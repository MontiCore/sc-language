/* (c) https://github.com/MontiCore/monticore */
package util;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlcd4a.cd4analysis._ast.ASTCD4AnalysisNode;
import de.monticore.umlcd4a.prettyprint.CDPrettyPrinterConcreteVisitor;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;

/**
 * Created by
 *
 */
public class Util {
  public static String print(ASTStatechartNode node){
    StatechartPrettyPrinter p  = new StatechartPrettyPrinter();
    return p.prettyPrint(node);
  }
  
  public static String print(ASTCD4AnalysisNode node){
    CDPrettyPrinterConcreteVisitor p  = new CDPrettyPrinterConcreteVisitor(new IndentPrinter());
    return p.prettyprint(node);
  }
}
