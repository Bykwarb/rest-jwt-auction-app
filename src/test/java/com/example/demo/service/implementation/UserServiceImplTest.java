package com.example.demo.service.implementation;

import com.example.demo.entity.User;
import com.example.demo.exception.EnoughMoneyException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.enums.Role;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
class UserServiceImplTest {
    private final UserService userService;
    private final UserRepository repository;
    @Autowired
    UserServiceImplTest(UserService userService, UserRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }
    @BeforeAll
    void setUp(){
        User user = new User();
        user.setBalance(0.0);
        user.setEmail("testUser1@gmail.com");
        user.setPassword("Xxagrorog123");
        user.setRole(Role.USER);
        user.setUsername("TestUser1");
        repository.save(user);

        User user2 = new User();
        user2.setBalance(0.0);
        user2.setEmail("testUser2@gmail.com");
        user2.setPassword("Xxagrorog123");
        user2.setRole(Role.USER);
        user2.setUsername("TestUser2");
        repository.save(user2);

        User user3 = new User();
        user3.setBalance(0.0);
        user3.setEmail("testUser3@gmail.com");
        user3.setPassword("Xxagrorog123");
        user3.setRole(Role.USER);
        user3.setUsername("TestUser3");
        repository.save(user3);
    }


    @Test
    void save() {
        User user = new User();
        user.setBalance(0.0);
        user.setEmail("testUser4@gmail.com");
        user.setPassword("Xxagrorog123");
        user.setRole(Role.USER);
        user.setUsername("TestUser4");
        userService.save(user);

    }

    @Test
    void getUserById() {
        System.out.println(userService.getUserById(1l));
    }

    @Test
    void getUserByEmail() {
        System.out.println(userService.getUserByEmail("testUser2@gmail.com"));
    }

    @Test
    void changeUserRoleById() {
        userService.changeUserRoleById(2l, Role.ADMIN);
    }

    @Test
    void changeUserRoleByUser() {
        User user = userService.getUserById(3l);
        userService.changeUserRoleByUser(user, Role.ADMIN);
    }

    @Test
    void depositByUserId() {
        userService.depositByUserId(1l, 330.0);
    }

    @Test
    void depositByUser() {
        User user = userService.getUserById(2l);
        userService.depositByUser(user, 22.0);
    }

    @Test
    void paymentByUserId() {
        userService.paymentByUserId(1l, 228.5);
    }

    @Test
    void paymentByUser() {
        User user = userService.getUserById(2l);
        assertThrows(EnoughMoneyException.class, () ->{
            userService.paymentByUser(user, 228.5);
        });
    }
}