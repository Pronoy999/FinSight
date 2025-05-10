import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class FinSightAuthService {
    private baseUrl = 'http://localhost:8080/';

    constructor(private http: HttpClient) {
    }

    isLoginSuccess(data: any): Observable<HttpResponse<any>> {
        const apiURL = this.baseUrl + 'user/login';
        const headers = new HttpHeaders({'Content-Type': 'application/json'});
        return this.http.post<any>(apiURL, data, {
            headers: headers,
            observe: 'response' // 👈 enables status code access
        });
    }
}
