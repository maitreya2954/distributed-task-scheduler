package org.systems.scheduler.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.systems.scheduler.strategy.TaskExecutionStrategy;

public class TaskSubscriber implements MessageListener {

	private static final String TASK_CHANNEL = "task-channel";
	
	@Autowired
	private Map<String, TaskExecutionStrategy> taskExecutionStrategies;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// Receive taskId from redis channel
		String taskId = new String(message.getBody());
		System.out.println("Received task ID: " + taskId);
		
        // Extract task type from taskId (assuming taskId format: typeX-taskId)
        String taskType = taskId.split("-")[0];
        
        // Get appropriate strategy based on task type and execute task
        TaskExecutionStrategy strategy = taskExecutionStrategies.get(taskType);
        if (strategy != null) {
            strategy.executeTask(taskId);
        } else {
            // Handle unknown task type
            System.out.println("Unknown task type: " + taskType);
        }
        
	}
	
}
