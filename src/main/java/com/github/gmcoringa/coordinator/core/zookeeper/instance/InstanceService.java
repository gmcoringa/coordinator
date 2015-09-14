package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.github.gmcoringa.coordinator.core.zookeeper.command.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstanceService {

    private final Set<SimpleInstance> instances;
    private final CommandService commandService;

    @Autowired
    InstanceService(@Value("${zookeeper.hosts}") String connectionString, CommandService commandService) {
        instances = ZookeeperInstanceFactory.createSimpleInstances(connectionString);
        this.commandService = commandService;
    }

    public Set<ZookeeperInstance> getClusterState() {
        return instances
                .stream()
                .map(instance -> commandService.getStatus(instance.getHost(), instance.getPort()))
                .collect(Collectors.toSet());

    }
}
