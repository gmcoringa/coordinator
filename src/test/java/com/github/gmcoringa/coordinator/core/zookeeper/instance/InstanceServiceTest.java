package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import com.github.gmcoringa.coordinator.core.zookeeper.command.CommandService;
import com.google.common.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InstanceServiceTest {

    private final String CONNECTION_STRING = "localhost:2181";

    private InstanceService subject;
    @Mock
    private CommandService commandService;
    @Mock
    private Cache<String, Set<ZookeeperInstance>> clusterStateCache;

    @Before
    public void setup() {
        subject = new InstanceService(CONNECTION_STRING, clusterStateCache, commandService);
    }

    @Test
    public void shouldReturnCachedClusterStateWhenIsCached() throws ExecutionException {
        Set<ZookeeperInstance> clusterState = newHashSet(ZookeeperInstanceFactory.createDeadInstance("host", 0));
        when(clusterStateCache.get(Mockito.anyString(), Mockito.any())).thenReturn(clusterState);

        assertEquals(clusterState, subject.getClusterState());
        verify(commandService, never()).getStatus(anyString(), anyInt());
    }

    @Test
    public void shouldLoadInstanceStateWhenNotCached() {
        ZookeeperInstance instance = ZookeeperInstanceFactory.createDeadInstance("localhost", 2181);
        Set<ZookeeperInstance> clusterState = newHashSet(instance);
        when(commandService.getStatus(instance.getHost(), instance.getPort())).thenReturn(instance);

        assertEquals(clusterState, subject.loadClusterState());
    }
}