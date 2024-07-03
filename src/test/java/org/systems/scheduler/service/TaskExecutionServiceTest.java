package org.systems.scheduler.service;


import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.systems.scheduler.model.TaskType;
import org.systems.scheduler.strategy.TaskExecutionStrategy;

class TaskExecutionServiceTest {
	
	@Autowired
	TaskExecutionService taskExecutionService;
	
	@MockBean
	private Map<TaskType, TaskExecutionStrategy> taskExecutionStrategies;
	
	@MockBean
	private TaskExecutionStrategy type1ExecutionStrategy;

	public void testExecuteTask() throws Exception {
		String taskId = "TYPE1-9999";
		
		Mockito.when(taskExecutionStrategies.get(TaskType.TYPE1)).thenReturn(type1ExecutionStrategy);
		Mockito.doNothing().when(taskExecutionService).executeTask(Mockito.anyString());
		
		taskExecutionService.executeTask(taskId);
		
		Mockito.verify(type1ExecutionStrategy, Mockito.times(1)).executeTask(taskId);
	}

}
