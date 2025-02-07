package com.kikitalk.chatting.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 예외 처리 컨트롤러에서 반환하는 공통 예외 템플릿 클래스입니다.
 *
 * <p>
 * 이 클래스는 API 응답의 일관성을 유지하기 위해 사용됩니다.
 * 모든 응답에는 HTTP 상태, 예외 발생 시간, 메세지가 포함되며, 이 클래스는 이를 감싸는 공통 형식을 제공합니다.
 * </p>
 *
 *  <ul>
 *      <li><code>status</code>: HTTP 상태 코드 (예: 400, 500 등)</li>
 *      <li><code>code</code>: HTTP 상태 코드 이름 (예: BAD REQUEST, SERVER ERROR 등)</li>
 *      <li><code>timeStamp</code>: 예외 발생 시간</li>
 *      <li><code>msg</code>: 클라이언트에게 반환할 메세지 (예외 발생 원인에 대한 메세지)</li>
 *  </ul>
 *
 *  */
@Getter
public class ExceptionResponse {
    private final int status;
    private final String code;
    private final String timeStamp;
    private final String msg;

    private ExceptionResponse(int status, String code, String timeStamp, String msg) {
        this.status = status;
        this.code = code;
        this.timeStamp = timeStamp;
        this.msg = msg;
    }

    /**
     * 공통 예외 응답 객체를 생성합니다.
     *
     * @param status HTTP 상태
     * @param time   예외 발생 시간
     * @param msg    클라이언트에게 반환할 메세지 (예외 발생 원인에 대한 메세지)
     * @return       생성된 공통 응답 객체
     */
    public static ExceptionResponse of(HttpStatus status, LocalDateTime time, String msg) {
        return new ExceptionResponse(status.value(), status.name(), time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), msg);
    }
}
