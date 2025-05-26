package com.example.GVOne_blood.service.impl;


import com.example.GVOne_blood.model.Role;
import com.example.GVOne_blood.repository.RoleRepository;
import com.example.GVOne_blood.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    public final RoleRepository roleRepository;
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        return roleRepository.getAllByUserId(userId);
    }
}
