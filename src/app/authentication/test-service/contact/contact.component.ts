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
      console.log(this.getAllContact());
      
    this.getAllContact();
  }

  onSubmit() {
    if(this.id != 0) {
      this.contact.id = this.id;
      this.updateById();
    } else {
      this.createContact();
    }
  }

  getAllContact() {
    this.contactService.listContact().subscribe( data => {
      this.contacts = data.data;
    }, error => console.log(error))
  }

  createContact() {
    this.contactService.createContact(this.contact).subscribe( data => {
      alert("Create Successfully!");
      location.reload();
      this.getAllContact();
    }, error => console.log(error));
  }

  getContactById(id: number) {
    this.contactService.getContactById(id).subscribe( data => {
      this.contact = data.data;
      this.id = id;
    })
  }

  updateById() {
    this.contactService.updateById(this.contact).subscribe( data => {
      this.id = 0;
      alert("Update Successfully!");
      location.reload();
      this.getAllContact();
    }, error => console.log(error))
  }

  deleteById(id: number) {
    this.contactService.deleteById(id).subscribe( data => {
      this.getAllContact();
    }, error => console.log(error))
  }
}
