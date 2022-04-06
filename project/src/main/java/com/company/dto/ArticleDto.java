package com.company.dto;

import com.company.enums.ArticleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private Integer id;

    private String contentUZ;

    private String contentRU;

    private String photo;

    private AttachDTO attach;

    private String jwt;

    private ArticleEnum status;

}
