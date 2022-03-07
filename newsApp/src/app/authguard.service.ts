import { Injectable, OnInit } from '@angular/core';
import { Router, CanActivate, CanDeactivate } from '@angular/router';
import { AuthService, AuthState, User } from '@auth0/auth0-angular';


@Injectable({
    providedIn: 'root'
})
export class AuthGuardService implements CanActivate, OnInit {
    isAuthenticated!: boolean;

    constructor(public auth: AuthState, public router: Router) {
    }

    ngOnInit(): void {
        this.auth.isAuthenticated$.subscribe(val => {
            this.isAuthenticated = val;
        })
    }

    canActivate(): boolean {
        if (this.isAuthenticated === true) {
            this.router.navigate(['/landingpage']);
            return false;
        }
        return true;
    }
}