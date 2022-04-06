package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime created_date = LocalDateTime.now();

    @Column(name = "key")
    private String key; //8b19f7d6-5a8e-4209-8303-139bc6667837
    @Column(name = "origin_name")
    private String originName;
    @Column(name = "size")
    private Long size;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "extension")
    private String extension;
}
