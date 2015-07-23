package com.github.gmcoringa.coordinator.core.zookeeper.instance;

public enum Mode {
    LEADER,
    FOLLOWER,
    STANDALONE,
    UNKNOWN;

    public boolean isLeader() {
        return this == Mode.LEADER;
    }

    public boolean isFollower() {
        return this == Mode.FOLLOWER;
    }

    public boolean isStandalone() {
        return this == Mode.STANDALONE;
    }
}
