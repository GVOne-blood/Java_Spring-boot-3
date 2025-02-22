package com.example.GVOne_blood.dto.response;

import com.example.GVOne_blood.util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseUserDetail {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
