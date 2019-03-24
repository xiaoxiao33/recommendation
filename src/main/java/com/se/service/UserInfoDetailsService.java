package com.se.service;


import com.se.model.UserAuth;
import com.se.model.UserInfo;
import com.se.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Deprecated
public class UserInfoDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoDetailsService(UserInfoRepository userInfoRepository){
        super();
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = this.userInfoRepository.findInfoByEmail(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("cannot find user email: " + username);
        }
        return new UserAuth(user.get());
    }

}
