<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Coordinator</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/nodes.css" rel="stylesheet">

</head>
<body>

    <div class="css-treeview">
        <ul>
            <li>
                <input type="checkbox" id="${node.getPath()}" />
                <label for="${node.getPath()}">${node.getName()}</label>
                <ul>
                    <#include "childrenNodes.ftl" >
                </ul>
            </li>
        </ul>
    </div>
</body>
</html>