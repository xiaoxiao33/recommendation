package com.se.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordSecurityService {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String plainTextPassword){
        return bCryptPasswordEncoder.encode(plainTextPassword);
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(plainPassword, hashedPassword);

    }
}
