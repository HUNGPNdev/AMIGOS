package com.amigos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

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
}
