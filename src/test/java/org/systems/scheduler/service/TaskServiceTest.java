package org.systems.scheduler.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.systems.scheduler.model.Task;
import org.systems.scheduler.model.TaskType;

class TaskServiceTest {

	@Autowired
	private TaskService taskService;
	
	@MockBean
	private StringRedisTemplate stringRedisTemplate;
	
	@MockBean
	private TaskDistributor taskDistributor;
	
	@Test
	public void testSubmitTask() {
		Task task = new Task(null, TaskType.TYPE1, 1, null, "payload");
		Mockito.doNothing().when(stringRedisTemplate).opsForHash().put(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		Mockito.doNothing().when(taskDistributor).distributeTask(Mockito.anyString());
		
		String taskId = taskService.submitTask(task);
		
		assertNotNull(taskId);
		assertTrue(taskId.startsWith("TYPE1-"));
	}
}
