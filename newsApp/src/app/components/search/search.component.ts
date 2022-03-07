import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form!: FormGroup;


  constructor(private fb: FormBuilder, private router: Router, private newsSvc: NewsService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      query: this.fb.control('', [Validators.required])
    })
  }

  onSubmit() {
    let query = this.form.value.query;
    this.router.navigate(['/result', query]);


  }

}
