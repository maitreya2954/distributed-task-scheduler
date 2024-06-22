package org.systems.scheduler.strategy;

public class Type1TaskExecutionStrategy implements TaskExecutionStrategy {

	@Override
	public void executeTask(String taskId) {
		// Execute type 1 task
		System.out.println("Executing type 1 task " + taskId);
	}

}
