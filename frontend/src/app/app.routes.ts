import { Routes } from '@angular/router';


{ 
  path: 'tasks/new', 
  loadComponent: () => import('./features/tasks/task-form/task-form.component').then(m => m.TaskFormComponent),
  canActivate: [authGuard] 
},
{ 
  path: 'tasks/edit/:id', 
  loadComponent: () => import('./features/tasks/task-form/task-form.component').then(m => m.TaskFormComponent),
  canActivate: [authGuard] 
},

export const routes: Routes = [];
