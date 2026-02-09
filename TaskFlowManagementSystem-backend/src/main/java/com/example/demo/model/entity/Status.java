package com.example.demo.model.entity;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isStartNode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = false)
    private StatusMaster statusMaster;

    @Column(columnDefinition = "json")
    private String allowedTransitions;
}
