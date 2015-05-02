package com.github.gmcoringa.coordinator.core.zookeeper.instance;

public enum Status {
    LIVE,
    UNSTABLE,
    DEAD;

    public boolean isAlive() {
        return this == Status.LIVE;
    }

    public boolean isUnstable() {
        return this == Status.UNSTABLE;
    }

    public boolean isDead() {
        return this == Status.DEAD;
    }
}
