package com.api.dataHub.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    SUCCESS(HttpStatus.OK, 200, "성공", "요청이 성공적으로 처리되었습니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청", "요청 형식이 올바르지 않습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, 400, "입력값 유효성 검증 실패", "요청 파라미터 또는 본문의 유효성 검증에 실패했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "인증 실패", "유효한 인증 정보가 없습니다. 로그인 후 다시 시도해주세요."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "권한 없음", "해당 리소스에 접근할 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "리소스를 찾을 수 없음", "요청한 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "허용되지 않는 HTTP 메서드", "해당 리소스에 허용되지 않는 HTTP 메서드로 요청했습니다."),
    CONFLICT(HttpStatus.CONFLICT, 409, "충돌", "요청 처리 중 충돌이 발생했습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류", "서버 내부에서 예상치 못한 오류가 발생했습니다. 관리자에게 문의해주세요."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503, "서비스 이용 불가", "현재 서비스를 이용할 수 없습니다. 잠시 후 다시 시도해주세요.");

    private final HttpStatus httpStatus; // HTTP 상태
    private final int code; // 오류 코드
    private final String message; // 간략한 오류 메시지
    private final String detail; // 상세 오류 설명

}
