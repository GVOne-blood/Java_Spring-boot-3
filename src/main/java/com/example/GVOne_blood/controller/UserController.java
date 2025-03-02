package com.example.GVOne_blood.controller;

import com.example.GVOne_blood.dto.response.*;
import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.repository.SearchRepository;
import com.example.GVOne_blood.service.UserService;
import com.example.GVOne_blood.util.UserStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SearchRepository searchRepository;

    @PostMapping( value = "/") // header  = "apiKey = 1.0"
    public ResponseData<Long> addUser(@Valid @RequestBody UserRequestDTO user) {
        try {
            long userId = userService.saveUser(user);
            return new ResponseData<Long>(HttpStatus.CREATED.value(), "User added successful", userId);
            // muốn trả về phản hồi theo ý muốn ta sẽ try catch và trả về ResponseError
        }
        catch(Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), user);
        }
    }
    // Muốn được validate thì phải thêm @Valid
    // sử dụng ResponseStatus tự custom có nhược điểm là khó trả về message chuẩn cho các đội khác sử dụng API
    // để khắc phục điều này, ta dùng @Operation để trả về message chuẩn
    @Operation (summary = "Update user", description = "Update user by userId", responses = {
            @ApiResponse(responseCode = "201", description = "User updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        examples = @ExampleObject (name = "User updated successfully", summary = "updated",
                                        value = """
                                                {
                                                    "message": "User updated successfully",
                                                    "status": 201
                                                    "data": 1
                                                }
                                                """) )
            ),
    })
    // và thay vì dùng @Operation với khai báo phức tạp như trên, ta chỉ cần khai báo 1 đối tượng kiểu generic <T>.
    // Với đối tượng này, nó sẽ tự động mang theo các thông tin cần thiết như status, message, data
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<?> updateUser( @Min(1) @PathVariable Long userId ,
    @Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            userService.updateUser(userId, userRequestDTO);
            return new ResponseData<>(HttpStatus.OK.value(), "User updated successfully", userId);
        }
        catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
// Sử dụng ResponseData sẽ trả về đủ các thông tin cần thiết như status, message, data
    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@Min(1) @PathVariable Long userId, @RequestParam(required = false) UserStatus status) {
        try {
            userService.changeStatus(userId, status);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
            // vì status kiểu int nên ta trả về value của HttpStatus để tránh lỗi
        }
        catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    // Tương tự ta trả về ResponseError với những API không chạy đúng
    @DeleteMapping("/{userId}")
   // @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseData<?> deleteUser(@PathVariable(name = "userId") String id) {
        try {
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully", 1);
        }
        catch(Exception e) {
           return new ResponseError(HttpStatus.BAD_GATEWAY.value(), "User can't be deleted successfully");

        }
    }
    @GetMapping("/detail/{userId}")
    public ResponseData<?> getUserDetail(@PathVariable Long userId) {
        try{
        return new ResponseData<>(HttpStatus.OK.value(), "User detail",
                userService.getUserDetail(userId));
    }
        catch(Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        }
    @GetMapping("/list")
    public ResponseData<?> getListUser( @RequestParam(defaultValue = "1") @Max(10) int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) String sortBy) {
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "Get list user successfully", userService.getListUser(pageNo, pageSize, sortBy));
        }
        catch(Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation (summary = "Get list of users with sorting fields by asc or desc",
            description = "Sent a request via thi API (contains pageNo, pageSize and a String has a format like:" +
                    " <field_name1>:<sort_direction>, <field_name2>:<sort_direction>)")
    @GetMapping("/list-sorted-by-multiple-columns")
    public ResponseData<?> userFilter(@Max(10) @RequestParam( defaultValue = "1", required = false) int pageNo,
                                                             @RequestParam( defaultValue = "20", required = false) int pageSize,
                                                             @RequestParam(required = false) String... sortBy){
        try {
        return new ResponseData<>(HttpStatus.OK.value(), "get list user by multy sorted columns successfully",
                userService.getListUserBySortingFields(pageNo, pageSize, sortBy));
        }
        catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/list-sorted-by-column-and-search")
    public ResponseData<?> userSingleSortAndSearch(@Max(10) @RequestParam( defaultValue = "1", required = false) int pageNo,
                                                   @RequestParam( defaultValue = "20", required = false) int pageSize,
                                                   @RequestParam(required = false) String sortBy,
                                                   @RequestParam(required = false) String search){
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "searching successfully",
                    searchRepository.getAllUsersWithSortColumnAndSearch(pageNo, pageSize, sortBy, search));
        }
        catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
