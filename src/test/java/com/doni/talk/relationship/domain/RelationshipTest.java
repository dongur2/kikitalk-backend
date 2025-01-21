package com.doni.talk.relationship.domain;

import com.doni.talk.relationship.repository.RelationshipRepository;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class RelationshipTest {
    @Autowired UserRepository userRepository;
    @Autowired RelationshipRepository relationshipRepository;

    @Test @Transactional
    void 본인만_친구를_추가했을_경우_본인_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //본인이 친구를 추가
        relationshipRepository.save(Relationship.builder().user(owner).friend(friend).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(1); //관계 설정 확인

        //본인이 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(owner);
        relationshipRepository.deleteAllByFriend(friend);

        //본인 탈퇴
        userRepository.delete(owner);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //본인이 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }

    @Test @Transactional
    void 본인만_친구를_추가했을_경우_친구_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //본인이 친구를 추가
        relationshipRepository.save(Relationship.builder().user(owner).friend(friend).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(1); //관계 설정 확인

        //본인이 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(friend);
        relationshipRepository.deleteAllByFriend(friend);

        //친구 탈퇴
        userRepository.delete(friend);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //친구가 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }

    @Test @Transactional
    void 친구만_본인을_추가했을_경우_본인_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //친구만 본인을 추가
        relationshipRepository.save(Relationship.builder().user(friend).friend(owner).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(1); //관계 설정 확인

        //본인이 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(owner);
        relationshipRepository.deleteAllByFriend(owner);

        //본인 탈퇴
        userRepository.delete(owner);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //친구가 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }

    @Test @Transactional
    void 친구만_본인을_추가했을_경우_친구_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //친구만 본인을 추가
        relationshipRepository.save(Relationship.builder().user(friend).friend(owner).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(1); //관계 설정 확인

        //친구가 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(friend);
        relationshipRepository.deleteAllByFriend(friend);

        //친구 탈퇴
        userRepository.delete(friend);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //친구가 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }

    @Test @Transactional
    void 서로_추가했을_경우_본인_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //서로 친구 추가
        relationshipRepository.save(Relationship.builder().user(owner).friend(friend).build());
        relationshipRepository.save(Relationship.builder().user(friend).friend(owner).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(2); //관계 설정 확인

        //본인이 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(owner);
        relationshipRepository.deleteAllByFriend(owner);

        //본인 탈퇴
        userRepository.delete(owner);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //본인이 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }

    @Test @Transactional
    void 서로_추가했을_경우_친구_탈퇴_시_보유_관계_삭제_확인() {
        //회원 가입
        User owner = userRepository.save(User.builder().name("본인").build());
        User friend = userRepository.save(User.builder().name("친구").build());
        Assertions.assertThat(userRepository.findAll()).hasSize(2); //회원가입 확인

        //서로 친구 추가
        relationshipRepository.save(Relationship.builder().user(owner).friend(friend).build());
        relationshipRepository.save(Relationship.builder().user(friend).friend(owner).build());
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(2); //관계 설정 확인

        //친구가 포함된 관계 모두 삭제
        relationshipRepository.deleteAllByUser(friend);
        relationshipRepository.deleteAllByFriend(friend);

        //친구 탈퇴
        userRepository.delete(friend);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);

        //친구가 포함된 관계를 모두 삭제 => 저장된 관계 없음
        Assertions.assertThat(relationshipRepository.findAll()).hasSize(0);
    }
}