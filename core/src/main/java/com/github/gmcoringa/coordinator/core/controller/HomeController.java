package com.github.gmcoringa.coordinator.core.controller;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.InstanceService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CuratorFramework curatorFramework;
    private final InstanceService instanceService;

    @Autowired
    public HomeController(CuratorFramework curatorFramework, InstanceService instanceService) {
        this.curatorFramework = curatorFramework;
        this.instanceService = instanceService;
    }

    public String home(Model model) {
        model.addAttribute("hosts", instanceService.getClusterState());
        return "home";
    }
}
