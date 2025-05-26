package com.example.GVOne_blood.model;

import com.example.GVOne_blood.util.Gender;
import com.example.GVOne_blood.util.UserStatus;
import com.example.GVOne_blood.util.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "tbl_user")
public class User extends AbstractEntity<Long> implements Serializable, UserDetails {

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
    private String username;

    @Column(name = "password")
    private String password;

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

    @OneToMany(mappedBy = "user")
    private Set<GroupHasUser> users = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserHasRole> roles = new HashSet<>();
    public void saveAddress(Address address){

        if (address != null){
            if (addresses == null)
                addresses = new HashSet<>(); //tránh NullPointer
        }
        addresses.add(address);
        address.setUser(this); //save user_id

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
