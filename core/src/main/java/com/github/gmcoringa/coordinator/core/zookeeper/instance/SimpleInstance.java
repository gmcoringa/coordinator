package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import java.util.Objects;

public class SimpleInstance {

    private final String host;
    private final int port;

    public SimpleInstance(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getConnectionString() {
        return host + ":" + port;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ZookeeperInstance)) {
            return false;
        }

        ZookeeperInstance instance = (ZookeeperInstance) object;

        return Objects.equals(host, instance.getHost()) &&
                Objects.equals(port, instance.getPort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
