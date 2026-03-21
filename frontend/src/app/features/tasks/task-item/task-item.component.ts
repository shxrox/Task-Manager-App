import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Task } from '../../../shared/models/task.model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-task-item',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  template: `
    <mat-card class="task-card">
      <mat-card-content>
        <div class="task-info">
          <h3>{{ task.title }}</h3>
          <p>{{ task.description }}</p>
          <mat-chip-set>
            <mat-chip>{{ task.status }}</mat-chip>
          </mat-chip-set>
        </div>
        <div class="task-actions">
          <button mat-icon-button color="warn" (click)="onDelete()">
            <mat-icon>delete</mat-icon>
          </button>
        </div>
      </mat-card-content>
    </mat-card>
  `,
  styles: [`
    .task-card { margin-bottom: 1rem; }
    mat-card-content { display: flex; justify-content: space-between; align-items: center; }
  `]
})
export class TaskItemComponent {
  // @Input allows the Parent to "inject" a task into this component
  @Input() task!: Task; 

  // @Output allows this component to "notify" the Parent when a button is clicked
  @Output() deleteRequest = new EventEmitter<number>();

  onDelete() {
    this.deleteRequest.emit(this.task.id);
  }
}