package com.api.dataHub.common.exception;

import com.api.dataHub.common.ErrorCode;
import com.api.dataHub.controller.vo.response.ResponseHeadVo;
import com.api.dataHub.controller.vo.response.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // openApi 2.5 이상 권장 (2.5 버전 x)
public class ApiValidException {

    // 유효성 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> apiValidException(MethodArgumentNotValidException e) {

        ErrorCode errorCode = ErrorCode.BAD_REQUEST;

        ResponseVo responseVo = new ResponseVo();
        ResponseHeadVo responseHeadVo = new ResponseHeadVo();

        responseHeadVo.setCode(errorCode.getCode());
        responseHeadVo.setMessage(errorCode.getMessage());
        responseHeadVo.setDetail(errorCode.getDetail());

        responseVo.setHead(responseHeadVo);
        responseVo.setBody(null);


        return ResponseEntity.status(errorCode.getHttpStatus()).body(responseVo);
    }

    // 중복 체크 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();

        ResponseVo responseVo = new ResponseVo();
        ResponseHeadVo responseHeadVo = new ResponseHeadVo();

        responseHeadVo.setCode(errorCode.getCode());
        responseHeadVo.setMessage(errorCode.getMessage());
        responseHeadVo.setDetail(errorCode.getDetail());

        responseVo.setHead(responseHeadVo);
        responseVo.setBody(null);


        return ResponseEntity.status(errorCode.getHttpStatus()).body(responseVo);
    }
}
