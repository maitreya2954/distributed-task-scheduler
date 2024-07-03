package org.systems.scheduler.service;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;

class TaskDistributorTest {
	
	@Autowired
	private TaskDistributor taskDistributor;

	@MockBean
	private StringRedisTemplate stringRedisTemplate;
	
	public void testDistributeTask() throws Exception {
		String taskId = "TYPE1-99999";
		Mockito.doNothing().when(stringRedisTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString());
		
		taskDistributor.distributeTask(taskId);
		
		Mockito.verify(stringRedisTemplate, Mockito.times(1)).convertAndSend("task-channel", taskId);
	}

}
