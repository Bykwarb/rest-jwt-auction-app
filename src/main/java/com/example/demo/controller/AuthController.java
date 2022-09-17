package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequestDto;
import com.example.demo.entity.User;
import com.example.demo.event.user.OnRegistrationCompleteEvent;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationEventPublisher publisher;

    private final AuthenticationManager manager;
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    public AuthController(AuthenticationManager manager, UserService userService, VerificationTokenService verificationTokenService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request, HttpServletRequest servletRequest){
        try {

            manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userService.getUserByEmail(request.getEmail());
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            Map<String, Object> response = new LinkedHashMap<>();
            if (e.getMessage().equals("User is disabled")){
                User user = userService.getUserByEmail(request.getEmail());
                verificationTokenService.deleteByUser(user);
                publisher.publishEvent(new OnRegistrationCompleteEvent(
                        user,
                        servletRequest.getLocale()));

                response.put("cause", "User is disabled. Verification code sent on your email.");
                response.put("status", HttpStatus.FORBIDDEN);
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }

            response.put("cause", e.getMessage());
            response.put("status", HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
