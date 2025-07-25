package com.api.dataHub.common.exception;

import java.io.IOException;

public class InValidException extends IOException {

    // IOException 기본 호출
    public InValidException() { super(); }

    // IOException의 메시지 호출
    public InValidException(String message) {
        super(message);
    }
}
