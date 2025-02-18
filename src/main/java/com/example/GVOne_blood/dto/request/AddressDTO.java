package com.example.GVOne_blood.dto.request;

import com.example.GVOne_blood.model.Address;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AddressDTO implements Serializable {
    private String apartmentNumber;
    private String floor;
    private String building;
    private String streetNumber;
    private String street;
    private String city;
    private String country;
    private Integer addressType;
}

