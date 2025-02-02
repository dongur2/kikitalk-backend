package com.doni.talk.user.service;

import com.doni.talk.user.dto.request.UpdateProfileDTO;
import com.doni.talk.user.dto.response.DetailProfileDTO;

public interface ProfileService {
    DetailProfileDTO getUserProfileById(Long id);
    DetailProfileDTO updateUserProfile(Long id, UpdateProfileDTO updateInfo);
}
