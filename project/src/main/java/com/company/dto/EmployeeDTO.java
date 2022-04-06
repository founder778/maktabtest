package com.company.dto;

import com.company.enums.EmployeeRole;
import com.company.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Integer id;
    private String jwt;
    @NotEmpty(message = "Name can not be empty")
    private String name;
    @NotEmpty(message = "Surname can not be empty")
    private String surname;
    @NotEmpty(message = "Middle Name can not be empty")
    private String middleName;
    @NotEmpty(message = "Role can not be empty")
    private EmployeeRole role;
    private ItemStatus status;
    @NotEmpty(message = "Contact can not be empty")
    private String contact;
    private String email;
    private String info;
    @NotEmpty(message = "Address can not be empty")
    private String address;
    private LocalDate createdDate;
    private SubjectDTO subject;
    private AttachDTO attach;

}
