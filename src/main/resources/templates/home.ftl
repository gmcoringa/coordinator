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
                <#list hosts as zkHost>
                <div class="row">
                    <div class="col-sm-4">
                        <p><h2>${zkHost.connectionString}</h2></p>
                        <div class="row">
                            <div class="col-sm-12">
                                <#switch zkHost.status>
                                    <#case "LIVE">
                                        <#assign infoClass="label label-success"/>
                                        <#break>
                                    <#case "UNSTABLE">
                                        <#assign infoClass="label label-warning"/>
                                        <#break>
                                    <#case "DEAD">
                                        <#assign infoClass="label label-danger"/>
                                        <#break>
                                </#switch>
                                <div class="${infoClass}">${zkHost.status}</div>
                                <div>
                                    <div class="col-sm-6">Role</div>
                                    <div class="col-xs-4">${zkHost.mode}</div>
                                </div>
                            </div> <#-- col-xs-2 col-md-6 -->
                        </div><#-- row -->
                    </div><#-- col-sm-4 -->
                </div><#-- row -->
                </#list>
            </div><#-- row canvas -->
        </div><#-- container -->

    </body>
</html>