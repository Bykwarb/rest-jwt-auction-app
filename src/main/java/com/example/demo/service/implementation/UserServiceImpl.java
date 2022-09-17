package com.example.demo.service.implementation;

import com.example.demo.entity.User;
import com.example.demo.exception.EnoughMoneyException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.enums.Role;
import com.example.demo.security.enums.Status;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    @Override
    public void registration(String email, String password, String username) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setUsername(username);
        user.setRegistrationDate(LocalDateTime.now());
        user.setBalance(0.0);
        user.setStatus(Status.STATUS_ACTIVE);
        user.setEnable(false);
        user.setRole(Role.USER);
        repository.save(user);
    }

    @Override
    public void enableAfterRegistration(User user) {
        user.setEnable(true);
        repository.save(user);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }


    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return user;
        }else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void changeUserRoleById(Long id, Role newRole) {
        Optional<User> optionalUser= repository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRole(newRole);
            repository.save(user);
        }else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public void changeUserRoleByUser(User user, Role newRole) {
        user.setRole(newRole);
        repository.save(user);
    }

    @Override
    public void depositByUserId(Long id, Double value) {
        Optional<User> optionalUser= repository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setBalance(user.getBalance() + value);
            repository.save(user);
        }else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public void depositByUser(User user, Double value) {
        user.setBalance(user.getBalance() + value);
        repository.save(user);
    }

    @Override
    public void paymentByUserId(Long id, Double value) {
        Optional<User> optionalUser= repository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if ((user.getBalance() - value) < 0){
                throw new EnoughMoneyException("Enough money: " + (user.getBalance() - value));
            }else {
                user.setBalance(user.getBalance() - value);
                repository.save(user);
            }

        }else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public void paymentByUser(User user, Double value) {
        if ((user.getBalance() - value) < 0){
            throw new EnoughMoneyException("Enough money: " + (user.getBalance() - value));
        }else {
            user.setBalance(user.getBalance() - value);
            repository.save(user);
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

}
