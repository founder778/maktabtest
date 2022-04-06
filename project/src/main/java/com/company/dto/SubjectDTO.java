package com.company.dto;

import com.company.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectDTO {
    private Integer id;
    private String name;
}
