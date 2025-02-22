package com.example.GVOne_blood.service;

import com.example.GVOne_blood.dto.request.UserRequestDTO;
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

    List <ResponseUserDetail> getListUser(int pageNo, int pageSize);
}
