package com.github.gmcoringa.coordinator.core.zookeeper.node;

import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZNodeModificationNotAllowed;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.PathAndBytesable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZNodeServiceTest {

	@InjectMocks
	private ZNodeService subject;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private CuratorFramework client;
	@Mock
	private PathAndBytesable<String> pathAndBytesable;

	@Before
	public void setup() throws Exception {
		when(client.getChildren().forPath(anyString())).thenReturn(Collections.emptyList());
		when(client.create().creatingParentsIfNeeded().inBackground()).thenReturn(pathAndBytesable);
	}

	@Test
	public void shouldListRootNodeWhenPathIsNull() throws Exception {
		ZNode result = subject.list(null);

		verify(client.getChildren()).forPath("/");
		assertNotNull(result);
	}

	@Test
	public void shouldPreAppendSlashOnPathWhenPathDoNotStartsWithIt() throws Exception {
		ZNode result = subject.list("path");

		verify(client.getChildren()).forPath("/path");
		assertNotNull(result);
	}

	@Test
	public void shouldListPath() throws Exception {
		ZNode result = subject.list("/path");

		verify(client.getChildren()).forPath("/path");
		assertNotNull(result);
	}

	@Test
	public void shouldListRootNodeWhenPathIsBlank() throws Exception {
		ZNode result = subject.list(" ");

		verify(client.getChildren()).forPath("/");
		assertNotNull(result);
	}

	@Test
	public void shouldCreateNodeWithoutContentWhenDataIsNull() throws Exception {
		subject.create("/path", null);

		verify(pathAndBytesable).forPath("/path");
	}

	@Test
	public void shouldCreateNodeWithoutContentWhenDataIsBlank() throws Exception {
		subject.create("/path", " ");

		verify(pathAndBytesable).forPath("/path");
	}

	@Test
	public void shouldCreateNodeWithContentWhenDataIsNotBlank() throws Exception {
		subject.create("/path", "some");

		verify(pathAndBytesable).forPath("/path", "some".getBytes());
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToUpdateZookeeperNode() {
		subject.updateContent("/zookeeper", null);
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToUpdateZookeeperChildrenNode() {
		subject.updateContent("/zookeeper/some", null);
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToCreateNodeInsideZookeeperNode() {
		subject.create("/zookeeper/some", null);
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToDeleteNodeInsideZookeeperNode() {
		subject.delete("/zookeeper/some");
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToDeleteZookeeperNode() {
		subject.delete("/zookeeper");
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToDeleteRootNode() {
		subject.delete("/");
	}

	@Test(expected = ZNodeModificationNotAllowed.class)
	public void shouldThrowZNodeModificationNotAllowedWhenTryingToUpdateRootNodeContent() {
		subject.updateContent("/", null);
	}
}