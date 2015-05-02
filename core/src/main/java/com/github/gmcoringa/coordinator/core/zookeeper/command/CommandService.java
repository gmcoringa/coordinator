package com.github.gmcoringa.coordinator.core.zookeeper.command;

import com.github.gmcoringa.coordinator.core.zookeeper.instance.ZookeeperInstance;
import com.github.gmcoringa.coordinator.core.zookeeper.instance.ZookeeperInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    private static final String IMOK = "imok";

    private final ZookeeperCommand command;

    @Autowired
    public CommandService(ZookeeperCommand command) {
        this.command = command;
    }

    public boolean isAlive(String host, int port) {
        CommandResponse response = command.performCommand(Word.RUOK, host, port);
        return response.isOk() && response.getResponse().equals(IMOK);
    }

    public ZookeeperInstance getStatus(String host, int port) {
        if (isAlive(host, port)) {
            return ZookeeperInstanceFactory.createFromStatusResponseMap(host, port, command.performCommand(Word.SRVR,
                    host, port).getResponseMap());
        }

        return ZookeeperInstanceFactory.createDeadInstance(host, port);
    }

}
