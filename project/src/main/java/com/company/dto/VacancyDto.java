package com.company.dto;

import com.company.enums.VacancyStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacancyDto {
    private Integer id;
    @NotNull
    private String title_uz;
    @NotNull
    private String title_ru;
    @NotNull
    private String content_uz;
    @NotNull
    private String content_ru;
    @NotNull
    private Long fromSalary;
    @NotNull
    private Long toSalary;
    private VacancyStatus status;
    private LocalDateTime createdDate;


}
