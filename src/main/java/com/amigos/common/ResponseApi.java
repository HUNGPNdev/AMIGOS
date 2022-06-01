package com.amigos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApi {
    private int code;
    private String message;
    private Object data;

    public ResponseApi(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseApi(int code, String message) {
        this.code = code;
        this.message = message;
    }
}