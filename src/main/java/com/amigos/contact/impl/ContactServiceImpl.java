package com.amigos.contact.impl;

import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.contact.ContactService;
import com.amigos.contact.model.ContactEntity;
import com.amigos.contact.repository.ContactRepository;
import com.amigos.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    @Override
    public ResponseApi addContact(ContactEntity contact) {
        contact.setIsDeleted(Boolean.FALSE);
        ContactEntity contactEntity = contactRepository.save(contact);
        ContactDTO contactDTO = modelMapper.map(contactEntity, ContactDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), contactDTO);
        return rs;
    }

    @Override
    public ResponseApi updateContact(ContactEntity contact) {
        Optional<ContactEntity> find = contactRepository.findById(contact.getId());
        if (!find.isEmpty()) {
            ContactEntity contactEntity = contactRepository.save(contact);
            ContactDTO contactDTO = modelMapper.map(contactEntity, ContactDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), contactDTO);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getDetailContact(UUID id) {
        Optional<ContactEntity> contactEntity = contactRepository.findById(id);
        if(!contactEntity.isEmpty()) {
            ContactDTO map = modelMapper.map(contactEntity.get(), ContactDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi delete(UUID id) {
        Optional<ContactEntity> contactEntity = contactRepository.findById(id);
        if(!contactEntity.isEmpty()) {
            ContactEntity entity = contactEntity.get();
            entity.setIsDeleted(true);
            contactRepository.save(entity);
            ContactDTO map = modelMapper.map(entity, ContactDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getAll() {
        List<ContactDTO> contactEntity = contactRepository.getAll(Boolean.FALSE);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), modelMapper.mapAll(contactEntity, ContactDTO.class));
        return rs;
    }
}
