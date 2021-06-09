package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.*;
import de.monticore.scbasis._ast.ASTNamedStatechart;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.umlmodifier.UMLModifierMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor2;
import de.se_rwth.commons.Splitters;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Phase 1: Create the State Pattern classes based on the states
 */
public class SC2CDStateVisitor
        implements SCBasisVisitor2, UMLStatechartsVisitor2 {

  protected ASTSCArtifact astscArtifact;

  protected ASTCDCompilationUnit cdCompilationUnit;

  /**
   * The StateCharts class
   */
  protected ASTCDClass scClass;
  /**
   * The super class for all state implementations
   */
  protected ASTCDClass stateSuperClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> stateToClassMap = new HashMap<>();
  /**
   * Code template reference
   */
  protected final CD4C cd4C;

  /**
   * Name of the initial state
   */
  protected String initialState = "";

  public SC2CDStateVisitor() {
    this.cd4C = CD4C.getInstance();
  }


  @Override
  public void visit(ASTSCArtifact scArtifact) {
    astscArtifact = scArtifact;
  }

  @Override
  public void endVisit(ASTSCArtifact scArtifact) {
    // Generate the constructor of the class
    // It inits all state attributes
    // and the initial state
    this.cd4C.addConstructor(this.scClass, "de.monticore.sc2cd.StateInitConstructor",
                             scClass.getName(),
                             this.stateToClassMap.keySet(),
                             this.initialState);
  }

  @Override
  public void visit(ASTNamedStatechart statechart) {
    // Add a CDDefinition for every statechart
    ASTCDDefinition astcdDefinition = CDBasisMill.cDDefinitionBuilder().setName(statechart.getName())
            .setModifier(UMLModifierMill.modifierBuilder().build()).build();

    ASTCDCompilationUnitBuilder cdCompilationUnitBuilder = CDBasisMill.cDCompilationUnitBuilder();
    if (this.astscArtifact.isPresentPackage()) {
      cdCompilationUnitBuilder.setMCPackageDeclaration(
              CDBasisMill.mCPackageDeclarationBuilder().setMCQualifiedName(this.astscArtifact.getPackage()).build());
    }
    cdCompilationUnitBuilder.setMCImportStatementsList(this.astscArtifact.getMCImportStatementList());
    cdCompilationUnitBuilder.setCDDefinition(astcdDefinition);

    cdCompilationUnit = cdCompilationUnitBuilder.build();

    // Main class, names equally to the SC
    scClass = CDBasisMill.cDClassBuilder().setName(statechart.getName())
            .setModifier(CDBasisMill.modifierBuilder().setPublic(true).build()).build();
    astcdDefinition.addCDElement(scClass);
    
    
  }
  
  /**
   *  used endVisit as CD4C currently requires classes to have scopes already 
   *  */
  @Override
  public void endVisit(ASTNamedStatechart statechart) {
    CD4CodeMill.scopesGenitorDelegator().createFromAST(cdCompilationUnit);
  
    // setState method on the class
    cd4C.addMethod(scClass, "de.monticore.sc2cd.StateSetStateMethod");
  
    // The "current state" attribute on the class
    ASTCDAttribute scClassStateAttribute = CD4CodeMill.cDAttributeBuilder()
        .setModifier(CD4CodeMill.modifierBuilder().setProtected(true).build())
        .setMCType(qualifiedType("StateClass"))
        .setName("state")
        .build();
  
    // And add it to the class
    scClass.addCDMember(scClassStateAttribute);
  
    // The super class for states, has a handle{Stimulus} method
    stateSuperClass = CDBasisMill.cDClassBuilder().setName("StateClass")
        .setModifier(CDBasisMill.modifierBuilder().setAbstract(true).build()).build();
    cdCompilationUnit.getCDDefinition().addCDElement(stateSuperClass);
  }

  // TODO: public void visit(ASTUnnamedStatechart statechart)


  @Override
  public void visit(ASTSCState state) {
    if (state.getName().equals("state")) {
      throw new IllegalStateException(
              "State is named \"state\", which interferes with the attribute for the currently seleted state");
    }
    if (initialState.isEmpty()) {
      initialState = state.getName();
    }

    // A class extending StateClass for this state
    ASTCDClass stateClass = CDBasisMill.cDClassBuilder().setName(state.getName())
            .setModifier(CDBasisMill.modifierBuilder().build())
            .setCDExtendUsage(CDBasisMill.cDExtendUsageBuilder().addSuperclass(qualifiedType("StateClass")).build())
            .build();
    // Add the StateClassImpl to the CD and mapping
    this.cdCompilationUnit.getCDDefinition().addCDElement(stateClass);
    this.stateToClassMap.put(state.getName(), stateClass);
    // Add reference to this in the main class, in form of an attribute
    scClass.addCDMember(
            CD4CodeMill.cDAttributeBuilder()
                    .setModifier(CD4CodeMill.modifierBuilder().setProtected(true).build())
                    .setMCType(qualifiedType(state.getName()))
                    .setName(StringUtils.uncapitalize(state.getName()))
                    .build()
                       );

    // Set the initial state
    if (state.getSCModifier().isInitial()) {
      this.initialState = state.getName();
    }
  }

  public ASTCDCompilationUnit getCdCompilationUnit() {
    return cdCompilationUnit;
  }

  public ASTCDClass getScClass() {
    return scClass;
  }

  public Map<String, ASTCDClass> getStateToClassMap() {
    return stateToClassMap;
  }

  public ASTCDClass getStateSuperClass() {
    return stateSuperClass;
  }

  // Support methods
  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }

  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
            .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }
}
