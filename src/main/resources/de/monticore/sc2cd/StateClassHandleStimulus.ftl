<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Fills in a method with the state-action implementation

-->
${tc.signature("stimulus", "className", "targetStateAttrName", "action", "precondition")}
${cd4c.method("public void handle${stimulus?cap_first}( ${className} k)")}
{
 // ocl invariant is ignored
 if (${precondition}) { // precondition
    // action:
    ${action}

    // Set next state
    k.setState(k.${targetStateAttrName?uncap_first});
 }
}
