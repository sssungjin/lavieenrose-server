package org.dongguk.lavieenrosehotel.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: Int, val httpStatus: HttpStatus, val message: String) {
    // Not Found Error
    NOT_FOUND(40400, HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
    NOT_FOUND_ENTITY_AMONG_PROCESSING_EVENT(40401, HttpStatus.NOT_FOUND, "해당 이벤트에 대한 엔티티를 찾을 수 없습니다."),
    NOT_END_POINT(40402, HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),
    NOT_FOUND_USER(40403, HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다."),
    NOT_FOUND_ROOM_STATUS(40404, HttpStatus.NOT_FOUND, "해당 방 상태가 존재하지 않습니다."),
    NOT_FOUND_EMPTY_ROOM(40405, HttpStatus.NOT_FOUND, "해당 카테고리의 빈 방이 존재하지 않습니다."),
    NOT_FOUND_AMENITY(40406, HttpStatus.NOT_FOUND, "해당 시설이 존재하지 않습니다."),

    // Invalid Argument Error
    BAD_REQUEST(40000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(40001, HttpStatus.BAD_REQUEST, "지원하지 않는 HTTP Method 입니다."),
    UNSUPPORTED_MEDIA_TYPE(40001, HttpStatus.BAD_REQUEST, "지원하지 않는 미디어 타입입니다."),
    INVALID_ROLE(40001, HttpStatus.BAD_REQUEST, "유효하지 않은 권한입니다."),
    INVALID_PROVIDER(40002, HttpStatus.BAD_REQUEST, "유효하지 않은 제공자입니다."),
    INVALID_ARGUMENT(40003, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    MISSING_REQUEST_COOKIE(40004, HttpStatus.BAD_REQUEST, "필수 요청 쿠키가 누락되었습니다."),
    MISSING_REQUEST_PARAMETER(40004, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(40005, HttpStatus.BAD_REQUEST, "요청 파라미터의 형태가 잘못되었습니다."),
    INVALID_AUTHENTICATION_HEADER(50001, HttpStatus.BAD_REQUEST, "유효하지 않은 인증 헤더입니다."),
    DUPLICATED_SERIAL_ID(40006, HttpStatus.BAD_REQUEST, "중복된 ID 입니다."),
    INVALID_BREAKFAST_ORDER(40007, HttpStatus.BAD_REQUEST, "예약 기간과 조식 예약 기간이 일치하지 않습니다."),
    RESERVATION_FULL(40008, HttpStatus.BAD_REQUEST, "예약이 가득찼습니다."),

    // Unauthorized Error
    TOKEN_GENERATION_ERROR(40100, HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."),
    INVALID_TOKEN_ERROR(40101, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN_ERROR(40102, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_MALFORMED_ERROR(40103, HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_TYPE_ERROR(40104, HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않습니다."),
    TOKEN_UNSUPPORTED_ERROR(40105, HttpStatus.UNAUTHORIZED, "지원하지않는 토큰입니다."),
    TOKEN_UNKNOWN_ERROR(40105, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),

    // Forbidden Error
    ACCESS_DENIED(40300, HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다."),
    UNKNOWN_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다."),
    UPLOAD_FILE_ERROR(50002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다."),


}