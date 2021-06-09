<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Main class method forwarding the stimulus to the concrete state

-->
${tc.signature("stimulus")}
${cd4c.method("public void " + stimulus +"()")}
{
  this.state.handle${stimulus?cap_first}(this);
}
