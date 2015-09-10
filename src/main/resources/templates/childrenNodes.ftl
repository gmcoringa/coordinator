<#list node.getChildren() as child>
    <li>
        <#if node.getPath() = "/">
            <#assign childPath = "/" + child />
        <#else>
            <#assign childPath = node.getPath() + "/" + child />
        </#if>
        <a href="#" data-path="${childPath}" onclick="toggleNode(this)">
            <span class="glyphicon glyphicon-tasks" aria-hidden="true"></span> ${child}
        </a>
        <ul class="nav nav-list tree" style="display: none;"></ul>
    </li>
</#list>
