package com.example.GVOne_blood.controller;

import com.example.GVOne_blood.dto.response.ResponseData;
import com.example.GVOne_blood.dto.response.ResponseError;
import com.example.GVOne_blood.dto.response.ResponseSuccess;
import com.example.GVOne_blood.dto.request.UserRequestDTO;
import com.example.GVOne_blood.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping( value = "/") // header  = "apiKey = 1.0"
    public ResponseData<Integer> addUser(@Valid @RequestBody UserRequestDTO user) {
        try {
            userService.addUser(user);
            return new ResponseData<>(HttpStatus.CREATED.value(), "User added successful", 1);
            // muốn trả về phản hồi theo ý muốn ta sẽ try catch và trả về ResponseError
        }
        catch(Exception e) {

            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "User can't be added");
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
    public ResponseSuccess updateUser( @Min(1) @PathVariable String userId ,
    @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseSuccess(HttpStatus.CREATED, "User updated successfully");
    }
// Sử dụng ResponseData sẽ trả về đủ các thông tin cần thiết như status, message, data
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData<?> updateLimitedField(@Min(1) @PathVariable String userId, @RequestBody(required = false) int status) {
        System.out.println("Update user successfully with status = " + status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
        // vì status kiểu int nên ta trả về value của HttpStatus để tránh lỗi
    }
    // Tương tự ta trả về ResponseError với những API không chạy đúng
    @DeleteMapping("/{userId}")
   // @ResponseStatus (HttpStatus.NO_CONTENT)
    public ResponseError deleteUser(@PathVariable(name = "userId") String id) {
        return new ResponseError(HttpStatus.BAD_GATEWAY.value(), "User can't be deleted successfully");

    }
    @GetMapping("/detail/{userId}")
    public ResponseSuccess getUserDetail(@PathVariable String userId) {
        return new ResponseSuccess(HttpStatus.OK, "User detail",
                new UserRequestDTO("My", "@gmail.com", "my234", "1234565432"));
    }
    @GetMapping("/list")
    @ResponseStatus (HttpStatus.OK)
    public ResponseSuccess getListUser( @RequestParam(defaultValue = "1") @Max(10) int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseSuccess(HttpStatus.OK, "List user ", List.of
                (new UserRequestDTO("Join", "join123@gmail.com", "join123", "1234567890"),
        new UserRequestDTO("My", "@gmail.com", "my234", "1234565432"))    );
    }

}
