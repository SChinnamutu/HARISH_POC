package com.mufg.us.amh.vln_ced_401.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "depiris")
public class MessageReader {
	private Map<String, QueueInfo> queue;

	public Map<String, QueueInfo> getQueue() {
		return queue;
	}

	public void setQueue(Map<String, QueueInfo> queue) {
		this.queue = queue;
	}

	
}
