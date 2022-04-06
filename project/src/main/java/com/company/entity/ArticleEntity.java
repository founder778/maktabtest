package com.company.entity;

import com.company.enums.ArticleEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contentUZ;

    private String contentRU;

    private LocalDateTime created_date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ArticleEnum status;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn()
    private AttachEntity attach;
}
