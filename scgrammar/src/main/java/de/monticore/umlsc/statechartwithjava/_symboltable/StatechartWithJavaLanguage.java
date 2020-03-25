/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import java.util.Optional;

import de.monticore.ast.ASTNode;
import de.monticore.modelloader.ModelingLanguageModelLoader;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.SymbolTableCreator;
import de.monticore.umlsc.statechart._symboltable.SCStateResolvingFilter;
import de.monticore.umlsc.statechart._symboltable.StatechartModelNameCalculator;
import de.monticore.umlsc.statechart._symboltable.StatechartResolvingFilter;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;

/**
 * TODO: Write me!
 *
 *
 */
public class StatechartWithJavaLanguage extends StatechartWithJavaLanguageTOP {

	/**
	 * Constructor for de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage.
	 */
	public StatechartWithJavaLanguage() {
		super("Statechart Language", "sc");

		setModelNameCalculator(new StatechartModelNameCalculator());
	}

	/**
	 * @see de.monticore.ModelingLanguage#getSymbolTableCreator(de.monticore.symboltable.ResolvingConfiguration, de.monticore.symboltable.MutableScope)
	 */
	@Override
	public Optional<? extends SymbolTableCreator> getSymbolTableCreator(ResolvingConfiguration resolvingConfiguration,
			MutableScope enclosingScope) {
    return Optional.of(new StatechartSymbolTableCreator(resolvingConfiguration, enclosingScope));
	}

	/**
	 * @see de.monticore.CommonModelingLanguage#provideModelLoader()
	 */
	@Override
	protected ModelingLanguageModelLoader<? extends ASTNode> provideModelLoader() {
		return new StatechartWithJavaModelLoader(this);
	}
	
  protected void initResolvingFilters() {
    addResolvingFilter(new SCStateResolvingFilter());
    addResolvingFilter(new StatechartResolvingFilter());
  }

}
