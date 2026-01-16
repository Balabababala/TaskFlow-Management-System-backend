package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean active=true; 
    
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}

