<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Coordinator</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/node.css" rel="stylesheet">

</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="/ ">Coordinator</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="/">Instances</a></li>
                    <li class="active"><a href="#">Nodes <span class="sr-only">(current)</span></a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="jumbotron"></div>
        <div>
            <ul id="znodes" class="nav nav-list-main">
                <#include "childrenNodes.ftl" >
            </ul>
        </div>
    </div>

    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/nodes.js"></script>
</body>
</html>