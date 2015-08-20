RENDER_URL_PATH = "/znodes/render/children?node=";

function toggleNode(source){
    var label = jQuery(source);
    var isLoaded = label.data("loaded") != "true";

    if(isLoaded){
        loadChildren(label);
    }

    label.parent().children('ul.tree').toggle(300);
}

function loadChildren(node){
    var url = RENDER_URL_PATH + encodeURI(node.data("path"));
    var request = jQuery.ajax({
        url: url,
        accepts: {
            xml: 'text/xml',
            text: 'text/plain'
        }
    });

    request.done(function(response){
        node.siblings("ul").append(response);
    });

    request.fail(function(jqXHR, textStatus, errorThrown){
        alert(textStatus);
    });

    jQuery("label", jQuery("#znodes")).removeClass("active");
    node.addClass("active");
    node.data("loaded", "true");
}
