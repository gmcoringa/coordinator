RENDER_URL_PATH = "/znodes/render/children?node=";
NODE_PREFIX = "/api/node";

function toggleNode(source){
    var node = jQuery(source);
    var isNotLoaded = node.data("loaded") != "true";

    if(isNotLoaded){
        loadChildren(node);
    }

    node.parent().children('ul.tree').toggle(300);
    jQuery("a", jQuery("#znodes")).removeClass("active");
    node.addClass("active");
}

function loadChildren(node){
    var url = RENDER_URL_PATH + encodeURI(node.data("path"));
    var request = jQuery.ajax({
        url: url,
        accepts: {
            text: 'text/plain'
        }
    });

    request.done(function(response){
        node.siblings("ul").append(response);
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        alert(textStatus);
    });

    node.data("loaded", "true");
}

function createNode(){
    var modal = jQuery('#node-create-modal');
    var name = jQuery('#node-name', modal);
    var content = jQuery('#node-content', modal);
    var parentNode = jQuery("a.active", jQuery("#znodes"));
    var url = NODE_PREFIX + parentNode.data("path") + "/" + name.val();

    var request = jQuery.ajax({
        url: url,
        method: 'POST',
        data: content.val(),
        contentType: "text/plain"
    });

    request.done(function(response){
        modal.modal('hide');
        name.val("");
        content.val("");
        loadChildren(parentNode);
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        alert(textStatus);
    });
}

function deleteNode(){
    var nodePath = jQuery("a.active", jQuery("#znodes")).data("path");
    jQuery("#delete-node").html(nodePath);
    jQuery("#node-delete-modal").modal('show');
}

function doDeleteNode(){
    var node = jQuery("a.active", jQuery("#znodes"));
    var url = NODE_PREFIX + node.data("path");

    var request = jQuery.ajax({
        url: url,
        method: 'DELETE'
    });

    request.done(function(response){
        node.parent().remove();
        jQuery("#node-delete-modal").modal('hide');
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        alert(textStatus);
    });
}

function viewNodeContent(){
    alert("TODO");
}
