package org.systems.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TaskSubscriber implements MessageListener {

	private static final String TASK_CHANNEL = "task-channel";
	
	@Autowired
	private TaskExecutionService taskExecutionService;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// Receive taskId from redis channel
		String taskId = new String(message.getBody());
		System.out.println("Received task ID: " + taskId);
		
        taskExecutionService.executeTask(taskId);
        
	}
	
}
