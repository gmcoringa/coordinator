package com.github.gmcoringa.coordinator.common;

import java.util.List;

public class ZNode {
	
	private final String path;
	private final List<String> children;

	public ZNode(String path, List<String> children) {
		this.path = path;
		this.children = children;
	}

	public String getPath() {
		return path;
	}

	public List<String> getChildren() {
		return children;
	}

}
