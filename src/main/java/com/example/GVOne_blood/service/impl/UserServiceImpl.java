package com.example.GVOne_blood.service.impl;

import com.example.GVOne_blood.dto.request.AddressDTO;
import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.dto.response.ResponseUserDetail;
import com.example.GVOne_blood.model.Address;
import com.example.GVOne_blood.model.User;
import com.example.GVOne_blood.repository.UserRepository;
import com.example.GVOne_blood.service.UserService;
import com.example.GVOne_blood.util.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

@RequiredArgsConstructor // annotation của lombook sẽ tiêm constructor vào các field final thay cho @Autowired
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void addUser(UserRequestDTO userRequestDTO) {

    }

    @Override
    public long saveUser(UserRequestDTO userRequestDTO) {
        User user = User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .phone(userRequestDTO.getPhone())
                .email(userRequestDTO.getEmail())
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .gender(userRequestDTO.getGender())
                .userName(userRequestDTO.getUserName())
                .passWord(userRequestDTO.getPassWord())
                .type(userRequestDTO.getUserType())
                .status(userRequestDTO.getUserStatus())
                .addresses(convertToAddress(userRequestDTO.getAddresses()))
                .build();
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void updateUser(UserRequestDTO userRequestDTO) {

    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public void changeStatus(Long id, UserStatus status) {

    }

    @Override
    public ResponseUserDetail getUserDetail(Long userId) {
        return null;
    }

    @Override
    public List<UserRequestDTO> getListUser(int pageNo, int pageSize) {
        return List.of();
    }


    public Set<Address> convertToAddress(Set<AddressDTO> addresses){
        Set<Address> result = new HashSet<>();

        addresses.forEach(a ->
                result.add(Address.builder()
                                .addressType(a.getAddressType())
                                .floor(a.getFloor())
                                .city(a.getCity())
                                .country(a.getCountry())
                                .streetNumber(a.getStreetNumber())
                                .street(a.getStreet())
                                .apartmentNumber(a.getApartmentNumber())
                                .building(a.getBuilding())
                        .build()));
    return result;
    }

}
