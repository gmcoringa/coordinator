<!DOCTYPE html>
<html lang="en">
<head>
    <#include "header.ftl">
    <title>Coordinator - Nodes</title>
    <link href="css/node.css" rel="stylesheet">
    <link href="css/codemirror.css" rel="stylesheet">
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
                    <li class="active"><a>Nodes <span class="sr-only">(current)</span></a></li>
                </ul>
            </div>

            <!-- Znodes actions -->
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="collapse navbar-collapse" id="node-menu-nav-bar">
                        <button type="button" class="btn btn-primary navbar-btn" data-toggle="modal" data-target=".new-node-modal">New</button>
                        <button type="button" class="btn btn-primary navbar-btn" onclick="deleteNode();">Delete</button>
                        <button type="button" class="btn btn-primary navbar-btn" onclick="viewNodeContent();">View / Modify</button>
                    </div>
                </div>
            </nav>
        </div>
    </nav>

    <div class="container">
        <div>
            <ul id="znodes" class="nav nav-list-main">
                <li>
                    <a data-path="" onclick="toggleNode(this)">
                        <span class="glyphicon glyphicon-tasks" aria-hidden="true"></span> root
                    </a>
                    <ul id="znodes" class="nav nav-list tree">
                        <#include "childrenNodes.ftl" >
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <div id="node-create-modal" class="modal fade new-node-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">New Node</h4>
                </div>
                <div class="modal-body">
                    <form id="node-create-form">
                        <div class="form-group">
                            <label for="node-name" class="control-label">Name:</label>
                            <input type="text" class="form-control" id="node-name">
                        </div>
                        <div class="form-group">
                            <label for="node-content" class="control-label">Content:</label>
                            <textarea class="form-control" id="node-content"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="createNode();">Create</button>
                </div>
            </div>
        </div>
    </div>

    <div id="node-delete-modal" class="modal fade delete-node-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Delete Node</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger alert-dismissible fade in" role="alert">
                        <h4>Do you really want to delete <b id="delete-node"></b></h4>
                        <p>Deleting this node will delete all children nodes.</p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger" onclick="doDeleteNode();">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <div id="node-edit-modal" class="modal fade edit-node-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">View / Modify Node</h4>
                </div>
                <div class="modal-body">
                    <textarea id="update-node-content"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="updateNode();">Save</button>
                </div>
            </div>
        </div>
    </div>

    <div id="alert-error-modal" class="modal fade alert-error-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Something goes wrong</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger alert-dismissible fade in" role="alert">
                        <h4 id="error-message"></h4>
                        <p id="error-detail"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/nodes.js"></script>
    <script src="js/codemirror.js"></script>
    <script src="js/properties.js"></script>
</body>
</html>
