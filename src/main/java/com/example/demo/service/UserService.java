package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.security.enums.Role;

public interface UserService {
    void registration(String email, String password, String username);
    void enableAfterRegistration(User user);
    User save(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void changeUserRoleById(Long id, Role newRole);
    void changeUserRoleByUser(User user, Role newRole);
    void depositByUserId(Long id, Double value);
    void depositByUser(User user, Double value);
    void paymentByUserId(Long id, Double value);
    void paymentByUser(User user, Double value);
    boolean existByEmail(String email);

}
