package com.example.GVOne_blood.dto.request;

import com.example.GVOne_blood.model.Address;
import com.example.GVOne_blood.util.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDTO implements Serializable {
    @NotBlank (message = "firstName must be not blank")
    private String firstName;
    @NotNull(message = "lastName must be not null")
    private String lastName;
    @Email(message = "email must be not blank")
    private String email;
    @NotBlank (message = "username must be not blank")
    private String username;
    @NotBlank
    private String password;
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
    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    private UserStatus status;

    @EnumPattern(name = "gender", regexp = "MALE|FEMALE|OTHER")
    private Gender gender;

    @EnumPattern(name = "type", regexp = " OWNER|USER|ADMIN")
    private UserType type;
//    @NotEmpty
//    private List<String> permissions;
    @NotEmpty (message = "Address is not empty")
    Set<AddressDTO> addresses ;

    public UserRequestDTO() {
    }
    public UserRequestDTO(String name, String email, String password, String phone) {
        this.lastName = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

}
