package com.github.gmcoringa.coordinator.core.zookeeper.node;

import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZNodeModificationNotAllowed;
import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZookeeperException;
import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZNodeService {

	private final CuratorFramework client;

	@Autowired
	ZNodeService(CuratorFramework client) {
		this.client = client;
	}

	public ZNode list(String path) {
		path = resolvePath(path).orElse("/");

		try {
			return new ZNode(path, client.getChildren().forPath(path));
		} catch (Exception e) {
			throw new ZookeeperException("Failed to load children for path: " + path, e);
		}
	}

	private Optional<String> resolvePath(String path) {
		String resolvedPath = Strings.nullToEmpty(path).trim();

		if (resolvedPath.isEmpty()) {
			return Optional.empty();
		}

		if (!resolvedPath.startsWith("/")) {
			resolvedPath = "/" + resolvedPath;
		}

		return Optional.of(resolvedPath);
	}

	public void create(String path, String data) {
		path = resolvePath(path).orElse("/");

		if (path.startsWith("/zookeeper")) {
			throw new ZNodeModificationNotAllowed(path);
		}

		try {
			data = Strings.nullToEmpty(data);

			if (data.trim().isEmpty()) {
				client.create().creatingParentsIfNeeded().inBackground().forPath(path);
			} else {
				client.create().creatingParentsIfNeeded().inBackground().forPath(path, data.getBytes());
			}
		} catch (Exception e) {
			throw new ZookeeperException("Failed to create path: " + path, e);
		}
	}

	public void updateContent(String path, String data) {
		path = resolvePath(path).orElse("/");
		validateZNodeModification(path);

		try {
			client.setData().inBackground().forPath(path, data.getBytes());
		} catch (Exception e) {
			throw new ZookeeperException("Failed to create path: " + path, e);
		}
	}

	public String getData(String path) {
		path = resolvePath(path).orElse("/");

		try {
			return new String(client.getData().forPath(path));
		} catch (Exception e) {
			throw new ZookeeperException("Failed to load data for path: " + path, e);
		}
	}

	public void delete(String path) {
		path = resolvePath(path).orElse("/");
		validateZNodeModification(path);

		try {
			client.delete().deletingChildrenIfNeeded().inBackground().forPath(path);
		} catch (Exception e) {
			throw new ZookeeperException("Failed to delete path: " + path, e);
		}
	}

	private void validateZNodeModification(String path) {
		if (path.startsWith("/zookeeper") || path.trim().equals("/")) {
			throw new ZNodeModificationNotAllowed(path);
		}
	}

}
