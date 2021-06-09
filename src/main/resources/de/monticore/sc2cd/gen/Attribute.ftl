<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java attribute

-->

${tc.signature("ast", "printer")}
${printer.prettyprint(ast.getModifier())} ${printer.prettyprint(ast.getMCType())} ${ast.getName()} <#rt>
 <#if ast.isPresentInitial()>=${printer.prettyprint(ast.getInitial())}</#if>;
