<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Constructor for the main class of a SC

-->
${tc.signature("classname", "states", "initialState")}
${cd4c.constructor("public ${classname}()")}
{
  <#list states as state>
    this.${state?uncap_first} = new ${state}();
  </#list>
  <#if initialState??>
    this.state = this.${initialState?uncap_first};
  </#if>
}
