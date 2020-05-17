<!-- (c) https://github.com/MontiCore/monticore -->

# Statecharts
The language for UML Statecharts is split up into 3 language components:
- **Statechart**: basic Statechart language component with states and transitions. 
- **StatechartWithJava**: A Statechart language that uses 
  * Java-like BockStatements for actions of transitions, 
  * Common- and AssignmentExpressions for returns and arguments of events as well as for invariants of states, pre and post conditions of transtitions
- **StatehartWithJavaWithCD** combines _StatechartWithJava_ with _CD4Analysis_. It is not meant for modeling but for deriving its corresponding transformation languages that enables to transform statecharts and class diagrams. 

## Statechart

### Handwritten Extensions
#### AST
- a method to get all outgoing transitions is added to [de.monticore.umlsc.statechart._ast.ASTSCState][ASTSCState]
- methods to directly get the source and target state are added to [de.monticore.umlsc.statechart._ast.ASTSCTransition][ASTSCTransition]
- a method to get the initial state is added to [de.monticore.umlsc.statechart._ast.ASTStatechart][ASTStatechart]



### Functionality
#### CoCos
The CoCos can be found in 
 [`de.monticore.umlsc.statechart._cocos`][cocos] and are combined accessible in
 [`de.monticore.umlsc.statechart.CoCoChecker`][cocochecker].


## StatechartsWithJava

### Handwritten Extensions

### Parser
- The parser is extended to have additional checks like the statechart's name
 has to match the file name
 ([`de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser`][SCWJParser])


<!-- ### Symboltable -->


## StatehartWithJavaWithCD
This language component is only created to derive its corresponding transformation language. This transformation language is used in the code generation for Statechart to translate Statecharts to class diagrams as an intermediate representation before generating Java code for it.

<!-- List with all references used within this markdown file: -->
[SCGrammar]: Statechart.mc4
[SCWithJGrammar]: StatechartWithJava.mc4
[SCWithJWithCDGrammar]: StatehartWithJavaWithCD.mc4
[cocos]: ../../../../java/de/monticore/umlsc/statechart/_cocos
[cocochecker]: ../../../../java/de/monticore/umlsc/statechart/CoCoChecker.java
[ASTSCState]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTSCState.java
[ASTSCTransition]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTSCTransition.java
[ASTStatechart]: ../../../../java/de/monticore/umlsc/statechart/_ast/ASTStatechart.java
[SCWJParser]: ../../../../java/de/monticore/umlsc/statechartwithjava/_parser/StatechartWithJavaParser.java