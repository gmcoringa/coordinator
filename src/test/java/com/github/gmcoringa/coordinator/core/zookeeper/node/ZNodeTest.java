package com.github.gmcoringa.coordinator.core.zookeeper.node;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ZNodeTest {

    @Test
    public void shouldReturnNameAsSlashWhenPathIsBlank() throws Exception {
        ZNode subject = new ZNode("", Collections.emptyList());
        Assert.assertEquals("/", subject.getName());
    }

    @Test
    public void shouldReturnNameAsSlashWhenPathIsRoot() throws Exception {
        ZNode subject = new ZNode("/", Collections.emptyList());
        Assert.assertEquals("/", subject.getName());
    }

    @Test
    public void shouldReturnLastSegmentAsNameWhenPathHasSegments() throws Exception {
        ZNode subject = new ZNode("/some/deep/path/segment", Collections.emptyList());
        Assert.assertEquals("segment", subject.getName());
    }
}