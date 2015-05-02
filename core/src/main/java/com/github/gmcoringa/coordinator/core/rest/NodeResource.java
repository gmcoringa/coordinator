package com.github.gmcoringa.coordinator.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gmcoringa.coordinator.common.ZNode;
import com.github.gmcoringa.coordinator.core.zookeeper.node.NodeService;

@RestController("Node")
@RequestMapping("/api/node")
public class NodeResource {

	private final NodeService zNodeService;

	@Autowired
	public NodeResource(NodeService zNodeService) {
		this.zNodeService = zNodeService;
	}

	@RequestMapping("/list/{path:(.*)$}")
	public ZNode list(@PathVariable("path") String path) {
		return zNodeService.list(path);
	}

	@RequestMapping("/status")
	public String status() {
		return "OK";
	}
}
