package com.amigos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig extends ModelMapper{
    /** Source is null return null */

    @Override
    public <D> D map(final Object source, final Class<D> destinationType) {
        Object tmpSource = source;
        if (source == null) {
            tmpSource = new Object();
        }
        return super.map(tmpSource, destinationType);
    }

    public <D, E> List<E> mapAll(List<D> entityList, Class<E> out) {
        if (!entityList.isEmpty()) {
            return entityList.stream().map(e -> map(e, out)).collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }
}
