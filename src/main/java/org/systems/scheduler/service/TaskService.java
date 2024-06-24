package org.systems.scheduler.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.systems.scheduler.model.Task;

@Service
public class TaskService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private TaskDistributor taskDistributor;
	
	public String submitTask(Task task) {
		String taskId = UUID.randomUUID().toString() + "-" + task.getType();
		String taskKey = "task:" + taskId;
		
		stringRedisTemplate.opsForHash().put(taskKey, "priority", String.valueOf(task.getPriority()));
		stringRedisTemplate.opsForHash().put(taskKey, "payload", task.getPayload());
		
		taskDistributor.distributeTask(taskId);
		
		return taskId;
	}
	
}
