package com.github.gmcoringa.coordinator.core.zookeeper.node;

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

    public String getName() {
        int index = path.lastIndexOf("/");

        if (index >= 0 && path.length() > 1) {
            return path.substring(index + 1);
        }

        return "/";
    }
}
