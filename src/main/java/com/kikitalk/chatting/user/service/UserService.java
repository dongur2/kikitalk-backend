package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.SignUpDTO;

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
     * @return 해당 SNS ID를 가진 회원
     * @throws NullPointerException 해당 SNS ID를 가진 회원 정보가 존재하지 않을 경우
     * @since 1.0
     */
    User getUserBySnsId(String snsId);

    /**
     * ID를 사용하여 회원을 조회합니다.
     *
     * @param id 조회할 회원의 고유 ID
     * @return 해당 ID를 가진 회원
     * @throws NullPointerException 해당 ID를 가진 회원 정보가 존재하지 않을 경우
     * @since 1.0
     */
    User getUserById(Long id);

    /**
     * 회원가입을 처리합니다.
     *
     * @param postInfo 회원 정보 (소셜ID, 이름, 닉네임, 상태 메세지, 연락처, 프로필 사진 URL)
     * @return 가입된 회원
     * @since 1.0
     */
    User join(SignUpDTO postInfo);

    /**
     * 회원 로그인을 처리합니다.
     * <br/>
     * 로그인에 성공할 경우 인증 토큰을 반환합니다.
     *
     * @param user 로그인 요청 회원
     * @return 인증 토큰
     * @since 1.0
     */
    String signIn(User user);
}
