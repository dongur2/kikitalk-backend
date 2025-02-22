package com.kikitalk.chatting.chat.service;

import com.kikitalk.chatting.chat.domain.ChatMessage;
import com.kikitalk.chatting.chat.domain.ChatParticipant;
import com.kikitalk.chatting.chat.domain.ChatRoom;
import com.kikitalk.chatting.chat.dto.request.MessageSendDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomListDTO;
import com.kikitalk.chatting.chat.dto.response.MessageInChatDTO;
import com.kikitalk.chatting.chat.repository.ChatMessageRepository;
import com.kikitalk.chatting.chat.repository.ChatParticipantRepository;
import com.kikitalk.chatting.chat.repository.ChatRoomRepository;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j @Service @AllArgsConstructor
public class ChatServiceI implements ChatService{
    @Autowired private final ChatRoomRepository chatRoomRepository;
    @Autowired private final ChatParticipantRepository chatParticipantRepository;
    @Autowired private final ChatMessageRepository chatMessageRepository;

    @Autowired private final UserService userService;

    @Override @Transactional
    public List<ChatRoomListDTO> getMyChatRooms(User user) {
        List<ChatParticipant> participants = chatParticipantRepository.findAllByUser(user);

        if(participants.isEmpty()) return List.of();

        List<ChatRoomListDTO> chatRoomList = new ArrayList<>();
        participants.forEach(participant -> {
            ChatRoom chatRoom = participant.getChatRoom();

            //상대방
            List<User> company = chatRoom.getParticipants()
                    .stream().map(ChatParticipant::getUser).filter(partUser -> !partUser.getId().equals(user.getId())).toList();

            //최신 메세지
            ChatMessage latestMessage = chatRoom.getMessages()
                    .stream().max(Comparator.comparing(ChatMessage::getCreatedAt)).get();

            //DTO
            ChatRoomListDTO charRoomListDTO = ChatRoomListDTO.from(chatRoom, company.get(0), latestMessage);
            chatRoomList.add(charRoomListDTO);
        });

        return chatRoomList;
    }

    @Override @Transactional
    public Long getOrCreateChatRoom(User user, Long otherId) throws NullPointerException {
        User other = userService.getUserById(otherId); //채팅 참여 상대

        //이미 본인과 상대방이 참여한 채팅방이 존재할 경우: 기존 채팅방 리턴
        Optional<ChatRoom> existingChatRoom = chatParticipantRepository.findChatParticipant(user.getId(), otherId);

        // 이미 존재하는 채팅방이 있을 경우
        if (existingChatRoom.isPresent()) return existingChatRoom.get().getId();

        // 존재하지 않는 채팅방이 있을 경우 생성
        return createChatRoom(user, other);
    }

    @Override
    public ChatRoomDTO getChatRoomWithMessages(User user, Long chatRoomId) throws NullPointerException {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NullPointerException("채팅방이 존재하지 않습니다."));

        //상대방
        User company = chatRoom.getParticipants()
                .stream().map(ChatParticipant::getUser).filter(partUser -> !partUser.getId().equals(user.getId())).toList()
                .get(0);

        return ChatRoomDTO.from(chatRoom, company);
    }

    //메세지 전송
    @Override @Transactional
    public MessageInChatDTO createChatMessage(User user, Long chatRoomId, MessageSendDTO message) throws NullPointerException {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NullPointerException("채팅방이 존재하지 않습니다."));

        ChatMessage newMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(message.getContent())
                .build();

        ChatMessage sent = chatMessageRepository.save(newMessage);

        return MessageInChatDTO.from(sent);
    }

    //메세지 전송
    @Override @Transactional
    public MessageInChatDTO createChatMessage(MessageSendDTO message) throws NullPointerException {
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).orElseThrow(() -> new NullPointerException("채팅방이 존재하지 않습니다."));
        User user = userService.getUserById(message.getWriterId());

        ChatMessage newMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(message.getContent())
                .build();

        ChatMessage sent = chatMessageRepository.save(newMessage);

        return MessageInChatDTO.from(sent);
    }

    //채팅방 삭제
    @Override @Transactional
    public void deleteChatRoom(User user, Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

    //채팅방 생성
    public Long createChatRoom(User user, User other) {
        ChatRoom newChatRoom = chatRoomRepository.save(new ChatRoom());
        addParticipantToRoom(newChatRoom, user);
        addParticipantToRoom(newChatRoom, other);
        return newChatRoom.getId();
    }

    //채팅방 참여자 추가
    public void addParticipantToRoom(ChatRoom chatRoom, User participant){
        ChatParticipant chatParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .user(participant)
                .build();
        chatParticipantRepository.save(chatParticipant);
    }
}
