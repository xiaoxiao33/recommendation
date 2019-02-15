package com.se.service;

import com.se.Model.UserInfo;
import com.se.util.LoginResult;

public interface LoginService {

    public LoginResult login(String email, String password);

    public UserInfo getUserInfoByEmail(String email);
}
