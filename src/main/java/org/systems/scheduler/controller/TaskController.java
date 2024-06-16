package org.systems.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.systems.scheduler.model.Task;
import org.systems.scheduler.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/tasks")
	public ResponseEntity<String> submitTask(@RequestBody Task task) {
		String taskId = taskService.submitTask(task);
		return ResponseEntity.ok("Task submitted successfully with ID: " + taskId);
	}
	
}
