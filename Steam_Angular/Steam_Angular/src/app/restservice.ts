import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RestCallService {

  constructor(private http: HttpClient) { }

  getHeader() {
    const headers = new HttpHeaders();
    headers.append('content-type', 'application/json');

    return headers;
  }

  buildParams(filters: {}) {
    let params = new HttpParams();
    for (let key in filters) {
      if (filters[key]) {
        params = params.append(key, filters[key].toString());
      }
    }
    return params;
  }

  public getAllSeries() {
    console.log("GET");
    
    return this.http.get('http://localhost:8080/steam_v2-master/resources/series', {
      observe: 'response',
      headers: this.getHeader()
    });
  }

  public login(username, password) {
    console.log("GET LOGIN");

    var token = username + ":" + password;

    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': btoa(token)
    })
    
    return this.http.get('http://localhost:8080/steam_v2-master/resources/users/login', {
      observe: 'response',
      headers: headers
    });
  }

  public register(username, password) {
    console.log("PUT REGISTER");

    var user = {
      "password": password,
      "username": username
    };

    return this.http.post('http://localhost:8080/steam_v2-master/resources/register', user ,{
      observe: 'response',
      headers: this.getHeader()
    });
  }
}