package com.kikitalk.chatting.chat.repository;

import com.kikitalk.chatting.chat.domain.ChatParticipant;
import com.kikitalk.chatting.chat.domain.ChatRoom;
import com.kikitalk.chatting.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    @Query("SELECT cp1.chatRoom FROM ChatParticipant cp1 JOIN ChatParticipant cp2 ON cp1.chatRoom.id = cp2.chatRoom.id WHERE cp1.user.id = :userId AND cp2.user.id = :otherId")
    Optional<ChatRoom> findChatParticipant(@Param("userId") Long userId, @Param("otherId") Long otherId);

    List<ChatParticipant> findAllByUser(User user);
    List<ChatParticipant> findByChatRoom(ChatRoom chatRoom);
}
