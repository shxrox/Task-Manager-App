import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Task } from '../../../shared/models/task.model';

@Component({
  selector: 'app-task-item',
  standalone: true,
  template: `
    <div class="task-card">
      <h3>{{ task.title }}</h3>
      <p>{{ task.description }}</p>
      <button (click)="deleteRequest.emit(task.id)">Delete</button>
    </div>
  `
})
export class TaskItemComponent {
  @Input() task!: Task; 
  @Output() deleteRequest = new EventEmitter<number>(); 
}