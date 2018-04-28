package com.github.gmcoringa.coordinator.core.zookeeper.node;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ZNodeTest {

	@Test
	public void shouldReturnNameAsSlashWhenPathIsBlank() {
		ZNode subject = new ZNode("", Collections.emptyList());
		Assert.assertEquals("/", subject.getName());
	}

	@Test
	public void shouldReturnNameAsSlashWhenPathIsRoot() {
		ZNode subject = new ZNode("/", Collections.emptyList());
		Assert.assertEquals("/", subject.getName());
	}

	@Test
	public void shouldReturnLastSegmentAsNameWhenPathHasSegments() {
		ZNode subject = new ZNode("/some/deep/path/segment", Collections.emptyList());
		Assert.assertEquals("segment", subject.getName());
	}
}