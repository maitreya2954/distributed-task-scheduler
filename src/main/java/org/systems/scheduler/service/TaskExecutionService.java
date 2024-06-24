package org.systems.scheduler.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.systems.scheduler.model.TaskType;
import org.systems.scheduler.strategy.TaskExecutionStrategy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
class TaskExecutionService {
	
	@Autowired
	private Map<TaskType, TaskExecutionStrategy> taskExecutionStrategies;
	
	@CircuitBreaker(name="taskExecution", fallbackMethod = "handleTaskExecutionFailure")
	public void executeTask(String taskId) {
		
		System.out.println("Executing task: " + taskId);
		
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
                System.out.println("Failed to execute task: " + taskId);
			}
            
        } else {
            // Handle unknown task type
            System.out.println("Unknown task type: " + taskType);
        }
        
	}
	
	public void handleTaskExecutionFailure(String taskId, Throwable throwable) {
		System.out.println("Task execution failed for task: " + taskId);
	}
	
}
