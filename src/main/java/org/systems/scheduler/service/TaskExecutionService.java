package org.systems.scheduler.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.systems.scheduler.model.TaskType;
import org.systems.scheduler.strategy.TaskExecutionStrategy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
class TaskExecutionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutionService.class);
	
	@Autowired
	private Map<String, TaskExecutionStrategy> taskExecutionStrategies;
	
	@CircuitBreaker(name="taskExecution", fallbackMethod = "handleTaskExecutionFailure")
	public void executeTask(String taskId) {
		
		LOGGER.info("Executing task: " + taskId);
		
		// Extract task type from taskId (assuming taskId format: typeX-taskId)
        String taskType = taskId.split("-")[0];
        
        // Get appropriate strategy based on task type and execute task
        TaskExecutionStrategy strategy = taskExecutionStrategies.get(taskType);
        if (strategy != null) {
        	try {
        		strategy.executeTask(taskId);
        		//Simulate failure
                if (Math.random() < 0.5) {
                	throw new RuntimeException("Task execution failed for task: " + taskId);
                }
			} catch (Exception e) {
                // Handle task execution failure
                LOGGER.error("Failed to execute task: " + taskId);
			}
            
        } else {
            // Handle unknown task type
            LOGGER.warn("Unknown task type: " + taskType);
        }
        
	}
	
	public void handleTaskExecutionFailure(String taskId, Throwable throwable) {
		LOGGER.error("Task execution failed for task: " + taskId);
	}
	
}
