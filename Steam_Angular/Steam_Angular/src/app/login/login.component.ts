import { Component, OnInit } from '@angular/core';
import { RestCallService } from '../restservice';
import { Config } from 'protractor';
import { AppRoutingModule } from '../app-routing.module';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.template.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  username: string;
  password: string;
  loggedIn: boolean;
  error: string;

    constructor(private rest: RestCallService, private router: Router) {
        this.username = "";
        this.password = "";
        this.loggedIn = false;
    }

    isLoggedIn() {
      return this.loggedIn;
    }

    login() {
      this.rest.login(this.username, this.password)
      .subscribe(
        (result: Config) => {
          if( result.status == 200) {
            this.loggedIn = true;
            this.router.navigate(["series"]);
          }
        },
        error => {
          this.loggedIn = false;
          this.error = 'Falsche Anmeldedaten!';
        }
      )
    }

    getError() {
      return this.error;
    }

    register() {
      this.rest.register(this.username, this.password)
      .subscribe(
        (result: Config) => {
          if( result.status == 200 && result.body) {
            this.loggedIn = true;
            this.router.navigate(["series"]);
          }
          else {
            this.error = 'Username bereits vergeben';
          }
        },
        error => {
          this.loggedIn = false;
          this.error = 'Username bereits vergeben';
          
        }
      )
    }
}
