package com.treinetic.backend.service;

import com.treinetic.backend.entity.Task;
import com.treinetic.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // FIX: Get tasks filtered by the logged-in User's ID
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // FIX: Verify the task belongs to the user before updating
    public Task updateTask(Long id, Task taskDetails, Long userId) {
        return taskRepository.findById(id).map(existingTask -> {
            // Security check: Does this task belong to the person trying to edit it?
            if (!existingTask.getUser().getId().equals(userId)) {
                throw new RuntimeException("Unauthorized: You do not own this task");
            }

            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setStatus(taskDetails.getStatus());
            // Update other fields if necessary (e.g., priority, dueDate)

            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    // FIX: Verify ownership before deleting
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You do not own this task");
        }

        taskRepository.delete(task);
    }
}