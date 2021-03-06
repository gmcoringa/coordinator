package com.github.gmcoringa.coordinator.core.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorClientFactory {

    @Bean
    CuratorFramework client(@Value("${zookeeper.hosts}") String hosts,
                            @Value("${zookeeper.connection.retries:3}") int retries,
                            @Value("${zookeeper.connection.timeoutMS:15000}") int connectionTimeout,
                            @Value("${zookeeper.session.timeoutMS:60000}") int sessionTimeout) {

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, retries);

        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(hosts)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeout)
                .sessionTimeoutMs(sessionTimeout)
                .build();

        client.start();

        return client;
    }
}
