package com.github.gmcoringa.coordinator.core.zookeeper.exception;

public class ZookeeperException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ZookeeperException(String message) {
        super(message);
    }

    public ZookeeperException(String message, Throwable cause) {
        super(message, cause);
    }
}
