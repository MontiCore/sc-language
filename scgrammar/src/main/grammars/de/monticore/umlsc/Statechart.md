<!-- (c) https://github.com/MontiCore/monticore -->

# Statecharts

The language for UML Statecharts is split up into 3 language components:
- **Statechart**: basic Statechart language component with states and transitions. 
- **StatechartWithJava**: A Statechart language that uses 
  * Java-like BockStatements for actions of transitions, 
  * Common- and AssignmentExpressions for returns and arguments of events as well as for invariants
   of states, pre and post conditions of transtitions
- **StatehartWithJavaWithCD** combines _StatechartWithJava_ with _CD4Analysis_. It is not meant for
   modeling but for deriving its corresponding transformation languages that enables to transform 
   statecharts and class diagrams. 

[[_TOC_]]

## The Statechart Language Component
The Statechart language component provides the concrete and abstract syntax to model Statecharts.
It is based on the language components `MCBasicTypes` and `MCCommonLiterals` provided by MontiCore.

### Syntax 
The grammar [Statechart] documents the abstract and concrete syntax of this language component. 
The grammar is a component grammar and contains a couple of extension points as external nonterminals:
* `SCStatements` - used to model the actions of do, entry and exit actions, transitions, 
   and code within the statechart or states
* `SCExpression` - used to model return events and arguments 
* `SCInvariantContent` -  used to model pre-, post-conditions of transitions and actions as well
   as invariants of states

#### Handwritten AST & Symbol Table Classes
The AST has been extended by the following functionality:
- [ASTSCState][ASTSCState]: a method to get all outgoing transitions is added 
- [ASTSCTransition][ASTSCTransition]: methods to directly get the source and target state are added  
- [ASTStatechart][ASTStatechart]: a method to get the initial state is added 


#### CoCos
The CoCos can be found in 
 [`de.monticore.umlsc.statechart._cocos`][cocos] and are combined accessible in
 [`de.monticore.umlsc.statechart.CoCoChecker`][cocochecker].


## The StatechartsWithJava Language Component 
The grammar [StatechartWithJava] extends the language component [Statechart] and fills its extension 
points with Java-like Syntax taken from the language components `CommonExpressions`,
`AssignmentExpressions`, and  `MCCommonStatements` provided by MontiCore. 

### Syntax
The extension points of [Statechart] are filled as follows:
* `SCStatements = MCBlockStatement` 
* `SCExpression = Expression`
* `SCInvariantContent = Expression`

A compact teaser for the StatechartsWithJava language component:

```
statechart Door {
  state Opened
  initial state Closed
  state Locked

  Opened -> Closed close() /
  Closed -> Opened open() / {ringTheDoorBell();}
  Closed -> Locked timeOut() / { lockDoor(); } [doorIsLocked]
  Locked -> Closed [isAuthorized] unlock() /
}
```
This example models the different states of a door: `Opened`, `Closed`, and `Locked`.
When the statechart is in state `Opened`, it is possible to close the door using `close()`.
When the statechart is in state `Closed`, it is possible to open the door using `open()`. 
In the latter case the action  `ringDoorBell()` is executed. 
When the door is `Closed` it is automatically locked after some time due to a 
`timeout()` event that triggers the `lockDoor()` action.
Consequently, the post-condition `doorIsLocked` holds. In case the door is locked,
it can be unlocked by using `unlock()` if the pre-condition `isAuthorized` is fulfilled.

#### Handwritten Extensions

The parser [`StatechartWithJavaParser`][SCWJParser] is extended to have additional checks like the 
statechart's name has to match the file name.



<!-- ### Symboltable -->


## StatechartWithJavaWithCD
This language component is only created to derive its corresponding transformation language. 
This transformation language is used in the code generation for Statechart to translate Statecharts
to class diagrams as an intermediate representation before generating Java code for it.

<!-- List with all references used within this markdown file: -->
[Statechart]: Statechart.mc4
[StatechartWithJava]: StatechartWithJava.mc4
[SCWithJWithCDGrammar]: StatechartWithJavaWithCD.mc4
[cocos]: ../../../../java/de/monticore/umlsc/statechart/_cocos
[cocochecker]: ../../../../java/de/monticore/umlsc/statechart/CoCoChecker.java
[ASTSCState]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTSCState.java
[ASTSCTransition]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTSCTransition.java
[ASTStatechart]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTStatechart.java
[SCWJParser]: ../../../../java/de/monticore/umlsc/statechartwithjava/_parser/StatechartWithJavaParser.java

## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)

* [**List of languages**](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Best Practices](BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)

* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)

