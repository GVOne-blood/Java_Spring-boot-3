package com.example.GVOne_blood.service.impl;

import com.example.GVOne_blood.dto.request.AddressDTO;
import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.dto.response.ResponseUserDetail;
import com.example.GVOne_blood.exception.custom.SourceNotFoundException;
import com.example.GVOne_blood.model.Address;
import com.example.GVOne_blood.model.User;
import com.example.GVOne_blood.repository.UserRepository;
import com.example.GVOne_blood.service.UserService;
import com.example.GVOne_blood.util.UserStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
                .type(userRequestDTO.getType())
                .status(userRequestDTO.getStatus())
//                .addresses(convertToAddress(userRequestDTO.getAddresses()))

                        .build();
                userRequestDTO.getAddresses().forEach(a ->
                        user.saveAddress(Address.builder()
                                        .building(a.getBuilding())
                                        .floor(a.getFloor())
                                        .apartmentNumber(a.getApartmentNumber())
                                        .street(a.getStreet())
                                        .streetNumber(a.getStreetNumber())
                                        .country(a.getCountry())
                                        .addressType(a.getAddressType())
                                        .city(a.getCity())
                                .build())
                );
        userRepository.save(user);
        return user.getId();

    }

    @Override
    public void updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = getUserById(userId);
        //với các trường dữ liệu đã validate nhưng vẫn chưa vét hết trường hợp, ta cần check trong service
        //VD
        if (StringUtils.hasLength(userRequestDTO.getUserName()))
            user.setUserName(userRequestDTO.getUserName());
        if (!userRequestDTO.getEmail().equals(user.getEmail()))
            user.setEmail(userRequestDTO.getEmail());
        userRequestDTO.getAddresses().forEach(a ->
                user.saveAddress(Address.builder()
                        .building(a.getBuilding())
                        .floor(a.getFloor())
                        .apartmentNumber(a.getApartmentNumber())
                        .street(a.getStreet())
                        .streetNumber(a.getStreetNumber())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .city(a.getCity())
                        .build())
        );

        user.setGender(userRequestDTO.getGender());
        user.setType(userRequestDTO.getType());
        user.setUserName(userRequestDTO.getUserName());
        user.setPassWord(userRequestDTO.getPassWord());
        user.setDateOfBirth(userRequestDTO.getDateOfBirth());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setPhone(userRequestDTO.getPhone());
        user.setStatus(userRequestDTO.getStatus());
        //other fields will map with ModelMapper
//        ModelMapper mapper = new ModelMapper();
//        mapper.map(userRequestDTO, user);

        userRepository.save(user);

    }

    @Override
    public void deleteUser(long id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id, UserStatus status) {
        User user = getUserById(id);
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public ResponseUserDetail getUserDetail(Long userId) {
        User user = getUserById(userId);
       return ResponseUserDetail.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Override
    public List<ResponseUserDetail> getListUser(int pageNo, int pageSize) {
        Pageable pageable  = PageRequest.of(pageNo - 1, pageSize); //thiết lập phân trang bằng đối trượng pageable của spring
        //vì phần trang của spring tính từ 0, nên muốn đánh số trang theo stt thì phải chỉnh pageNo
        Page<User> users = userRepository.findAll(pageable);  // lấy ra danh sách user theo phân trang

        return users.stream().map(a -> ResponseUserDetail.builder() //chuyển đổi từ User sang ResponseUserDetail
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .phone(a.getPhone())
                .email(a.getEmail())
                .build()).toList();
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

    private User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new SourceNotFoundException("User not found") );
    }


}
