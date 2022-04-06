package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private Integer id;
    private String key;
    private String originName;
    private Long size;
    private String filePath;
    private String extension;
    private LocalDateTime createdDate;
    private String url;
}
