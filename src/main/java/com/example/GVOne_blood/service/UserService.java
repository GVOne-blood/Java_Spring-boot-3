package com.example.GVOne_blood.service;

import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.dto.response.PageResponse;
import com.example.GVOne_blood.dto.response.ResponseUserDetail;
import com.example.GVOne_blood.util.UserStatus;

import java.util.List;

public interface UserService {
    public void addUser(UserRequestDTO userRequestDTO);

    long saveUser(UserRequestDTO userRequestDTO);

    void updateUser(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(long id);

    void changeStatus(Long id, UserStatus status);

    ResponseUserDetail getUserDetail(Long userId);

    // đối với các API về phân trang, ta trả về 1 đối tượng PageResponse để cung cấp cho FE thông tin cần thiết như số lượng record, các record trong 1 page
    PageResponse<?> getListUser(int pageNo, int pageSize, String sortBy);

   PageResponse<?> getListUserBySortingFields(int pageNo, int pageSize, String... sortBy);
}
