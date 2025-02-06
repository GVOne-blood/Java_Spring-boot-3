package com.example.GVOne_blood.dto.request;

import com.example.GVOne_blood.util.EnumPattern;
import com.example.GVOne_blood.util.Gender;
import com.example.GVOne_blood.util.PhoneNumber;
import com.example.GVOne_blood.util.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
    private String passWord;
    //@Pattern(regexp = "^//d{10}$", message = "Phone number format exception")
    @PhoneNumber // Custom annotation
    private String phone;
    @NotNull (message = "dateOfBirth must be not null")
    @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)
    @JsonFormat (pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    //@Pattern(regexp = "^ACTIVE|INACTIVE|NONE$", message = "User status must be ACTIVE, INACTIVE or NONE")
    // pattern không validate được kiểu enum
    //private UserStatus userStatus;
    //private String userStatus;
    @EnumPattern(name = "userStatus", regexp = "ACTIVE|INACTIVE|NONE")
    private UserStatus userStatus;

    @EnumPattern(name = "gender", regexp = "MALE|FEMALE|OTHER")
    private Gender gender;

//    @NotEmpty
//    private List<String> permissions;
    public UserRequestDTO() {
    }
    public UserRequestDTO(String name, String email, String passWord, String phone) {
        this.lastName = name;
        this.email = email;
        this.passWord = passWord;
        this.phone = phone;
    }

    public UserRequestDTO(String firstName, String lastName, String email,
                          String userName, String passWord, String phone,
                          Date dateOfBirth, UserStatus userStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
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

    public String getPassWord() {
        return passWord;
    }

    public String getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }
//
//    public List<String> getPermissions() {
//        return permissions;
//    }

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
