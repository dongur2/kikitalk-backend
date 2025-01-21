package com.doni.talk.relationship.repository;

import com.doni.talk.relationship.domain.Relationship;
import com.doni.talk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> deleteAllByUser(User owner);
    List<Relationship> deleteAllByFriend(User friend);
}
