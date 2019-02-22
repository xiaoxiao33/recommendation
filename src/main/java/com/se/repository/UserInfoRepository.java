package com.se.repository;

import com.se.model.UserInfo;
import com.se.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInfoRepository {

    Map<String, UserInfo> userInfoMap = new HashMap<>();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfo save(UserInfo user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userInfoMap.put(user.getId(), user);
    }

    public List<UserInfo> allUsers() {

        return new ArrayList<UserInfo>(userInfoMap.values());
    }


    public Optional<UserInfo> findByUsername(final String username) {
        return userInfoMap
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }

}
