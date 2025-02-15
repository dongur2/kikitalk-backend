package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.signup.UserSaveDTO;
import com.kikitalk.chatting.user.dto.request.signup.SignUpDTO;
import com.kikitalk.chatting.user.dto.response.signup.SignUpFormDTO;

/**
 * 회원 관련 서비스 인터페이스입니다.
 * 회원 정보 조회, 회원가입, 로그인 기능을 제공합니다.
 *
 * @since 1.0
 */
public interface UserService {

    /**
     * SNS ID를 사용하여 회원을 조회합니다.
     *
     * @param snsId 조회할 회원의 SNS 고유 ID
     * @return 저장된 회원 객체
     *
     * @throws NullPointerException 해당 SNS ID를 가진 회원 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    User getUserBySnsId(String snsId);

    /**
     * ID를 사용하여 회원을 조회합니다.
     *
     * @param id 조회할 회원의 고유 ID
     * @return 저장된 회원 객체
     *
     * @throws NullPointerException 해당 ID를 가진 회원 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    User getUserById(Long id);

    /**
     * 데이터베이스에 새로운 회원을 저장합니다.
     * OAuth 인증을 통해 전달받은 기본적인 사용자 정보를 저장하며,
     * 이 단계에서는 회원가입이 완료된 상태가 아닙니다.
     *
     * @param oauth2Info 기본 사용자 정보
     * @return 저장된 회원 객체
     *
     * @since 1.0
     */
    User save(UserSaveDTO oauth2Info);

    /**
     * 회원 정보의 필수값을 확인하여 회원가입 완료 여부를 반환합니다.
     * 사용자의 이름, 닉네임, 연락처, 생년월일이 모두 입력되었는지 확인하여
     * 누락된 정보가 있으면 회원가입이 완료되지 않은 것으로 간주합니다.
     *
     * @param user 인증된 사용자
     * @return 회원가입 완료 여부 (true: 완료, false: 미완료)
     *
     * @since 1.0
     */
    Boolean checkSignUpStatus(User user);

    /**
     * 회원가입 폼에 바인딩할 데이터를 조회합니다.
     * 조회한 사용자 정보를 기반으로 수정 가능한 데이터만 반환합니다.
     *
     * @param user 인증된 사용자 객체
     * @return 회원가입 폼에 바인딩할 데이터
     *
     * @throws NullPointerException 사용자가 존재하지 않을 경우
     *
     * @since 1.0
     */
    SignUpFormDTO getSignUpFormData(User user);


    /**
     * 기존 회원의 필수 추가 정보를 업데이트하여 회원가입을 완료합니다.
     *
     * @param user 인증된 사용자
     * @param additionalInfo 추가 회원 정보
     *
     * @throws NullPointerException 사용자가 존재하지 않을 경우
     *
     * @since 1.0
     */
    void updateRequiredInfo(User user, SignUpDTO additionalInfo);

    /**
     * 회원 로그인을 처리합니다.
     * 로그인에 성공할 경우 인증 토큰을 반환합니다.
     *
     * @param user 로그인 요청 회원
     * @return 인증 토큰
     *
     * @since 1.0
     */
    String signIn(User user);
}
