package com.amigos.errors.handle;

import lombok.Data;

@Data
public class ObjectDuplicateException extends RuntimeException{
    private Object data;

    public ObjectDuplicateException(String info, String message, Object data) {
        super(String.format(info + " is already exists: %s", message));
        this.data = data;
    }
}
