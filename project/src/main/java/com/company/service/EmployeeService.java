package com.company.service;

import com.company.dto.EmployeeDTO;
import com.company.entity.AttachEntity;
import com.company.entity.EmployeeEntity;
import com.company.entity.SubjectEntity;
import com.company.enums.EmployeeRole;
import com.company.enums.ItemStatus;
import com.company.repository.EmployeeRepository;
import com.company.repository.SubjectRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AttachService attachService;

    public EmployeeDTO create(EmployeeDTO dto){
        EmployeeEntity entity = new EmployeeEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setStatus(ItemStatus.ACTIVE);
        entity.setRole(dto.getRole());
        entity.setContact(dto.getContact());
        entity.setInfo(dto.getInfo());
        entity.setAddress(dto.getAddress());
        entity.setSubject(subjectService.getById(dto.getSubject().getId()));
        entity.setAttach(attachService.get(dto.getAttach().getId()));
        employeeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<EmployeeDTO> list(){
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDTO update(EmployeeDTO dto){
        Integer id = JwtUtil.decodeJwt(dto.getJwt());
        EmployeeEntity employee = getById(id);
        if (dto.getName() != null) {
            employee.setName(dto.getName());
        } if (dto.getSurname() != null) {
            employee.setSurname(dto.getSurname());
        } if (dto.getMiddleName() != null) {
            employee.setMiddleName(dto.getMiddleName());
        } if (dto.getContact() != null) {
            employee.setContact(dto.getContact());
        } if (dto.getEmail() != null) {
            employee.setEmail(dto.getEmail());
        } if (dto.getInfo() != null) {
            employee.setInfo(dto.getInfo());
        } if (dto.getAddress() != null) {
            employee.setAddress(dto.getAddress());
        } if (dto.getRole() != null) {
            employee.setRole(dto.getRole());
        } if (dto.getStatus() != null) {
           employee.setStatus(dto.getStatus());
        }
        employeeRepository.save(employee);
        return toDto(employee);
    }

    public void delete(String jwt)
    {
        Integer id = JwtUtil.decodeJwt(jwt);
        employeeRepository.delete(getById(id));
    }

    public List<EmployeeDTO> filterByRole(EmployeeRole role){
        List<EmployeeEntity> employeeEntities = employeeRepository.filterByRole(role);
        return employeeEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeEntity getById(Integer id){
        return employeeRepository.getById(id);
    }

    public EmployeeDTO toDto(EmployeeEntity entity){
        EmployeeDTO dto = new EmployeeDTO();
        dto.setJwt(JwtUtil.createJwtById(entity.getId()));
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setContact(entity.getContact());
        dto.setInfo(entity.getInfo());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreatedDate(entity.getCreatedDate());
        SubjectEntity subject=subjectService.getById(entity.getSubject().getId());
        if(subject != null) {
            dto.setSubject(subjectService.toDto(subject));
        }
        AttachEntity attach=attachService.get(entity.getAttach().getId());
        dto.setAttach(attachService.toDto(attach));
        return dto;
    }

}
