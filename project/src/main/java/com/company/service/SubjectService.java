package com.company.service;

import com.company.dto.SubjectDTO;
import com.company.entity.EmployeeEntity;
import com.company.entity.SubjectEntity;
import com.company.enums.ItemStatus;
import com.company.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDTO create(SubjectDTO dto){
        SubjectEntity entity = new SubjectEntity();
        entity.setName(dto.getName());
        subjectRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<SubjectDTO> list(){
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();
        return subjectEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SubjectDTO update(SubjectDTO dto){
        SubjectEntity subject = getById(dto.getId());
        if (dto.getName() != null) {
            subject.setName(dto.getName());
        }
        subjectRepository.save(subject);
        return toDto(subject);
    }

    public void delete(Integer id)
    {
        subjectRepository.delete(getById(id));
    }

    public SubjectEntity getById(Integer id){
        return subjectRepository.getById(id);
    }

    public SubjectDTO toDto(SubjectEntity entity){
        SubjectDTO dto = new SubjectDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
