package com.github.gmcoringa.coordinator.core.znode;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.gmcoringa.coordinator.common.ZNode;

@Service
public class ZNodeService {

	private static final Logger LOG = LoggerFactory.getLogger(ZNodeService.class);
	private final CuratorFramework client;

	@Autowired
	public ZNodeService(CuratorFramework client) {
		this.client = client;
	}

	public ZNode list(String path) {
		try {
			return new ZNode(path, client.getChildren().watched().forPath("/"));
		} catch (Exception e) {
			// TODO create exception
			throw new RuntimeException(e);
		}
	}

}
