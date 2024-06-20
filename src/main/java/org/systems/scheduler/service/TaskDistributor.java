package org.systems.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskDistributor {
	
	private static final String TASK_CHANNEL = "task-channel";
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void distributeTask(String taskId) {
		// Publish task ID to redis channel
		stringRedisTemplate.convertAndSend(TASK_CHANNEL, taskId);
	}
}
