package com.kikitalk.chatting.chat.service;

import com.kikitalk.chatting.chat.dto.request.MessageSendDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomListDTO;
import com.kikitalk.chatting.chat.dto.response.MessageInChatDTO;
import com.kikitalk.chatting.user.domain.User;

import java.util.List;


public interface ChatService {
    List<ChatRoomListDTO> getMyChatRooms(User user);
    Long getOrCreateChatRoom(User user, Long otherId);
    ChatRoomDTO getChatRoomWithMessages(User user, Long chatRoomId);
    MessageInChatDTO createChatMessage(User user, Long chatRoomId, MessageSendDTO message);
    MessageInChatDTO createChatMessage(MessageSendDTO message);
    void deleteChatRoom(User user, Long chatRoomId);
}
