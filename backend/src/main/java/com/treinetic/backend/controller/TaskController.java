package com.treinetic.backend.controller;

import com.treinetic.backend.dto.TaskDTO;
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

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Helper to convert Entity to DTO
    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getTasksByUserId(getCurrentUser().getId())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        return taskService.getTaskById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody Task task) {
        task.setUser(getCurrentUser());
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.ok(convertToDTO(savedTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        User currentUser = getCurrentUser();
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails, currentUser.getId());
            return ResponseEntity.ok(convertToDTO(updatedTask));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            taskService.deleteTask(id, currentUser.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}