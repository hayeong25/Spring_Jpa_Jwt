package com.basic.jpa.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /*
     * C : Client Error
     * D : DB Fail
     * S : Server Error
     */
    INVALID_ID(HttpStatus.NOT_FOUND, "C0001", "존재하지 않는 ID입니다"),
    DUPLICATE_ID(HttpStatus.FORBIDDEN, "C0002", "이미 사용 중인 아이디입니다"),
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "C0003", "비밀번호가 일치하지 않습니다"),
    INVALID_ACCOUNT(HttpStatus.FORBIDDEN, "C0004", "유효하지 않은 계정입니다"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "C0005", "유효하지 않은 토큰입니다"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "C0006", "만료된 토큰입니다"),
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "C0007", "접근 권한이 없습니다"),

    INVALID_LEDGER(HttpStatus.NOT_FOUND, "C1001", "존재하지 않는 가계부입니다"),
    NOT_OWNER(HttpStatus.FORBIDDEN, "C1002", "가계부 소유주가 아닙니다"),

    SELECT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0001", "데이터 조회 실패"),
    INSERT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0002", "데이터 저장 실패"),
    UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0003", "데이터 수정 실패"),
    DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "D0004", "데이터 삭제 실패"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S0001", "서버 오류입니다. 재시도 해주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}