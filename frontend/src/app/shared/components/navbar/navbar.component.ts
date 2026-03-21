import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { Observable } from 'rxjs'; // 1. Import Observable

// Angular Material Imports
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    MatToolbarModule, 
    MatButtonModule, 
    MatIconModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  // 2. Define the observable stream
  isLoggedIn$: Observable<boolean>;

  constructor(public authService: AuthService, private router: Router) {
    // 3. Assign the observable from your service
    // Ensure this matches the name in your AuthService (e.g., isLoggedIn$)
    this.isLoggedIn$ = this.authService.isLoggedIn$; 
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}