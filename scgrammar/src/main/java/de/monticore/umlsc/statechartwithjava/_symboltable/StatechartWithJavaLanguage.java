/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import de.monticore.ast.ASTNode;
//import de.monticore.modelloader.ModelingLanguageModelLoader;
//import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.monticore.umlsc.statechart._symboltable.IStatechartScope;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.monticore.utils.Names;

public class StatechartWithJavaLanguage extends StatechartWithJavaLanguageTOP {
  
  /**
   * Constructor for de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage.
   */
  public StatechartWithJavaLanguage() {
    super("Statechart Language", "sc");
    
    
  }
  
  
  
  @Override
  protected Set<String> calculateModelNamesForSCState(String name) {
    // e.g., if p.Automaton.State, return p.Automaton
    if (!Names.getQualifier(name).isEmpty()) {
      return ImmutableSet.of(Names.getQualifier(name));
    }
    
    return Collections.emptySet();
  }
  
  
  
  /**
   *  de.monticore.IModelingLanguage#getSymbolTableCreator(de.monticore.symboltable.IScope)
   */
  public Optional<? extends StatechartSymbolTableCreator> getSymbolTableCreator(IStatechartScope enclosingScope) {
    return Optional.of(new StatechartSymbolTableCreator(enclosingScope));
  }
  
  /**
   *  de.monticore.CommonModelingLanguage#provideModelLoader()
   */
  @Override
  protected StatechartWithJavaModelLoader provideModelLoader() {
    return new StatechartWithJavaModelLoader(this);
  }
  
}
