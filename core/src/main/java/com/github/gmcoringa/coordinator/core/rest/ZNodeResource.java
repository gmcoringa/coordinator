package com.github.gmcoringa.coordinator.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gmcoringa.coordinator.common.ZNode;
import com.github.gmcoringa.coordinator.core.znode.ZNodeService;

@RestController("Node")
@RequestMapping("/api/node")
public class ZNodeResource {

	private final ZNodeService zNodeService;

	@Autowired
	public ZNodeResource(ZNodeService zNodeService) {
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
