package org.systems.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskDistributor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskDistributor.class);
	
	private static final String TASK_CHANNEL = "task-channel";
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void distributeTask(String taskId) {
		// Publish task ID to redis channel
        try {
        	stringRedisTemplate.convertAndSend(TASK_CHANNEL, taskId);
            LOGGER.info("Task distributed successfully: {}", taskId);
        } catch (Exception e) {
        	LOGGER.error("Failed to distribute task: {}", taskId, e);
            throw new RuntimeException("Failed to distribute task", e);
        }
	}
}
