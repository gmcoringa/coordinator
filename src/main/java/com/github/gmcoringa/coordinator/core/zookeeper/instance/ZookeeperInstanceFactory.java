package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZookeeperException;
import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ZookeeperInstanceFactory {


    public static ZookeeperInstance createFromStatusResponseMap(String host, int port, Map<String, String> response) {
        int id = Integer.parseInt(response.getOrDefault("Zxid", "0"));
        Mode mode = Mode.valueOf(response.getOrDefault("Mode", "unknown").toUpperCase());
        Status status = Status.valueOf(response.getOrDefault("status", Status.UNSTABLE.name()).toUpperCase());
        return new ZookeeperInstance(id, host, port, mode, status);
    }

    public static ZookeeperInstance createDeadInstance(String host, int port) {
        return new ZookeeperInstance(0, host, port, Mode.UNKNOWN, Status.DEAD);
    }

    public static Set<SimpleInstance> createSimpleInstances(String connectionString) {
        Set<SimpleInstance> instances = new HashSet<>();

        for (String instance : connectionString.split(",")) {
            instances.add(createSimpleInstance(instance));
        }

        return instances;
    }

    private static SimpleInstance createSimpleInstance(String instance) {
        String[] instanceString = instance.split(":");

        if (instanceString.length <= 1 || Strings.isNullOrEmpty(instanceString[0]) ||
                Strings.isNullOrEmpty(instanceString[1])) {
            throw new ZookeeperException("Expected host:port for host connection, but found: " + instance);
        }

        return new SimpleInstance(instanceString[0], Integer.parseInt(instanceString[1]));
    }

}
