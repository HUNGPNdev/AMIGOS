package com.amigos.contact;

import com.amigos.common.ResponseApi;
import com.amigos.contact.model.ContactEntity;

import java.util.UUID;

public interface ContactService {
    ResponseApi addContact(ContactEntity contact);
    ResponseApi updateContact(ContactEntity contact);
    ResponseApi getDetailContact(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getAll();
}
