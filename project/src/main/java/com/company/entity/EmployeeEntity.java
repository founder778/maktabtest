package com.company.entity;

import com.company.enums.EmployeeRole;
import com.company.enums.ItemStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String middleName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private EmployeeRole role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ItemStatus status;
    @Column
    private String contact;
    @Column
    private String email;
    @Column
    private String info;
    @Column
    private String address;
    @Column
    private LocalDate createdDate=LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "attach_id")
    private AttachEntity attach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject")
    private SubjectEntity subject;
}
