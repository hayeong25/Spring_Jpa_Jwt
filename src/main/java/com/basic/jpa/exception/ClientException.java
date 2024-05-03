package com.basic.jpa.exception;

import com.basic.jpa.util.ErrorCode;
import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private final ErrorCode errorCode;

    public ClientException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}