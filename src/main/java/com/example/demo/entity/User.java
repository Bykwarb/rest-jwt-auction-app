package com.example.demo.entity;

import com.example.demo.entity.lot.Lot;
import com.example.demo.security.enums.Role;
import com.example.demo.security.enums.Status;
import com.example.demo.util.ValidEmail;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidEmail
    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    private Double balance;

    @Column(name = "enabled")
    private Boolean isEnable;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "creator", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Lot> userLots = new ArrayList<>();


    public User() {
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Lot> getUserLots() {
        return userLots;
    }

    public void setUserLots(List<Lot> userLots) {
        this.userLots = userLots;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role && Objects.equals(registrationDate, user.registrationDate) && Objects.equals(balance, user.balance) && Objects.equals(isEnable, user.isEnable) && status == user.status && Objects.equals(userLots, user.userLots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, role, registrationDate, balance, isEnable, status, userLots);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", registrationDate=" + registrationDate +
                ", balance=" + balance +
                ", isEnable=" + isEnable +
                ", status=" + status +
                ", userLots=" + userLots +
                '}';
    }
}
