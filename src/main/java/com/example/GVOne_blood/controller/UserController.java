package com.example.GVOne_blood.controller;

import com.example.GVOne_blood.dto.request.UserRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping( value = "/", headers = "apiKey = 1.0")
    public String addUser() {
        return "User added successfully";
    }
    // Muốn được validate thì phải thêm @Valid
    @PutMapping("/{userId}")
    public UserRequestDTO updateUser(@PathVariable String userId, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println("Update user successfully");
        return userRequestDTO;
    }
    @PatchMapping("/{userId}")
    public UserRequestDTO updateLimitedField(@PathVariable String userId, @RequestBody(required = false) int status) {
        System.out.println("Update user successfully with status = " + status);
        return new UserRequestDTO("Join", "join123@gmail.com", "join123", "1234567890");
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable(name = "userId") String id) {
        return "User deleted successfully";
    }
    @GetMapping("/detail/{userId}")
    public UserRequestDTO getUserDetail(@PathVariable String userId) {
        return new UserRequestDTO("My", "@gmail.com", "my234", "1234565432");
    }
    @GetMapping("/list")
    public List <UserRequestDTO> getListUser( @RequestParam(defaultValue = "1") @Max(10) int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return List.of
                (new UserRequestDTO("Join", "join123@gmail.com", "join123", "1234567890"),
        new UserRequestDTO("My", "@gmail.com", "my234", "1234565432")    );
    }

}
