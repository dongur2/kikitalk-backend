package com.kikitalk.chatting.chat.domain;

import com.kikitalk.chatting.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

//채팅 참여자
@Entity @Table(name = "tbl_chat_participant")
@Getter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class ChatParticipant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
