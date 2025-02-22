package com.example.GVOne_blood.model;

import com.example.GVOne_blood.util.Gender;
import com.example.GVOne_blood.util.UserStatus;
import com.example.GVOne_blood.util.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "tbl_user")

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "tbl_user")
public class User extends AbstractEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL: lưu giá trị của enum theo index, EnumType.STRING: lưu giá trị của enum theo tên, convert thành enum trong DB
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM) // Dùng để lưu enum theo tên
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String passWord;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    private UserStatus status;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    private UserType type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Address> addresses = new HashSet<>();

    public void saveAddress(Address address){
        if (address != null){
            if (addresses == null)
                addresses = new HashSet<>(); //tránh NullPointer
        }
        addresses.add(address);
        address.setUser(this); //save user_id

    }

}
