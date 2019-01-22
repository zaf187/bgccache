package com.cache.bgc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="The value could not be found in the cache")
public class CacheValueNotFoundException extends RuntimeException {

    public CacheValueNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
