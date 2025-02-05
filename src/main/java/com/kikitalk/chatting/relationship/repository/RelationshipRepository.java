package com.kikitalk.chatting.relationship.repository;

import com.kikitalk.chatting.relationship.domain.Relationship;
import com.kikitalk.chatting.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> deleteAllByUser(User owner);
    List<Relationship> deleteAllByFriend(User friend);

    List<Relationship> findAllByUserId(Long userId);

    Optional<Relationship> findByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
