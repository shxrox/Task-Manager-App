package com.treinetic.backend.service;

import com.treinetic.backend.entity.Task;
import com.treinetic.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Verify ownership before updating
    public Task updateTask(Long id, Task details, Long currentUserId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("You do not have permission to update this task");
        }

        task.setTitle(details.getTitle());
        task.setDescription(details.getDescription());
        task.setStatus(details.getStatus());
        return taskRepository.save(task);
    }

    // Verify ownership before deleting
    public void deleteTask(Long id, Long currentUserId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("You do not have permission to delete this task");
        }

        taskRepository.delete(task);
    }
}