package com.github.gmcoringa.coordinator.core.controller;

import com.github.gmcoringa.coordinator.core.rest.ContentType;
import com.github.gmcoringa.coordinator.core.zookeeper.node.ZNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/znodes")
public class ZNodeController {

    private final ZNodeService zNodeService;

    @Autowired
    ZNodeController(ZNodeService zNodeService){
        this.zNodeService = zNodeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String rootNodes(Model model) {
        model.addAttribute("node", zNodeService.list("/"));
        return "nodes";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/render/children", produces = ContentType.TEXT)
    public String renderChildren(Model model, @RequestParam("node") String node){
        model.addAttribute("node", zNodeService.list(node));
        return "childrenNodes";
    }
}
