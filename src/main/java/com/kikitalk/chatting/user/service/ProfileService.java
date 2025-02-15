package com.kikitalk.chatting.user.service;

import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.profile.ProfileUpdateDTO;
import com.kikitalk.chatting.user.dto.response.profile.DetailProfileDTO;
import com.kikitalk.chatting.user.dto.response.profile.SimpleProfileDTO;

/**
 * 프로필 관련 서비스 인터페이스입니다.
 * 사용자의 프로필 조회 및 수정 기능을 제공합니다.
 *
 * @since 1.0
 */
public interface ProfileService {

    /**
     * 로그인한 사용자의 간단한 프로필 정보를 조회합니다.
     *
     * @param currentUser 인증된 사용자
     * @return 사용자의 간단한 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    SimpleProfileDTO fetchMySimpleProfile(User currentUser);

    /**
     * 로그인한 사용자의 상세 프로필 정보를 조회합니다.
     *
     * @param currentUser 인증된 사용자
     * @return 사용자의 상세 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    DetailProfileDTO fetchUserDetailProfile(User currentUser);

    /**
     * 회원 ID를 기반으로 해당 회원의 상세 프로필을 조회합니다.
     *
     * @param userId 조회할 회원의 고유 ID
     * @return 사용자의 상세 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    DetailProfileDTO fetchUserDetailProfile(Long userId);

    /**
     * 회원의 프로필 정보를 수정합니다.
     *
     * @param currentUser 인증된 사용자
     * @param newInfo 수정할 프로필 정보
     * @return 사용자의 수정된 상세 프로필 정보
     *
     * @throws NullPointerException 사용자 정보가 존재하지 않을 경우
     *
     * @since 1.0
     */
    DetailProfileDTO updateUserProfile(User currentUser, ProfileUpdateDTO newInfo);
}
