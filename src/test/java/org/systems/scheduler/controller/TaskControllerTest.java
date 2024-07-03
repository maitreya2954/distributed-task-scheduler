package org.systems.scheduler.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.systems.scheduler.model.Task;
import org.systems.scheduler.model.TaskType;
import org.systems.scheduler.service.TaskService;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
class TaskControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TaskService taskService;
	
	@Test
	public void testSubmitTask() throws Exception {
        Task task = new Task(null, TaskType.TYPE1, 1, null, "payload");
        String taskId = "TYPE1-123";
        Mockito.when(taskService.submitTask(Mockito.any(Task.class))).thenReturn(taskId);
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"TYPE1\",\"priority\":1,\"payload\":\"payload\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task submitted successfully with ID: " + taskId));
	}
	
}
