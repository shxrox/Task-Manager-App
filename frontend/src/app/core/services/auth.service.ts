import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User, LoginRequest, AuthResponse } from '../../shared/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  // 1. Initialize BehaviorSubject based on current token status
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  // Helper to check localStorage
  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  // 2. Expose as an Observable so components can subscribe to changes
  get isLoggedIn$(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token); 
          // 3. Notify all subscribers that we are now logged in
          this.loggedIn.next(true);
        }
      })
    );
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }

  logout(): void {
    localStorage.removeItem('token');
    // 4. Notify all subscribers that we are now logged out
    this.loggedIn.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}