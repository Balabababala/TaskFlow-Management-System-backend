package com.example.demo.model.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "status_master")
public class StatusMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String label;

    private String description;
}
