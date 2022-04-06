package com.company.entity;

import com.company.enums.VacancyStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vacancy")
public class VacancyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title_uz")
    private String title_uz;
    @Column(name = "title_ru")
    private String title_ru;
    @Column(name = "content_uz",columnDefinition = "text")
    private String content_uz;
    @Column(name = "content_ru")
    private String content_ru;
    @Column(name = "from_salary",columnDefinition = "text")
    private Long fromSalary;
    @Column(name = "to_salary")
    private Long toSalary;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VacancyStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate;


}
