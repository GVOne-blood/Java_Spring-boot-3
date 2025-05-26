package com.example.GVOne_blood.service;

import com.example.GVOne_blood.model.Role;

import java.util.List;

public interface RoleService {
        public List<Role> getRolesByUserId(Long userId);
}
