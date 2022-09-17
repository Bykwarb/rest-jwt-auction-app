package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.VerificationToken;
import com.example.demo.event.user.OnRegistrationCompleteEvent;
import com.example.demo.service.UserService;
import com.example.demo.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    private ApplicationEventPublisher publisher;
    public RegistrationController(UserService userService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, Object>> userRegistration(@RequestBody UserDto userDto, HttpServletRequest request){
        Map<String, Object> response = new LinkedHashMap<>();

        String emailPattern = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(userDto.getEmail());

        if (!matcher.matches()){
            response.put("cause", "Email pattern must like as: bebra@mail.com");
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (userService.existByEmail(userDto.getEmail())){
            response.put("cause", "An account for that email already exists");
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (userDto.getPassword().getBytes(StandardCharsets.UTF_8).length < 8){
            response.put("cause", "Password must contain at least 8 characters");
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        userService.registration(userDto.getEmail(), userDto.getPassword(), userDto.getUsername());
        publisher.publishEvent(new OnRegistrationCompleteEvent(
                                userService.getUserByEmail(userDto.getEmail()),
                                request.getLocale()));

        response.put("cause", "A confirmation code has been sent to your email.");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<Map<String, Object>> confirmRegistration(@RequestParam("token") String token,
                                                                   @RequestParam("email") String email,
                                                                   HttpServletRequest request){

       Map<String, Object> response = new LinkedHashMap<>();
       User user = userService.getUserByEmail(email);
       VerificationToken verificationToken = verificationTokenService.getTokenByUser(user);
       if (verificationToken == null || !verificationToken.getToken().equals(token)){
           response.put("cause", "Code is not correct");
           response.put("status", HttpStatus.OK);
           return new ResponseEntity<>(response, HttpStatus.OK);
       }

       Calendar cal = Calendar.getInstance();
       if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0){
          response.put("cause", "The code has expired. A new code has been sent to your email.");
          response.put("status", HttpStatus.OK);
          verificationTokenService.deleteByUser(user);
          publisher.publishEvent(new OnRegistrationCompleteEvent(
                  user,
                  request.getLocale()));
          return new ResponseEntity<>(response, HttpStatus.OK);
       }

       user.setEnable(true);
       userService.enableAfterRegistration(user);

       response.put("cause", "Account verified");
       response.put("status", HttpStatus.OK);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send-code")
    public ResponseEntity<Map<String, Object>> sendVerificationCode(@RequestParam("email")String email, HttpServletRequest request){
        Map<String, Object> response = new LinkedHashMap<>();
        User user = userService.getUserByEmail(email);
        if (user == null){
            response.put("cause", "User with this email not found");
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        verificationTokenService.deleteByUser(user);
        publisher.publishEvent(new OnRegistrationCompleteEvent(
                user,
                request.getLocale()));
        response.put("cause", "A confirmation code has been sent to your email.");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
