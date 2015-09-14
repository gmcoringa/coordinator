<!DOCTYPE html>
<html lang="en">
    <head>
        <#include "header.ftl">
        <title>Coordinator</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Coordinator</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Instances <span class="sr-only">(current)</span></a></li>
                        <li><a href="/znodes">Nodes</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row row-offcanvas row-offcanvas-right">
                <div class="panel panel-default">
                    <div class="panel-heading">Zookeeper Instances</div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Instance</th>
                                <th>State</th>
                                <th>Role</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#list hosts as zkHost>
                            <tr>
                                <th><span>${zkHost.connectionString}</span></th>
                                <th><@getStatusLabel zkHost.status/></th>
                                <th><span>${zkHost.mode}</span></th>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div><#-- row canvas -->
        </div><#-- container -->
    </body>

    <#macro getStatusLabel status>
        <#switch status>
            <#case "LIVE">
                <div class="label label-success">${status}</div>
                <#break>
            <#case "UNSTABLE">
                <div class="label label-warning">${status}</div>
                <#break>
            <#case "DEAD">
            <#default>
                <div class="label label-danger">${status}</div>
                <#break>
        </#switch>
    </#macro>
</html>