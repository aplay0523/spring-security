package com.api.dataHub.common.exception;

import com.api.dataHub.common.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
