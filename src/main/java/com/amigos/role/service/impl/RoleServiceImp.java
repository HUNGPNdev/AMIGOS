package com.amigos.role.service.impl;

import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.RoleDTO;
import com.amigos.role.repository.RoleRepository;
import com.amigos.role.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ModelMapperConfig modelMapperConfig;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseApi getAll() {
        List<RoleDTO> roleEntity = roleRepository.getAllUser();
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), roleEntity);
        return rs;
    }
}
