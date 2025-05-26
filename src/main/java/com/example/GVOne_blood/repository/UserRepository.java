package com.example.GVOne_blood.repository;

import com.example.GVOne_blood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    public List<User> findAll();

    // AND, OR, NOT
    public List<User> findUserByFirstNameAndLastName(String firstName, String lastName);

    // BEFORE, AFTER, BETWEEN
    public List<User> findUserByDateOfBirthBefore(Date dateOfBirth);

    @Query("SELECT u.password FROM User u WHERE u.username = ?1")
    public String findPasswordByUsername(String username);
}
