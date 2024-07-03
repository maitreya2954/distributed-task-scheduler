package org.systems.scheduler.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.systems.scheduler.model.Task;

@Service
public class TaskService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	TaskDistributor taskDistributor;
	
	public String submitTask(Task task) {
		String taskId = UUID.randomUUID().toString() + "-" + task.getType();
		String taskKey = "task:" + taskId;
		
		 try {
            stringRedisTemplate.opsForHash().put(taskKey, "priority", String.valueOf(task.getPriority()));
            stringRedisTemplate.opsForHash().put(taskKey, "payload", task.getPayload());

            taskDistributor.distributeTask(taskId)	;

            LOGGER.info("Task submitted successfully with ID: {}", taskId);
        } catch (Exception e) {
            LOGGER.error("Failed to submit task", e);
            throw new RuntimeException("Failed to submit task", e);
        }
		
		return taskId;
	}
	
}
