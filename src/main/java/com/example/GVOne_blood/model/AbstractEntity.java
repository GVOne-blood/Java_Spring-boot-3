package com.example.GVOne_blood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity

@Getter
@Setter
@MappedSuperclass //map với các entity sẽ extends từ nó để sử dụng các trường createdAt, updatedAt
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp //cập nhật thời gian khi tạo
    @Temporal(TemporalType.TIMESTAMP)
    private String createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp //cập nhật thời gian khi update
    @Temporal(TemporalType.TIMESTAMP)
    private String updatedAt;

}
