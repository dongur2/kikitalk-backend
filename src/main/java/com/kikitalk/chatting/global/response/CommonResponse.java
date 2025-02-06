package com.kikitalk.chatting.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 컨트롤러에서 반환하는 응답의 공통 템플릿 클래스입니다.
 *
 * <p>
 * 이 클래스는 API 응답의 일관성을 유지하기 위해 사용됩니다.
 * 모든 응답에는 HTTP 상태 코드, 메세지, 데이터가 포함될 수 있으며, 이 클래스는 이를 감싸는 공통 형식을 제공합니다.
 * </p>
 *
 *  <ul>
 *      <li><code>status</code>: HTTP 상태 코드 (예: 200, 400, 500 등)</li>
 *      <li><code>msg</code>: 클라이언트에게 반환할 메세지 (예: 요청과 처리 결과에 대한 메세지)</li>
 *      <li><code>data</code>: 실제 응답 데이터 (데이터가 없는 경우 <code>null</code> 처리, <code>JsonInclude.Include.NON_NULL</code>을 통해 포함되지 않음)</li>
 *  </ul>
 *
 * @param <T> 응답 데이터의 타입 (제네릭 타입으로 다양한 데이터 형태를 처리 가능)
 */
@Getter
public class CommonResponse<T> {
    private final int status;
    private final String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL) //data가 null일 경우 포함하지 않음
    private final T data;

    private CommonResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 공통 응답 객체를 생성합니다.
     *
     * <p>
     * 이 메서드는 HTTP 상태 코드, 메시지, 데이터가 모두 제공될 때 사용됩니다.
     * </p>
     *
     * @param <T>    응답 데이터의 타입 (제네릭 타입)
     * @param status HTTP 상태
     * @param msg    클라이언트에게 반환할 메세지 (예: 요청과 처리 결과에 대한 메세지)
     * @param data   클라이언트에게 반환할 데이터 (예: 반환된 객체, 리스트 등)
     * @return       생성된 공통 응답 객체
     */
    public static <T> CommonResponse<T> of(HttpStatus status, String msg, T data) {
        return new CommonResponse<>(status.value(), msg, data);
    }

    /**
     * 공통 응답 객체를 생성합니다.
     *
     * <p>
     * 이 메서드는 일반적인 성공적인 요청 처리 후 HTTP 상태 코드가 200일 때 사용되며, 데이터와 메세지를 반환합니다.
     * </p>
     *
     * @param <T>    응답 데이터의 타입 (제네릭 타입)
     * @param msg    클라이언트에게 반환할 메세지 (예: 요청과 처리 결과에 대한 메세지)
     * @param data   클라이언트에게 반환할 데이터 (예: 반환된 객체, 리스트 등)
     * @return       생성된 공통 응답 객체
     */
    public static <T> CommonResponse<T> of(String msg, T data) {
        return new CommonResponse<>(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 공통 응답 객체를 생성합니다.
     *
     * <p>
     * 이 메서드는 일반적인 성공적인 요청 처리 후 HTTP 상태 코드가 200일 때 사용되며, 메세지만 제공되고 데이터는 없습니다.
     * 주로 데이터가 필요 없는 단순 성공 메세지를 반환할 때 사용됩니다.
     * </p>
     *
     * @param <T>    응답 데이터의 타입 (제네릭 타입)
     * @param msg    클라이언트에게 반환할 메세지 (예: 요청과 처리 결과에 대한 메세지)
     * @return       생성된 공통 응답 객체
     */
    public static <T> CommonResponse<T> of(String msg) {
        return new CommonResponse<>(HttpStatus.OK.value(), msg, null);
    }
}
