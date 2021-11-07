package com.tapu.shorterurl.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private int status;

    private String message;

    private String path;

    private long timestamp = new Date().getTime();

    private Map<String, String> validationErrors;

    public ApiError(final int status,
                    final String message,
                    final String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
