import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, User } from '@auth0/auth0-angular';
import { message } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent implements OnInit {
  form!: FormGroup;
  isAuthenticated: User | undefined | null = this.auth.user$.subscribe(user => this.isAuthenticated = user);


  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService, private fb: FormBuilder, private newsSvc: NewsService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control('', [Validators.required, Validators.email]),
      message: this.fb.control('', [Validators.required, Validators.maxLength(300)])
    })

    // await this.auth.isAuthenticated$.subscribe(bool => {
    //   if (bool === true) {
    //     this.router.navigate(['/landingpage']);
    //   }
    // })
  }


  async onSubmit() {
    let msg = this.form.value as message;
    await this.newsSvc.sendEmail(msg);
    alert("You query has been sent! You will receive a confirmation email in your inbox shortly!")
  }

}
