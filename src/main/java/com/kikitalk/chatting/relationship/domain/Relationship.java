package com.kikitalk.chatting.relationship.domain;

import com.kikitalk.chatting.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "tbl_relationship")
@Getter @ToString @NoArgsConstructor
public class Relationship {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

    @Builder
    public Relationship(Long id, User user, User friend) {
        this.id = id;
        this.user = user;
        this.friend = friend;
    }
}