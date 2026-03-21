package com.treinetic.backend.controller;

import com.treinetic.backend.entity.Task;
import com.treinetic.backend.entity.User;
import com.treinetic.backend.repository.UserRepository;
import com.treinetic.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @Autowired
    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    // Helper method to get the logged-in user
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        // Filter by the current user's ID
        return taskService.getTasksByUserId(getCurrentUser().getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        return taskService.getTaskById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId())) // Security Check
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        // Link the task to the current user before saving
        task.setUser(getCurrentUser());
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        User currentUser = getCurrentUser();
        try {
            // Your Service should ideally verify that the task belongs to this user
            Task updatedTask = taskService.updateTask(id, taskDetails, currentUser.getId());
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        // Pass userId to service to ensure they can't delete someone else's task
        taskService.deleteTask(id, currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}