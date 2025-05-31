import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MaterialModule } from '../shared/material.module';
import { HttpResponse } from '@angular/common/http';
import { FinSightAuthService } from '../shared/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [MaterialModule, CommonModule, FormsModule, ReactiveFormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})

export class LoginComponent implements OnInit {

    @ViewChild('registerFormSideNav') registerFormSideNav: any;
    hide: boolean = true;
    showSignIn: boolean = true;
    emailValidationPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$";
    specialCharactersPattern = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

    designOption1: boolean = true;

    constructor(private fb: FormBuilder,
        private router: Router, private finSightAuthService: FinSightAuthService) {
    }

    public loginForm: any

    public registerForm: any;


    ngOnInit(): void {
        this.loginForm = this.fb.group({
            emailId: new FormControl('', [Validators.required]),
            password: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
            rememberMe: new FormControl('')
        });

        this.registerForm = this.fb.group({
            firstName: new FormControl('', [Validators.required]),
            lastName: new FormControl('', [Validators.required]),
            emailId: new FormControl('', [Validators.required]),
            phoneNumber: new FormControl('', [Validators.required]),
            age: new FormControl('', [Validators.required]),
            password: new FormControl('', [Validators.required])
        });
    }

    ngAfterViewInit(): void {
        this.finSightAuthService.initGoogleAuth().then(() => {
            // Wait a tick to ensure the DOM is fully painted
            setTimeout(() => {
                this.finSightAuthService.renderButton('google-signin-button');
            }, 0);
        }).catch((error) => {
            console.error('Google Auth Init Error:', error);
        });
    }

    public passwordPatternValidator(regex: RegExp) {
        return (control: AbstractControl): { [key: string]: any } => {
            if (!control.value) {
                return { 'passwordPatternValidator': false }
            }
            const valid = regex.test(control.value);
            return valid ? { 'passwordPatternValidator': false } : { 'passwordPatternValidator': true };
        };
    }

    signIn() {
        this.showSignIn = true;

        if (this.loginForm.valid) {
            console.log(this.loginForm.value);

            this.router.navigate(['/dashboard']);

            this.finSightAuthService.isLoginSuccess(this.loginForm.value).subscribe({
                next: (response: HttpResponse<any>) => {
                    console.log('Status Code:', response.status);
                    if (response.status === 200) {
                        alert("Login Successful");
                        console.log(response)
                        localStorage.setItem("loggedInUserJWT", response.body.jwt)
                        this.router.navigate(['/dashboard']);
                    } else {
                        alert("Login Failed");
                    }
                },
                error: (error) => {
                    console.error('Login Error:', error);
                    alert("Login Failed");
                }
            });
        }
    }

    register() {
        this.showSignIn = false;
        if (this.registerForm.valid) {
            console.log(this.registerForm.value);

            this.finSightAuthService.registerUser(this.registerForm.value).subscribe({
                next: (response: HttpResponse<any>) => {
                    console.log('Status Code:', response.status);
                    if (response.status === 200) {
                        alert("User Registration Successful");
                    } else {
                        alert("User Registration Failed");
                    }
                },
                error: (error) => {
                    console.error('User Registration Error:', error);
                    alert("User Registration Failed");
                }
            });
        }
    }

    signup() {
        this.registerFormSideNav.open();
    }

    goBackToLogin() {
        this.registerFormSideNav.close();
    }
}
