package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.github.gmcoringa.coordinator.core.zookeeper.command.CommandService;
import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZookeeperException;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class InstanceService {

    private static final Logger LOG = LoggerFactory.getLogger(InstanceService.class);
    private static final String KEY = "cluster.state";

    private final Set<SimpleInstance> instances;
    private final CommandService commandService;
    private final Cache<String, Set<ZookeeperInstance>> clusterState;

    @Autowired
    InstanceService(
            @Value("${zookeeper.hosts}") String connectionString,
            Cache<String, Set<ZookeeperInstance>> clusterState,
            CommandService commandService) {
        this.instances = ZookeeperInstanceFactory.createSimpleInstances(connectionString);
        this.commandService = commandService;
        this.clusterState = clusterState;
    }

    public Set<ZookeeperInstance> getClusterState() {
        try {
            return clusterState.get(KEY, this::loadClusterState);
        } catch (ExecutionException e) {
            throw new ZookeeperException("Failed to load cluster state.", e);
        }
    }

    @VisibleForTesting
    Set<ZookeeperInstance> loadClusterState() {
        LOG.info("Loading cluster state");
        return instances
                .stream()
                .parallel()
                .map(instance -> commandService.getStatus(instance.getHost(), instance.getPort()))
                .collect(Collectors.toSet());
    }
}
