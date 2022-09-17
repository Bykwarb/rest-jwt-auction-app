package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.VerificationToken;

public interface VerificationTokenService {
    void createToken(User user, String token);
    VerificationToken getToken(String token);

    void deleteToken(VerificationToken token);

    VerificationToken getTokenByUser(User user);

    void deleteByUser(User user);
}
