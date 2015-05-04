package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.github.gmcoringa.coordinator.core.zookeeper.command.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InstanceService {

    private final Set<SimpleInstance> instances;
    private final CommandService commandService;

    @Autowired
    public InstanceService(@Value("${zookeeper.hosts}") String connectionString, CommandService commandService) {
        instances = ZookeeperInstanceFactory.createSimpleInstances(connectionString);
        this.commandService = commandService;
    }

    public Set<ZookeeperInstance> getClusterState() {
        Set<ZookeeperInstance> clusterState = new HashSet<>(instances.size() * 2);

        for (SimpleInstance simpleInstance : instances) {
            clusterState.add(commandService.getStatus(simpleInstance.getHost(), simpleInstance.getPort()));
        }

        return clusterState;
    }
}
