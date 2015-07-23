package com.github.gmcoringa.coordinator.core.rest;

import com.github.gmcoringa.coordinator.core.zookeeper.node.ZNode;
import com.github.gmcoringa.coordinator.core.zookeeper.node.ZNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("Node")
@RequestMapping("/api/node")
public class NodeResource {

    private final ZNodeService zNodeService;
    private static final String CONTENT_TYPE_JSON = "application/json";

    @Autowired
    public NodeResource(ZNodeService zNodeService) {
        this.zNodeService = zNodeService;
    }

    @RequestMapping(value = "/list/{path:(.*)$}", method = RequestMethod.GET)
    public ZNode list(@PathVariable("path") String path) {
        return zNodeService.list(path);
    }

    @RequestMapping(value = "/{path:(.*)$}", method = RequestMethod.POST, consumes = CONTENT_TYPE_JSON)
    public void create(@PathVariable("path") String path, @RequestBody String data) {
        zNodeService.create(path, Optional.ofNullable(data));
    }

    @RequestMapping(value = "/{path:(.*)$}", method = RequestMethod.PUT, consumes = CONTENT_TYPE_JSON)
    public void updateContent(@PathVariable("path") String path, String data) {
        zNodeService.updateContent(path, data);
    }

    @RequestMapping(value = "/{path:(.*)$}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("path") String path) {
        zNodeService.delete(path);
    }

    @RequestMapping(value = "/{path:(.*)$}", method = RequestMethod.GET, produces = CONTENT_TYPE_JSON)
    public String getContent(@PathVariable("path") String path) {
        return zNodeService.getData(path);
    }

    @RequestMapping("/status")
    public String status() {
        return "OK";
    }
}
