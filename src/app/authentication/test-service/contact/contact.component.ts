import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '../../entity/contact/Contact';
import { ContactService } from '../../entity/contact/contact.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  contact: Contact = new Contact();
  contacts: Contact[];
  searchText: string;
  c:number;
  id: number = 0;

  constructor(private contactService: ContactService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.createContact();
  }

  createContact() {
    this.contactService.createContact(this.contact).subscribe( data => {
      this.contact = new Contact();
    }, error => console.log(error));
  }

}
