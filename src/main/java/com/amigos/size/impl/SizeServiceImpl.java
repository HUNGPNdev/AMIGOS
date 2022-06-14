package com.amigos.size.impl;

import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.SizeDTO;
import com.amigos.size.SizeService;
import com.amigos.size.model.SizeEntity;
import com.amigos.size.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class SizeServiceImpl implements SizeService
{
    @Autowired
    ModelMapperConfig modelMapper;

    @Autowired
    SizeRepository sizeRepository;

    @Override
    public ResponseApi addSize(SizeDTO size)
    {
        SizeEntity sizeCreate = new SizeEntity();
        sizeCreate.setName(size.getName());
        sizeCreate = sizeRepository.save(sizeCreate);

        size = modelMapper.map(sizeCreate, SizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), size);
        return rs;
    }

    @Override
    public ResponseApi updateSize(SizeDTO size)
    {
        Optional<SizeEntity> find = sizeRepository.findById(size.getId());
        if(find.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        SizeEntity sizeUpdate = find.get();
        modelMapper.map(size, sizeUpdate);
        sizeUpdate = sizeRepository.save(sizeUpdate);
        SizeDTO sizeDTO = modelMapper.map(sizeUpdate, SizeDTO.class);

        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), sizeDTO);
        return rs;
    }

    @Override
    public ResponseApi getDetailSize(UUID id)
    {
        Optional<SizeEntity> find = sizeRepository.findById(id);
        if(find.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        SizeDTO sizeDTO = modelMapper.map(find.get(), SizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), sizeDTO);
        return rs;
    }

    @Override
    public ResponseApi getAll()
    {
        List<SizeEntity> sizeEntities = sizeRepository.findAll();
        List<SizeDTO> sizeDTOS = modelMapper.mapAll(sizeEntities, SizeDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), sizeDTOS);
        return rs;
    }


}
