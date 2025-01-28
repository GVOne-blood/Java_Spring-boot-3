package com.example.GVOne_blood.dto.request;

import com.example.GVOne_blood.util.PhoneNumber;
import com.example.GVOne_blood.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRequestDTO implements Serializable {
    @NotNull (message = "firstName must be not null")
    private String firstName;
    @NotNull(message = "lastName must be not null")
    private String lastName;
    @Email(message = "email must be not blank")
    private String email;
    @NotBlank (message = "userName must be not blank")
    private String userName;
    @NotBlank
    private String password;
    //@Pattern(regexp = "^//d{10}$", message = "Phonenumber format exception")
    @PhoneNumber // Custom annotation
    private String phone;
    @NotNull (message = "dateOfBirth must be not null")
    @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)
    @JsonFormat (pattern = "MM/dd/yyyy")
    private Date dateOfBirth;
    @NotNull
    private UserStatus userStatus;
    @NotEmpty
    private List<String> permissions;

    public UserRequestDTO(String name, String email, String password, String phone) {
        this.lastName = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public UserRequestDTO(String firstName, String lastName, String email,
                          String userName, String password, String phone,
                          Date dateOfBirth, UserStatus userStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.userStatus = userStatus;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
