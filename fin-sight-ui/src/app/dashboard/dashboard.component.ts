import { Component, inject, OnInit } from '@angular/core';
import { Dialog, DialogRef, DIALOG_DATA, DialogModule } from '@angular/cdk/dialog';
import { MaterialModule } from '../shared/material.module';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [MaterialModule, DialogModule],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
})


export class DashboardComponent implements OnInit {

    dialog = inject(Dialog);

    constructor(

    ) { }

    ngOnInit() {
    }


    // createNewAccount(): void {
    //     const dialogRef = this.dialog.open<string>(CreateNewAccountComponent, {
    //         width: '250px',
    //         data: { accountName: '', accountType: '' },
    //     });

    //     dialogRef.closed.subscribe(result => {
    //         console.log('The dialog was closed', result);
    //         localStorage.getItem("loggedInUserJWT")
    //     });
    // }


}



export interface AccountCreateData {
    accountName: string;
    accountType: string;
}
