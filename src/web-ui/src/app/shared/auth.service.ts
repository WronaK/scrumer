import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from '../model/user';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private customHttpClient: HttpClient;

  constructor(
    private http: HttpClient,
    private router: Router,
    private backend: HttpBackend
  ) {
    this.customHttpClient = new HttpClient(backend);
  }

  signIn(credentials: any) {
    return this.http.post('api/login', {
      email: credentials.email,
      password: credentials.password
    }, {observe: 'response'})
      .subscribe(
        response => {
          const token = response.headers.get('authorization');
          if(token !== null) {
            localStorage.setItem('access_token', token.replace('Bearer ', ''));
            this.router.navigate(['dashboard']);
          }
        }
      )
  }

  signUp(user: User): Observable<string> {
    return this.customHttpClient.post('api/users', user, {responseType: 'text'});
  }

  getToken() {
    return localStorage.getItem('access_token');
  }

  get isLoggedIn(): boolean {
    const token = localStorage.getItem('access_token');
    return (token !== null);
  }

  logout() {
    const removeToken = localStorage.removeItem('access_token');
    if(removeToken === null) {
      this.router.navigate(['login']);
    }
  }

  getUserData() {
    return this.http.get<User>('api/users');
  }
}
