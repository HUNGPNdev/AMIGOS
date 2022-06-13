package com.amigos.size;

import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.SizeDTO;

import java.util.UUID;

public interface SizeService
{
    ResponseApi addSize(SizeDTO size);
    ResponseApi updateSize(SizeDTO size);
    ResponseApi getDetailSize(UUID id);
    ResponseApi getAll();
}
