import {CommonModule} from '@angular/common';
import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MaterialModule} from '../material.module';
import {FinSightAuthService} from '../services/auth.service';
import {HttpResponse} from '@angular/common/http';

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
            registerEmail: new FormControl('', [Validators.required]),
            registerPassword: new FormControl('', [Validators.required])
        });
    }

    public passwordPatternValidator(regex: RegExp) {
        return (control: AbstractControl): { [key: string]: any } => {
            if (!control.value) {
                return {'passwordPatternValidator': false}
            }
            const valid = regex.test(control.value);
            return valid ? {'passwordPatternValidator': false} : {'passwordPatternValidator': true};
        };
    }

    signIn() {
        this.showSignIn = true;

        if (this.loginForm.valid) {
            console.log(this.loginForm.value);

            this.finSightAuthService.isLoginSuccess(this.loginForm.value).subscribe({
                next: (response: HttpResponse<any>) => {
                    console.log('Status Code:', response.status);
                    if (response.status === 200) {
                        alert("Login Successful");
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
        }
    }

    signup() {
        this.registerFormSideNav.open();
    }

    goBackToLogin() {
        this.registerFormSideNav.close();
    }
}
