<#list node.getChildren() as child>
    <li>
        <#if node.getPath() = "/">
            <#assign childPath = "/" + child />
        <#else>
            <#assign childPath = node.getPath() + "/" + child />
        </#if>
        <a data-path="${childPath}" onclick="toggleNode(this)" ondblclick="displayNodeContent(this)">
            <span class="glyphicon glyphicon-tasks" aria-hidden="true"></span> ${child}
        </a>
        <ul class="nav nav-list tree" style="display: none;"></ul>
    </li>
</#list>
