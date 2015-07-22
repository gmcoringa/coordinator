package com.github.gmcoringa.coordinator.core.controller;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    private final InstanceService instanceService;

    @Autowired
    public HomeController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("hosts", instanceService.getClusterState());
        return "home";
    }
}
