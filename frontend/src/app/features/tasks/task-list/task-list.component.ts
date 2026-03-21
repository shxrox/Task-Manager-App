import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { TaskService } from '../../../core/services/task.service';
import { Task } from '../../../shared/models/task.model';
import { TaskItemComponent } from '../task-item/task-item.component'; // 1. Added Import

// Angular Material Imports
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    FormsModule, 
    TaskItemComponent, // 2. Added to Imports array
    MatTableModule, 
    MatButtonModule, 
    MatIconModule, 
    MatChipsModule, 
    MatSelectModule, 
    MatFormFieldModule,
    MatCardModule
  ],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit {
  allTasks: Task[] = [];
  filteredTasks: Task[] = [];
  selectedStatus: string = 'ALL';
  
  // Table configuration
  displayedColumns: string[] = ['title', 'description', 'status', 'createdAt', 'actions'];

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    // Note: If you updated your backend to use DTOs, ensure your 
    // TaskService.getAllTasks() returns Observable<Task[]>
    this.taskService.getAllTasks().subscribe({
      next: (data: Task[]) => {
        this.allTasks = data;
        this.applyFilter();
      },
      error: (err) => console.error('Error fetching tasks', err)
    });
  }

  applyFilter(): void {
    if (this.selectedStatus === 'ALL') {
      this.filteredTasks = [...this.allTasks];
    } else {
      this.filteredTasks = this.allTasks.filter(t => t.status === this.selectedStatus);
    }
  }

  onDelete(id: number): void {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => {
          this.allTasks = this.allTasks.filter(t => t.id !== id);
          this.applyFilter();
        },
        error: (err) => console.error('Error deleting task', err)
      });
    }
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'DONE': return 'primary';
      case 'IN_PROGRESS': return 'accent';
      case 'TO_DO': return 'warn';
      default: return '';
    }
  }
}