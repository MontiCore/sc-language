<!-- (c) https://github.com/MontiCore/monticore -->


# Statecharts

This MontiCore project offers a set of language components to define 
Statechart Languages: 

- [`SCBasis`](SCBasis.mc4),
- [`SCStateHierarchy`](SCStateHierarchy.mc4),
- [`SCStateInvariants`](SCStateInvariants.mc4),
- [`SCActions`](SCActions.mc4),
- [`SCDoActions`](SCDoActions.mc4),
- [`SCEvents`](SCEvents.mc4),
- [`SCTransitions4Code`](SCTransitions4Code.mc4),
- [`SCTransitions4Modelling`](SCTransitions4Modelling.mc4), and
- [`SCCompleteness`](SCCompleteness.mc4)

And it defines two concrete, complete variants of Statechart languages:

 - [`UMLStatecharts`](UMLStatecharts.mc4) and 
 - [`TriggeredStatecharts`](TriggeredStatecharts.mc4)

Both are based on several of the above provided language components and 
group them into two forms of complete Statecharts. Please also note, 
that all of these components and languages are still configurable 
activity extendable with concrete forms of values, expressions and 
statements - like the ones defined in [MontiCore's expression grammar 
library](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md).    
**Statechart examples demonstrating the syntax are provided in the [README](../../../../../README.md) of the project.**


<div align="center"> <img width="800" src="doc/Statecharts.LFD.png" 
alt="Statecharts LFD"> <br><b>Figure 1:</b> Overview of the statecharts 
language components and their relations (shown as language feature 
diagram). </div><br> 


## The [UMLStatecharts](UMLStatecharts.mc4) Language Variant 

 The goal of the `UMLStatecharts` is to provide a language for handling 
 hierarchical Statecharts 
 in the spirit of modeling languages for software: Its triggers are 
 usually function calls 
 received either as a normal method call or also as a remote procedure 
 call. Usually they have arguments and are typed.

 The body of such a Statechart 
 contains procedural actions. `UMLStatecharts` are equipped with 
 various comfortbale modelleing elements, 
 such as hierarchy, entry and exit states, conditions on the transitions 
 or invariants.
 However, they on purpose do not provide the full original Statechart 
 language, because 
 in an object oriented development process, the history indicator and 
 AND-states are not really needed.
 See [Rum16] for a detailed discussion. 

 The `UMLStatecharts` language combines the language components 
 `SCActions`, `SCDoActions`, `SCStateHierarchy`, 
 `SCStateInvariants`, `SCCompleteness`, `SCTransitions4Modelling`, 
 `CommonExpressions`, and `SCEvents`, and
 provides a set of Java-like expressions and statements included from
 `CommonExpressions` and `MCCommonStatements`. 
 The teasers in the README conform to this language.

 `UMLStatecharts` can directly be used or extended in various forms.
 The language definition can also be used as blueprint for individual configuration of
 a Statechart language variant.      
 **Example models are located [here](../../../../test/resources/examples/uml).**

## The [TriggeredStatecharts](TriggeredStatecharts.mc4) Language Variant 
  
 `TriggeredStatecharts` is the second provided 
 combination of the basic language components. 
 It combines the 
 language components `SCActions`, `SCStateHierarchy`, 
 `SCTransitions4Code`, `CommonExpressions` and 
 provides the same set of statements and expressions defined by
 `CommonExpressions` and `MCCommonStatements`.
 Thus, this language variant allows modeling Statecharts with 
 hierarchical states that may have entry and exit actions, but does
 use values instead of function calls as input.
 
 On the one hand, `TriggeredStatecharts` show how flexible the language 
 component library
 is and how easy individual languages can be designed.
 
 On the other hand, `TriggeredStatecharts` have their own dedicated use 
 for descriptions
 in the embedded area, where a software component is often *triggered* 
 on a regular basis, i.e. with a certain periodic repetition, and signals 
 do not
 arrive as events, but as available values. We also designed 
 `TriggeredStatecharts`
 as implementation oriented language and therefore do not add 
 specification oriented constructs:
 - no do actions 
 - no state invariants
 - transitions consist of a precondition, an event and an action, 
   each of which is optional.    
 **Example models are located [here](../../../../test/resources/examples/triggered).**

 ## Statechart Language Components
 
 ### Basic Structure in the [`SCBasis`](SCBasis.mc4) Component

`SCBasis` is the basic grammar component for automata. It defines a 
Statechart structure including package and imports as well as the 
Statechart nonterminal itself. Inside a Statechart the `SCArtifact` 
extension point allows adding various forms of states and transitions 
(or other constructs). Furthermore, states and transitions with 
extension points for their bodies (`SCSBody` and `SCTBody`) are 
provided. 

 ### Hierarchical States in the [`SCStateHierarchy`](SCStateHierarchy.mc4) Component

 This component provides the language component `SCStateHierarchy` 
 for _hierarchical states_. 
 It is based on the `SCBasis` language component and adds a state 
 body variant that allows nested
 state elements, which contains states and transitions.
 The [second syntax example](#example2) below contains an hierarchical state 
 `EngineRunning` which has two substates `Parking` and `Driving`.
 
 ### Actions in the [`SCActions`](SCActions.mc4) and  [`SCDoActions`](SCDoActions.mc4) Components

Two grammars provide the syntax for actions: `SCActions` and 
`SCDoActions`. The former provides _entry_
and _exit actions_. The latter builds upon the `SCActions` grammar 
and additionally provides _do 
activities_. 
The [second syntax example](#example2) contains a state 
`EngineRunning` which has an `entry` and an `exit` action. The syntax 
for do activities is quite similar,
but uses the keyword `do`, example: `do / {update();}`
 
 ### Transitions in [`SCTransitions4Code`](SCTransitions4Code.mc4) and [`SCTransitions4Modelling`](SCTransitions4Modelling.mc4)

This project also offers two variants of transitions realized by the 
two grammars 
`Transitions4Modelling`  and `Transitions4Code`. The "simpler" variant 
is offered by `Transitions4Code`,
where transitions consist of a precondition, an event and an action, 
each of which is optional. 
`Transitions4Modelling` offers a more sophisticated variant of transitions, 
which are suitable for 
specification and can additionally have a postcondition. The 
language component `Transitions4Modelling`
thus extends `Transitions4Code`.
 
 
 ### Invariants in the [`SCStateInvariants`](SCStateInvariants.mc4) Component

Invariants are provided by the language component `SCStateInvariants`.  
The [second syntax example](#example2) contains a state `EngineRunning` 
which has a simple invariant `[!fuelIsEmpty]`. 

 ### Completeness in the [`SCCompleteness`](SCCompleteness.mc4) Component
 
This small grammar allows Statechart compartments to be marked as 
complete `(c)` or incomplete `(...)`, which semantically makes an 
important difference when using Statecharts for specification and refinement.
See [Rum16] for a detailed discussion.

## Context Conditions
This section lists the context conditions grouped by their corresponding language component.

### SCBasis Context Conditions
The implementations of the context conditions for the [SCBasis](../../../grammars/de/monticore/SCBasis.mc4) 
grammar are located [here](../../../java/de/monticore/scbasis/_cocos).

* The context condition [```AtLeastOneInitialState```](../../../java/de/monticore/scbasis/_cocos/AtLeastOneInitialState.java)  
  checks if an artifact containing an SC model has at least one initial state.

* The context condition [```CapitalStateNames```](../../../java/de/monticore/scbasis/_cocos/CapitalStateNames.java) 
  checks the naming convention that state names start with a capital letter.

* The context condition [```PackageCorrespondsToFolders```](../../../java/de/monticore/scbasis/_cocos/PackageCorrespondsToFolders.java) 
  checks if the package declaration matches the folder structure where the model is located.

* The context condition [```SCFileExtension```](../../../java/de/monticore/scbasis/_cocos/SCFileExtension.java) 
  checks if an artifact
  containing an SC model has the common file ending ".sc" of SC artifacts.

* The context condition [```SCNameIsArtifactName```](../../../java/de/monticore/scbasis/_cocos/SCNameIsArtifactName.java) 
  checks if the name of a named statechart matches the artifacts (file) name.

* The context condition [```TransitionSourceTargetExists```](../../../java/de/monticore/scbasis/_cocos/TransitionSourceTargetExists.java) 
  checks if the source and target of a transition are defined states.

* The context condition [```UniqueStates```](../../../java/de/monticore/scbasis/_cocos/UniqueStates.java) 
  checks if the name of state is unique.

### SCEvents Context Conditions
The implementations of the context conditions for the [SCEvents](../../../grammars/de/monticore/SCEvents.mc4) 
grammar are located [here](../../../java/de/monticore/scevents/_cocos).

* The context condition [```NonCapitalEventNames```](../../../java/de/monticore/scevents/_cocos/NonCapitalEventNames.java) 
  checks the naming convention that event names start with a lower case letter.

* The context condition [```NonCapitalParamNames```](../../../java/de/monticore/scevents/_cocos/NonCapitalParamNames.java) 
  checks the naming convention that event parameter names start with a lower case letter.

### SCStateInvariants Context Conditions
The implementations of the context conditions for the [SCStateInvariants](../../../grammars/de/monticore/SCStateInvariants.mc4)
grammar are located [here](../../../java/de/monticore/scevents/_cocos).

* The context condition [```InvariantValid```](../../../java/de/monticore/scstateinvariants/_cocos/InvariantValid.java) 
  checks that an invariant is of type boolean.

## Handwritten AST Extensions

The generated data structure has been extended to the
generated classes using the TOP mechanism as follows:

* [`ASTStatechart`](../../../java/de/monticore/scbasis/_ast/ASTStatechart.java) contains
  a method `Optional<String> getSCName()` for obtaining the name of the statechart. 
  It returns an empty Optional by default which is used for `ASTUnnamedStatechart`. 
* [`ASTNamedStatechart`](../../../java/de/monticore/scbasis/_ast/ASTNamedStatechart.java) 
  overrides the method `Optional<String> getSCName()` and returns an Optional of the 
  name of the statechart.
* [`ASTSCArtifact`](../../../java/de/monticore/scbasis/_ast/ASTSCArtifact.java) has an 
  attribute and corresponding access methods to store the file path of the artifact
  after parsing. Used in CoCos, e.g., for checking whether the package name equals the
  name of the full qualified name of the folder containing the artifact.


## Symboltable

Statecharts mainly introduces symbols to denote states. While the form 
of Statecharts adapts over the different language variants, the symbol 
infrastructure for state symbols remains stable. Therefore, the 
discussion below is valid for all combinations of Statechart languages 
that are based on `SCBasis`. 

### Symbol kinds used by the SC language (importable or subclassed):

The SC language uses symbols of kind `TypeSymbol`, `VariableSymbol` as 
well as the dependent symbols that are used by these symbols (e.g. 
`FunctionSymbol` is used by `TypeSymbol`. 

Furthermore, the SC language reuses the `DiagramSymbol` for named 
Statecharts. 


### Symbol kinds defined by the SD language (exported):
The SC language defines the symbol kinds `SCStateSynbol` and `SCEventDefSymbol`.      
- A `SCStateSymbol` is defined as (and has no additional body):
  ```
  class SCStateSymbol {
      String name;
  }
  ```
  
  The state symbols embody the state names of the states defined in 
  a Statechart. These symbols are used for efficient navigation inside 
  the Statechart but also can be made available for other models that
  want to refer to the states. We deliberately have choosen to omit 
  information about a state being initial or final as well as transitions
  in the state symbol, because we do not regard this information as 
  relevant outside the SC language itself.
  
- The UMLStatechart language also exports the kind `SCEventDefSymbol` defined as:
  ```
  class SCEventDefSymbol {
      String name;
  }
  ```
  
  This kind of symbols are refered to by stimuli that do not 
  refer to functions 
  and thus do not have arguments and return types.
  While the symbol kind is defined within the Statechart language,
  symbols of this kind are not defined by Statecharts themselves, 
  but will be imported for use as events within transitions.
  For example method or function symbols, but also data
  type (i.e. class) symbols may be mapped to events.
  Function symbols also can be used directly.

### Symbols imported by SC models:

* SCs import `VariableSymbols`, `FunctionSymbols` and `TypeSymbols`. 
  These imported symbol can be used within action or transition bodies. 
* Furthermore, events of transitions can use `FunctionSymbols` or 
  `SCEventDefSymbols`, 
  
* Invariants and pre-/post-conditions can use `VariableSymbols` and 
  `FunctionSymbols` (dependent of the invariant language that is 
  actually used). 
  
  It is notable, that the MontiCore language composition techniques 
  allow Statecharts to automatically import symbols and "transport" 
  them to embedded 
  sublanguages, such that Statecharts themselves including their symbol
  table infrastructure can rather independently be
  developed. They don't even need to know about unforseen new
  symbol kinds that are used in embedded languages.

### Symbols exported by SC models:

* SC models export `SCStateSymbols`. 
  For each state defined in an SC, the SC exports a corresponding 
  `SCStateSymbol`.
  
  While states are stored and thus made externally available,
  transitions are deliberately kept encapsulated und thus 
  state symbols do not contain any information about their transitions. 
  
  This also includes hierarchically nested substates.
  When these states should be encapsuled, a reconfiguration of
  either the symbol storage or the scope structure would be needed.
  
* Each SC exports at most one `DiagramSymbol` corresponding to the Statechart.

* For example the artifact scope of an SC "Door.sc" is stored in 
  file "Door.scsym" with this content:
  ```
  {
    "name": "Door",
    "symbols": [
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Opened"
      },
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Closed"
      },
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Locked"
      },
      {
        "kind": "de.monticore.symbols.basicsymbols._symboltable.DiagramSymbol",
        "name": "Door"
      }
    ]
  }
  ```

## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)
* [**List of languages**](https://github.com/MontiCore/monticore/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Best Practices](https://github.com/MontiCore/monticore/blob/dev/docs/BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)
* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)
