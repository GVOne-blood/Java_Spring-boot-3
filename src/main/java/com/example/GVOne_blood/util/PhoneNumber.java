package com.example.GVOne_blood.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
/*
* Phone number validater set up
* Constraint định nghĩa cho việc hàm xử lý chính của annotation ở class nào
* Target định nghĩa những thành phần nào của Java được phép áp dụng annotation này (hàm, biến, phương thức, ...)
* Retention chỉ định môi trường chạy annotation
* Triển khai custom annotation bằng @interface
* message() default chỉ định log mặc định khi dùng annotation
* group () default là rỗng chỉ định nhóm các ràng buộc xác thực
* payload () default cung cấp thông tin về các ràng buộc xác thực */
    @Documented
    @Constraint(validatedBy = PhoneValidaterUtil.class)
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PhoneNumber{
        String message() default "Phone number format invalid";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

