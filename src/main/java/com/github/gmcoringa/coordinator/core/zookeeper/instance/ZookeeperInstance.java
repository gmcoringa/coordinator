package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import java.util.Objects;

public class ZookeeperInstance extends SimpleInstance {

    private final Mode mode;
    private final Status status;

    public ZookeeperInstance(String host, int port, Mode mode, Status status) {
        super(host, port);
        this.mode = mode;
        this.status = status;
    }

    public Mode getMode() {
        return mode;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ZookeeperInstance)) {
            return false;
        }

        if (!super.equals(object)) {
            return false;
        }

        ZookeeperInstance instance = (ZookeeperInstance) object;

        return Objects.equals(mode, instance.getMode()) &&
                Objects.equals(status, instance.getStatus());
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(mode, status);
    }

}
