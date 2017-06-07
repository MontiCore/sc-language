/* generated by template symboltable.ModelLoader*/




package de.monticore.umlsc.statechartwithjava._symboltable;

import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.se_rwth.commons.logging.Log;

public class StatechartWithJavaModelLoader extends de.monticore.modelloader.ModelingLanguageModelLoader<de.monticore.umlsc.statechart._ast.ASTSCArtifact> {

  public StatechartWithJavaModelLoader(StatechartWithJavaLanguage language) {
    super(language);
  }

  @Override
  protected void createSymbolTableFromAST(final de.monticore.umlsc.statechart._ast.ASTSCArtifact ast, final String modelName,
    final MutableScope enclosingScope, final ResolvingConfiguration resolvingConfiguration) {
    final StatechartSymbolTableCreator symbolTableCreator =
            (StatechartSymbolTableCreator) getModelingLanguage().getSymbolTableCreator(resolvingConfiguration, enclosingScope).orElse(null);

    if (symbolTableCreator != null) {
      Log.debug("Start creation of symbol table for model \"" + modelName + "\".",
          StatechartWithJavaModelLoader.class.getSimpleName());
      final Scope scope = symbolTableCreator.createFromAST(ast);

      if (!(scope instanceof ArtifactScope)) {
        Log.warn("0xA7001_681 Top scope of model " + modelName + " is expected to be an artifact scope, but"
          + " is scope \"" + scope.getName() + "\"");
      }

      Log.debug("Created symbol table for model \"" + modelName + "\".", StatechartWithJavaModelLoader.class.getSimpleName());
    }
    else {
      Log.warn("0xA7002_681 No symbol created, because '" + getModelingLanguage().getName()
        + "' does not define a symbol table creator.");
    }
  }

  @Override
  public StatechartWithJavaLanguage getModelingLanguage() {
    return (StatechartWithJavaLanguage) super.getModelingLanguage();
  }
}