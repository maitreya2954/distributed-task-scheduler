package org.systems.scheduler.strategy;

import org.springframework.stereotype.Component;

@Component("TYPE2")
public class Type2TaskExecutionStrategy implements TaskExecutionStrategy {

	@Override
	public void executeTask(String taskId) {
		// Execute type 2 tasks
		System.out.println("Executing type 2 task: " + taskId);
	}

}
