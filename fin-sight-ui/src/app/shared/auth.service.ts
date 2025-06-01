


import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

declare global {
    interface Window { google?: any; }
}

@Injectable({
    providedIn: 'root'
})
export class FinSightAuthService {

    private googleAuthInitialized = false;
    apiUrl = 'http://192.168.1.8:8080/';

    constructor(private router: Router, private http: HttpClient) { }

    loadGoogleScript(): Promise<void> {
        return new Promise((resolve, reject) => {
            if (window.google && window.google.accounts) {
                return resolve(); // Already loaded
            }

            const script = document.createElement('script');
            script.src = 'https://accounts.google.com/gsi/client';
            script.async = true;
            script.defer = true;
            script.onload = () => resolve();
            script.onerror = () => reject('Failed to load Google script');
            document.head.appendChild(script);
        });
    }

    initGoogleAuth(): Promise<void> {
        return this.loadGoogleScript().then(() => {
            if (!this.googleAuthInitialized) {
                window.google.accounts.id.initialize({
                    client_id: '927044978121-fc2c5jecoc1mccs8ruburul4crtm3qhd.apps.googleusercontent.com',
                    callback: this.handleCredentialResponse.bind(this),
                });
                this.googleAuthInitialized = true;
            }
        });
    }

    renderButton(containerId: string): void {
        if (window.google) {
            window.google.accounts.id.renderButton(
                document.getElementById(containerId),
                { theme: 'outline', size: 'large' }
            );
        }
    }

    handleCredentialResponse(response: any): void {
        const registerUserData = {
            "firstName": null,
            "lastName": null,
            "emailId": null,
            "phoneNumber": null,
            "age": null,
            "password": null,
            "googleOAuthToken": response.credential
        }
        console.log('Google Credential:', response.credential);
        alert('Google Sign-in Successful');

        console.log(response);

        this.http.post<any>(this.apiUrl + "user", registerUserData).subscribe({
            next: (res) => {
                console.log('User registered successfully:', res);
                localStorage.setItem("loggedInUserJWT", res.jwtToken);
                //this.router.navigate(['/dashboard']);
            }
            , error: (err) => {
                console.error('Error registering user:', err);
                alert('Error during registration. Please try again.');
            }
        });
    }

    isLoginSuccess(loginData: any): Observable<HttpResponse<any>> {
        return this.http.post<any>(this.apiUrl, loginData);
    }

    registerUser(registerUserData: any): Observable<any> {
        return this.http.post<any>(this.apiUrl, registerUserData);
    }
}
