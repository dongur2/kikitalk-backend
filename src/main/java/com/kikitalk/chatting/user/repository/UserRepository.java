package com.kikitalk.chatting.user.repository;

import com.kikitalk.chatting.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 정보를 관리하는 Repository 인터페이스입니다.
 *
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * SNS ID를 사용하여 회원을 조회합니다.
     *
     * @param snsId 조회할 회원의 SNS 고유 ID
     * @return 해당 SNS ID를 가진 회원 (존재하지 않을 경우 <code>Optional.empty()</code>)
     * @since 1.0
     */
    Optional<User> findBySnsId(String snsId);

    /**
     * 전화번호를 사용하여 회원을 조회합니다.
     *
     * @param phone 조회할 회원의 전화번호
     * @return 해당 전화번호를 가진 회원 (존재하지 않을 경우 <code>Optional.empty()</code>)
     * @since 1.0
     */
    Optional<User> findByPhone(String phone);

}
