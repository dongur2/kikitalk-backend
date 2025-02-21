package com.kikitalk.chatting.chat.repository;

import com.kikitalk.chatting.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
