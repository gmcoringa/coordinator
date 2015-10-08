package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheFactory {

    @Bean
    Cache<String, Set<ZookeeperInstance>> clusterStateCache(
            @Value("${zookeeper.cache.instancesTimeoutMS:60000}") long cacheTimeoutMS) {
        return CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(cacheTimeoutMS, TimeUnit.MILLISECONDS)
                .build();
    }
}
