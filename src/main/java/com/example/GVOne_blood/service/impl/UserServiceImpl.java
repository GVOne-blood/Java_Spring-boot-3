package com.example.GVOne_blood.service.impl;

import com.example.GVOne_blood.dto.request.AddressDTO;
import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.dto.response.PageResponse;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public PageResponse<?> getListUser(int pageNo, int pageSize, String sortBy) {
        List <Sort.Order> sortDirection = new ArrayList<>();
//        Pageable pageable  = PageRequest.of(pageNo - 1, pageSize); //thiết lập phân trang bằng đối trượng pageable của spring
//        //vì phần trang của spring tính từ 0, nên muốn đánh số trang theo stt thì phải chỉnh pageNo

        // muốn sort data theo 1 trường dữ liệu từ client trả về, ta có 1 overload của PageRequest.of()
        // với data có dạng field:direction, ta dùng match và pattern để tách
        Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
        Matcher matcher = pattern.matcher(sortBy);
        if (matcher.find()){
            if (matcher.group(3).equalsIgnoreCase("asc")) sortDirection.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
        }
        else sortDirection.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection)); //sort  trường dũ liệy sortBy
        Page<User> users = userRepository.findAll(pageable);  // lấy ra danh sách user theo phân trang

        List <ResponseUserDetail> response =  users.stream().map(u -> ResponseUserDetail.builder()
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .phone(u.getPhone())
                .email(u.getEmail())
                .build()).toList();
        return PageResponse.builder()
                .pageSize(pageSize)
                .pageNo(pageNo)
                .totalPage(users.getTotalPages())
                .items(response)
                .build();
    }

    @Override
    public PageResponse<?> getListUserBySortingFields(int pageNo, int pageSize, String... sortBy) {
        int page = 0;
        if (pageNo > 0) page = pageNo - 1;
        List <ResponseUserDetail> listUser = new ArrayList<>();
        List <Sort.Order> sortDirection = new ArrayList<>();
        for (String field : sortBy){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(field);
        if (matcher.find()){
            if (matcher.group(3).equalsIgnoreCase("asc"))
                sortDirection.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
        }
        else sortDirection.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
        }
        Pageable pageable = PageRequest.of(page, pageSize,Sort.by(sortDirection)); //sort theo ds field
        Page<User> users = userRepository.findAll(pageable);

        List <ResponseUserDetail> response =  users.stream().map(u -> ResponseUserDetail.builder()
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .phone(u.getPhone())
                .email(u.getEmail())
                .build()).toList();
        return PageResponse.builder()
                .pageSize(pageSize)
                .pageNo(pageNo)
                .totalPage(users.getTotalPages())
                .items(response)
                .build();
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
