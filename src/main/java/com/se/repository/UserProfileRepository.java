package com.se.repository;

import com.se.model.UserInfo;
import com.se.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
public class UserProfileRepository {
    Map<Integer, UserProfile> userProfileMap = new HashMap<>();

    public Optional<UserProfile > findUserById(String id) {
        return ofNullable(userProfileMap.get(id));
    }

    public boolean addNewProfile(UserInfo userInfo) {
        userProfileMap.put(userInfo.getId(), new UserProfile(userInfo));
        return true;
    }

    public List<UserProfile> findAll() {
        return new ArrayList<UserProfile>(userProfileMap.values());
    }
}
