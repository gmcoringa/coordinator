package com.github.gmcoringa.coordinator.core.zookeeper.instance;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ZookeeperInstanceFactoryTest {

    @Test
    public void shouldCreateDeadInstance() {
        ZookeeperInstance instance = new ZookeeperInstance(0, "deadHost", 2181, Mode.UNKNOWN, Status.DEAD);
        Assert.assertEquals(instance, ZookeeperInstanceFactory.createDeadInstance("deadHost", 2181));
    }

    @Test
    public void shouldCreateLiveInstanceFromResponseMap() {
        Map<String, String> responseMap = new HashMap<>(4);
        responseMap.put("Zxid", "1");
        responseMap.put("Mode", "standalone");
        responseMap.put("status", Status.LIVE.name());

        ZookeeperInstance instance = new ZookeeperInstance(1, "liveHost", 2181, Mode.STANDALONE, Status.LIVE);
        Assert.assertEquals(instance, ZookeeperInstanceFactory.createFromStatusResponseMap("liveHost", 2181, responseMap));
    }

    @Test
    public void shouldCreateSimpleInstance() {
        SimpleInstance simpleInstance1 = new SimpleInstance("simple1", 2181);
        SimpleInstance simpleInstance2 = new SimpleInstance("simple2", 2181);

        Set<SimpleInstance> instances = ZookeeperInstanceFactory.createSimpleInstances("simple1:2181,simple2:2181");

        Assert.assertEquals(2, instances.size());
        Assert.assertTrue(instances.containsAll(Arrays.asList(simpleInstance1, simpleInstance2)));
    }
}
