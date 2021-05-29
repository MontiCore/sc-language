<!-- (c) https://github.com/MontiCore/monticore -->

# 6.8.0-SNAPSHOT
to be released  
based on MontiCore 6.8.0-SNAPSHOT

# 6.7.0
released: 05.02.2021  
based on MontiCore 6.7.0
* the `s` parameter now expects the file name and not the folder anymore
* added new lines to reports
* extended documentation


# 6.6.0
released: 15.12.2020  
based on MontiCore 6.6.0

* new `path` option to add paths to the model path
* symbol table creation via genitors
* renamed parameter `st` to `s`
* migrated all visitors to use new traverser structure
* `UMLStatecharts` now extends `MCReturnStatements` such that transitions can have returns

# 6.5.0
released: 12.11.2020  
based on MontiCore 6.5.0

* StateInvariants no longer extends StateHierarchy to ensure invariants can be used without hierarchical states
    * SCInvState replaced SCStateInvariant
    * changed the concrete syntax from `state Foo { [a>b] };` to `state Foo [a>b] { };`  
* introduced grammar `SCEvents` for events definitions
  * example: `event String foo(int a)`
  * `UMLStatecharts` now supports events definitions by integrating this grammar
* Transitions now directly point to states via `Name@SCState`
* `Statechart` nonterminal divided into `NamedStatechart` and `UnnamedStatechart`, which both implement the newly created interface nonterminal `Statechart`


# 6.4.0
released: 27.10.2020   
based on MontiCore 6.4.0

* fixed handling of optional statechart name in symbol table creation
* improved documentation

# 6.3.0
released: 09.10.2020  
based on MontiCore 6.3.0


# 6.2.0
released: 07.10.2020  
based on MontiCore 6.2.0

* added option to store symbols
* added reports about branching degree, state names and reachable states
* New CLI tool to process UMLStatecharts 
* Pretty printers for new grammar structure
* New grammar structure composed of the following grammars:
  * `SCActions`, `SCBasis`, `SCCompleteness`, `SCDoActions`, `SCStateHierarchy`, `SCStateInvariants`, `SCTransitions4Code`, `SCTransitions4Modelling`, `TriggeredStatecharts`, `UMLStatecharts`

  ## Further Information

  * [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
  * [MontiCore documentation](http://www.monticore.de/)
  * [**List of languages**](https://github.com/MontiCore/monticore/blob/dev/docs/Languages.md)
  * [**MontiCore Core Grammar Library**](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
  * [Best Practices](https://github.com/MontiCore/monticore/blob/dev/docs/BestPractices.md)
  * [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)
  * [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)

