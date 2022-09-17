package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.implementation.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl service;
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    //@PostMapping("/save")
    //public ResponseEntity<String> saveUser(@RequestParam("email") String email,
    //                                       @RequestParam("password") String password){
    //    User user = new User(email,password,"user");
    //    service.save(user);
    //    return ResponseEntity.ok("User saved");
    //}


    @GetMapping("/getUserByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(service.getUserByEmail(email));
    }



}
