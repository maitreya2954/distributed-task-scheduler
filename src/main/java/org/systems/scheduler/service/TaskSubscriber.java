package org.systems.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class TaskSubscriber implements MessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSubscriber.class);

	private static final String TASK_CHANNEL = "task-channel";
	
	@Autowired
	private TaskExecutionService taskExecutionService;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// Receive taskId from redis channel
		String taskId = new String(message.getBody());
		LOGGER.info("Received task ID: " + taskId);
		
		try {
			taskExecutionService.executeTask(taskId);
		} catch (Exception e) {
			LOGGER.error("Error processing task: {}", taskId, e);
		}
        
        
	}
	
}
