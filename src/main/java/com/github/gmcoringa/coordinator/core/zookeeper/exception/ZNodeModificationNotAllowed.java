package com.github.gmcoringa.coordinator.core.zookeeper.exception;

public class ZNodeModificationNotAllowed extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZNodeModificationNotAllowed(String path) {
		super(String.format("ZNode[%s] can not be modified!", path));
	}
}
