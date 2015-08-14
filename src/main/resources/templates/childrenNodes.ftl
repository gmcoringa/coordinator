<#list node.getChildren() as child>
    <li>
        <#assign childPath = node.getPath() + "/" + child />
        <input type="checkbox" id="${childPath}" />
        <label for="${childPath}">${child}</label>
        <ul></ul>
    </li>
</#list>