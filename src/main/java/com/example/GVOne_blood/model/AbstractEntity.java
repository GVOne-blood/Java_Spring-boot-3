package com.example.GVOne_blood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;


@Getter
@Setter
@MappedSuperclass //map với các entity sẽ extends từ nó để sử dụng các trường createdAt, updatedAt
public abstract class AbstractEntity<T extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;

    @Column(name = "created_by")
    @CreatedBy //cập nhật người tạo
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_at")
    @CreationTimestamp //cập nhật thời gian khi tạo
    @Temporal(TemporalType.TIMESTAMP)
    private String createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp //cập nhật thời gian khi update
    @Temporal(TemporalType.TIMESTAMP)
    private String updatedAt;


}
