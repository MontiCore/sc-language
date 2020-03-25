/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart;

import de.monticore.umlsc.statechart._cocos.AtLeastOneInitialStateInFinalStatechartChecker;
import de.monticore.umlsc.statechart._cocos.StatechartCoCoChecker;
import de.monticore.umlsc.statechart._cocos.UniqueStateNames;

/**
 * TODO: Write me!
 *
 *
 */
public class CoCoChecker {
	public void start() {
		StatechartCoCoChecker checker = new StatechartCoCoChecker();
		checker.addCoCo(new AtLeastOneInitialStateInFinalStatechartChecker());
		checker.addCoCo(new UniqueStateNames());
		
	}
}
