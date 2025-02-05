package com.doni.talk.user.service;

import com.doni.talk.user.dto.request.UpdateProfileDTO;
import com.doni.talk.user.dto.response.DetailProfileDTO;

/**
 * 프로필 관련 서비스 인터페이스입니다.
 * 사용자의 프로필 조회 및 수정 기능을 제공합니다.
 *
 * @since 1.0
 */
public interface ProfileService {

    /**
     * 회원 ID를 기반으로 해당 회원의 상세 프로필을 조회합니다.
     *
     * @param id 조회할 회원의 고유 ID
     * @return 회원의 상세 프로필 정보
     * @throws NullPointerException id에 해당하는 회원이 존재하지 않을 경우
     * @since 1.0
     */
    DetailProfileDTO getUserProfileById(Long id);

    /**
     * 회원의 프로필 정보를 수정합니다.
     *
     * @param id         수정할 회원의 고유 ID
     * @param updateInfo 수정할 프로필 정보 (닉네임, 연락처, 상태 메시지, 프로필 사진)
     * @return 회원의 고유 ID, 수정된 회원의 상세 프로필 정보
     * @throws NullPointerException id에 해당하는 회원이 존재하지 않을 경우
     * @since 1.0
     */
    DetailProfileDTO updateUserProfile(Long id, UpdateProfileDTO updateInfo);
}
