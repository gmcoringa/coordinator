RENDER_URL_PATH = "/znodes/render/children?node=";
NODE_PREFIX = "/api/node";

function showError(message, details){
    var modal = jQuery("#alert-error-modal");

    jQuery("#error-message").html(message);
    jQuery("#error-detail").html(details);
    modal.modal('show');
}

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
        node.siblings("ul").html(response);
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        showError(textStatus, errorThrown);
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
        showError(textStatus, errorThrown);
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
        showError(textStatus, errorThrown);
    });
}

function displayNodeContent(source){
    doViewNodeContent(jQuery(source));
}

function viewNodeContent(){
    var node = jQuery("a.active", jQuery("#znodes"));
    doViewNodeContent(node);
}

function doViewNodeContent(node){
    var url = NODE_PREFIX + node.data("path");

    var request = jQuery.ajax({
        url: url,
        accepts: {
            text: 'text/plain'
        }
    });

    request.done(function(response){
        getOrCreateContentEditor().setValue(response);
        jQuery("#node-edit-modal").modal('show');
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        showError(textStatus, errorThrown);
    });
}

function updateNode(){
    var node = jQuery("a.active", jQuery("#znodes"));
    var url = NODE_PREFIX + node.data("path");
    var content =  getOrCreateContentEditor().getValue();

    var request = jQuery.ajax({
        url: url,
        method: 'PUT',
        data: content,
        contentType: "text/plain"
    });

    request.done(function(response){
        jQuery("#node-edit-modal").modal('hide');
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        showError(textStatus, errorThrown);
    });
}

function getOrCreateContentEditor(){
    if(typeof EDITOR === "undefined"){
        EDITOR = CodeMirror.fromTextArea(
            document.getElementById("update-node-content"),
            {
                lineNumbers: true,
                autofocus: true,
                mode: "text/x-properties"
            }
        );
    }

    return EDITOR;
}

// Workaround to display editor content
jQuery( document ).ready(function() {
    jQuery('#node-edit-modal').on('shown.bs.modal', function (e) {
        getOrCreateContentEditor().refresh();
    });

    jQuery('#node-edit-modal').on('hide.bs.modal', function (e) {
        var editor = getOrCreateContentEditor();

        editor.setValue("");
        editor.clearHistory();
        editor.refresh();
    });
});