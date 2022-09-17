package com.example.demo.RepositoryTests;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    private final UserRepository repository;

    @Autowired
    public UserRepositoryTest(UserRepository repository) {
        this.repository = repository;
    }


    @Test
    public void createUser(){
        User user = new User("test1@gmail.com", "Xxagrorog123", Role.USER);
        repository.save(user);
        assertTrue(repository.existsByEmail(user.getEmail()));
    }

    @Test
    public void getUserById(){
        User user = new User("test2@gmail.com", "Xxagrorog123", Role.USER);
        repository.save(user);
        assertEquals(user, repository.findById(user.getId()).get());
    }

    @Test
    public void getUserByEmail(){
        User user = new User("test3@gmail.com", "Xxagrorog123", Role.USER);
        repository.save(user);
        assertEquals(user, repository.findByEmail("test3@gmail.com"));
    }

    @Test
    public void deleteUser(){
        User user = new User("test4@gmail.com", "Xxagrorog123", Role.USER);
        repository.save(user);
        repository.delete(user);
        assertTrue(!repository.existsByEmail("test4@gmail.com"));
    }

    @Test
    public void createUserButUserWithSameEmailIsExist(){
        User user = new User("test5@gmail.com", "Xxagrorog123", Role.USER);
        repository.save(user);
        User user1 = new User("test5@gmail.com", "Xxagrorog123", Role.USER);
        assertThrows(DataIntegrityViolationException.class, ()->{
            repository.save(user1);
        });
    }
}
