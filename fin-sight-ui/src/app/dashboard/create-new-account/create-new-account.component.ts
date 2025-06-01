import { Component, inject, OnInit } from '@angular/core';
import { Dialog, DialogRef, DIALOG_DATA, DialogModule } from '@angular/cdk/dialog';
import { MaterialModule } from '../../shared/material.module';
import { AbstractControl, FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-new-account',
  imports: [MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './create-new-account.component.html',
  styleUrl: './create-new-account.component.scss'
})
export class CreateNewAccountComponent {
  dialogRef = inject<DialogRef<string>>(DialogRef<string>);
  data = inject(DIALOG_DATA);
}
