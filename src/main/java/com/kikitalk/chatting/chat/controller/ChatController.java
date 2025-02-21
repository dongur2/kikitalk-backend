package com.kikitalk.chatting.chat.controller;

import com.kikitalk.chatting.chat.dto.request.MessageSendDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomDTO;
import com.kikitalk.chatting.chat.dto.response.ChatRoomListDTO;
import com.kikitalk.chatting.chat.dto.response.MessageInChatDTO;
import com.kikitalk.chatting.chat.service.ChatService;
import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    //채팅방 목록 조회
    @GetMapping
    public CommonResponse<List<ChatRoomListDTO>> getChats(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ChatRoomListDTO> chatRooms = chatService.getMyChatRooms(userDetails.getUser());
        return CommonResponse.of("채팅방 목록 조회를 완료했습니다.", chatRooms);
    }

    //채팅방 개설
    @PostMapping
    public CommonResponse<Long> getOrCreateChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long otherId){
        Long roomId = chatService.getOrCreateChatRoom(userDetails.getUser(), otherId);
        return CommonResponse.of("채팅방 개설 또는 기존 채팅방 조회를 완료했습니다.", roomId);
    }

    //채팅방 조회
    @GetMapping("/{chatRoomId}")
    public CommonResponse<ChatRoomDTO> getChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("chatRoomId") Long chatRoomId) {
        ChatRoomDTO chatRoom = chatService.getChatRoomWithMessages(userDetails.getUser(), chatRoomId);
        return CommonResponse.of("채팅방 조회를 완료했습니다.", chatRoom);
    }

    //채팅 메세지 전송
    @PostMapping("/{chatRoomId}/messages")
    public CommonResponse<MessageInChatDTO> sendChatMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @PathVariable("chatRoomId") Long chatRoomId, @Valid @RequestPart(name = "message") MessageSendDTO message){
        MessageInChatDTO sentMessage = chatService.createChatMessage(userDetails.getUser(), chatRoomId, message);
        return CommonResponse.of("메세지 전송을 완료했습니다.", sentMessage);
    }

    //채팅방 나가기
    @DeleteMapping("/{chatRoomId}")
    public CommonResponse<Void> exitChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("chatRoomId") Long chatRoomId) {
        chatService.deleteChatRoom(userDetails.getUser(), chatRoomId);
        return CommonResponse.of("채팅방 삭제를 완료했습니다.");
    }

}
