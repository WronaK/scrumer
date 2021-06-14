import {Injectable} from '@angular/core';
import {HttpBackend, HttpClient} from '@angular/common/http';
import {User} from '../model/user/user';
import {Router} from "@angular/router";
import {LoginUser} from "../model/user/login.user";

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

  signUp(user: User) {
    return this.customHttpClient.post('api/users', user);
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
    return this.http.get<LoginUser>('api/users');
  }
}
