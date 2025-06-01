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
        setTimeout(() => {
            this.finSightAuthService.initGoogleAuth().then(() => {
                setTimeout(() => {
                    this.finSightAuthService.renderButton('google-signin-button');
                }, 0);
            }).catch((error) => {
                console.error('Google Auth Init Error:', error);
            });
        }, 0);
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

// response: {
//     "guid": "d1122722-c068-4014-aade-d6e321e57c51",
//     "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InByYWtyaXRpcDIyQGdtYWlsLmNvbSIsImd1aWQiOiJkMTEyMjcyMi1jMDY4LTQwMTQtYWFkZS1kNmUzMjFlNTdjNTEiLCJ0aGlyZFBhcnR5VG9rZW4iOiJleUpoYkdjaU9pSlNVekkxTmlJc0ltdHBaQ0k2SW1KaFlUWTBaV1pqTVRObFpqSXpObUpsT1RJeFpqa3lNbVV6WVRZM1kyTTVPVFF4TldSaU9XSWlMQ0owZVhBaU9pSktWMVFpZlEuZXlKcGMzTWlPaUpvZEhSd2N6b3ZMMkZqWTI5MWJuUnpMbWR2YjJkc1pTNWpiMjBpTENKaGVuQWlPaUk1TWpjd05EUTVOemd4TWpFdFptTXlZelZxWldOdll6RnRZMk56T0hKMVluVnlkV3cwWTNKMGJUTnhhR1F1WVhCd2N5NW5iMjluYkdWMWMyVnlZMjl1ZEdWdWRDNWpiMjBpTENKaGRXUWlPaUk1TWpjd05EUTVOemd4TWpFdFptTXlZelZxWldOdll6RnRZMk56T0hKMVluVnlkV3cwWTNKMGJUTnhhR1F1WVhCd2N5NW5iMjluYkdWMWMyVnlZMjl1ZEdWdWRDNWpiMjBpTENKemRXSWlPaUl4TVRReU5UTXhOemN6TlRVMk5UZzFOakEwTXpFaUxDSmxiV0ZwYkNJNkluQnlZV3R5YVhScGNESXlRR2R0WVdsc0xtTnZiU0lzSW1WdFlXbHNYM1psY21sbWFXVmtJanAwY25WbExDSnVZbVlpT2pFM05EZzNPRFkzTlRJc0ltNWhiV1VpT2lKUWNtRnJjbWwwYVNCUVlXNXFZU0lzSW5CcFkzUjFjbVVpT2lKb2RIUndjem92TDJ4b015NW5iMjluYkdWMWMyVnlZMjl1ZEdWdWRDNWpiMjB2WVM5QlEyYzRiMk5MVkRsRU5VdzNTMVUwVmtoQ1NsQnJOMkZvZUdabmJtZGFhbmd6WWpsWE4yVlFia2xDY1dFdFNuQnlXVkJ6T1UxaVlqMXpPVFl0WXlJc0ltZHBkbVZ1WDI1aGJXVWlPaUpRY21GcmNtbDBhU0lzSW1aaGJXbHNlVjl1WVcxbElqb2lVR0Z1YW1FaUxDSnBZWFFpT2pFM05EZzNPRGN3TlRJc0ltVjRjQ0k2TVRjME9EYzVNRFkxTWl3aWFuUnBJam9pWVROaE9HUTFabVJoWkRKa01qTTFNekpsWkRSbU4yVTRZV1ZtTWpBd05EZ3lOMlUxTXpjM01DSjkuUEowWUo0OVJORGdIN2hudlpfTW9ZeTdGd2NpQ21fTUthOXdzU3pxVXgyZzcxWS0wbHBSdlgyRkZaeTNmMXV1WkpmUGg1enMwaU16R2J1aEhhTmtnNFV1b3hRUV9lbV90SVQ3R2RmMVlab1FrZWhYWEpMdjNBNXhsTWxhWDZ6T0xfdUNoMDQxLThjMFhjeHFFQWhMSDRRdFVUNnNqQVFRcjdsM0dwNUg3NHRUQlRHbnYzWHJKeXpPX0ZoZm9lV01XMnVJRGRsVjZKY3VxRzlkRUtWdm9Jd2J3MWhQbnpfdFUwazlTa0RISkRzNU1ab1BJWVJNWm8za3o5ZElsTkdqd2NiREVYOHNySi11QXhNN0FpWFMtYUNFTlJPZ3JQdUpxTXRJeWlvUGlYVWhOd01rWUZwZnFnYjlEUkpSZU14QTJmUnIyOXRJNEFrME5JOWduTmVmcThBIn0.CG8ISXLHNDgSygy_hpZI1pF8AbDlYOTXZ6MndMyFiHc"
// }