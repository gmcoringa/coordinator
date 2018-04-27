package com.github.gmcoringa.coordinator.core.rest;

import com.github.gmcoringa.coordinator.core.zookeeper.node.ZNode;
import com.github.gmcoringa.coordinator.core.zookeeper.node.ZNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RestController("Node")
@RequestMapping(NodeResource.PATH)
public class NodeResource {

	private final ZNodeService zNodeService;
	static final String PATH = "/api/node";

	@Autowired
	NodeResource(ZNodeService zNodeService) {
		this.zNodeService = zNodeService;
	}

	@RequestMapping(value = "/list/**", method = RequestMethod.GET)
	public ZNode list(HttpServletRequest request) {
		return zNodeService.list(extractSubPath(request).replace("/list", ""));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ZNode listRoot() {
		return zNodeService.list(null);
	}

	@RequestMapping(value = "/**", method = RequestMethod.POST, consumes = ContentType.TEXT)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(HttpServletRequest request, @RequestBody(required = false) String data) {
		zNodeService.create(extractSubPath(request), data);
	}

	@RequestMapping(value = "/**", method = RequestMethod.PUT, consumes = ContentType.TEXT)
	public void updateContent(HttpServletRequest request, @RequestBody String data) {
		zNodeService.updateContent(extractSubPath(request), data);
	}

	@RequestMapping(value = "/**", method = RequestMethod.DELETE)
	public void delete(HttpServletRequest request) {
		zNodeService.delete(extractSubPath(request));
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET, produces = ContentType.TEXT)
	public String getContent(HttpServletRequest request) {
		return zNodeService.getData(extractSubPath(request));
	}

	private String extractSubPath(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return path.replaceFirst(PATH, "");
	}
}
