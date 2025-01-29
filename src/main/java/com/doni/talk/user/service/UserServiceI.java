package com.doni.talk.user.service;

import com.doni.talk.global.security.jwt.JwtProvider;
import com.doni.talk.relationship.domain.Relationship;
import com.doni.talk.relationship.service.RelationshipService;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.dto.request.UserSearchDTO;
import com.doni.talk.user.dto.request.UserSignUpDTO;
import com.doni.talk.user.dto.request.UserUpdateDTO;
import com.doni.talk.user.dto.response.SimpleProfileDTO;
import com.doni.talk.user.dto.response.UserProfileDTO;
import com.doni.talk.user.dto.response.UserSearchProfileDTO;
import com.doni.talk.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j @Service
@AllArgsConstructor @NoArgsConstructor
public class UserServiceI implements UserService {
    @Autowired private JwtProvider jwtProvider;
    @Autowired private UserRepository repository;
    @Autowired private RelationshipService relationshipService;

    @Value("${profile.default_img}") private String DEFAULT_PROFILE_IMG;

    @Override
    public User getUserBySnsId(String snsId) {
        return repository.findBySnsId(snsId).orElseThrow(NullPointerException::new);
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public UserProfileDTO getUserProfileById(Long id) throws NullPointerException {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        return UserProfileDTO.from(user);
    }

    @Override @Transactional
    public User join(UserSignUpDTO postInfo) {
        return repository.save(postInfo.to(DEFAULT_PROFILE_IMG));
    }

    @Override
    public String signIn(User user) {
        return jwtProvider.createAccessToken(user);
    }

    @Override @Transactional
    public UserProfileDTO updateUserProfile(Long id, UserUpdateDTO updateInfo) {
        User user = repository.findById(id).orElseThrow(NullPointerException::new);
        user.updateUserProfile(updateInfo, DEFAULT_PROFILE_IMG);
        return UserProfileDTO.from(user);
    }

    @Override
    public List<SimpleProfileDTO> getFriendList(Long id) {
        return relationshipService.getFriendList(id);
    }

    @Override
    public UserSearchProfileDTO getUserBySearch(User user, UserSearchDTO searchInfo) {
        Optional<User> optionalUser = repository.findByPhone(searchInfo.getPhone());

        if (optionalUser.isPresent()) {
            User other = optionalUser.get();
            Boolean isFriend = checkIsFriend(user.getId(), other.getId());
            return UserSearchProfileDTO.from(other, isFriend);
        }

        return null;
    }

    @Override @Transactional
    public UserSearchProfileDTO addFriend(Long myId, Long otherId) throws RuntimeException {
        if(myId.equals(otherId)) throw new RuntimeException("자신을 친구로 추가할 수 없습니다.");

        Relationship relationship = relationshipService.addRelationship(getUserById(myId), getUserById(otherId));
        return UserSearchProfileDTO.from(relationship.getFriend(), checkIsFriend(myId, otherId));
    }

    private Boolean checkIsFriend(Long myId, Long otherId) {
        return relationshipService.checkIsFriend(myId, otherId);
    }
}
