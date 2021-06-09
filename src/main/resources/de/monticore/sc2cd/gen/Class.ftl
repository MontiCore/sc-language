<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class

-->
${tc.signature("printer", "package")}

<#if package?has_content>
  package ${package?join('.')};
</#if>

<#--${ast.printAnnotation()}
${ast.printModifier()}--> ${printer.prettyprint(ast.getModifier())} class ${ast.getName()} <#rt>
<#if ast.isPresentCDExtendUsage()>
  ${printer.prettyprint(ast.getCDExtendUsage())}
</#if>
<#if ast.isPresentCDInterfaceUsage()>
    ${printer.prettyprint(ast.getCDInterfaceUsage())}
</#if>

{
<#-- generate all constructors -->
<#list ast.getCDAttributeList() as attr>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Attribute", [attr, printer])}
</#list>
<#-- generate all constructors -->
<#list ast.getCDConstructorList() as constructor>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Constructor", [constructor, printer])}
</#list>

<#-- generate all methods -->
<#list ast.getCDMethodList() as method>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Method", [method, printer])}
</#list>

}
