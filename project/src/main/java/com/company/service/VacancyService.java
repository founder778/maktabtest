package com.company.service;

import com.company.dto.VacancyDto;
import com.company.entity.VacancyEntity;
import com.company.enums.Language;
import com.company.enums.VacancyStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.VacancyRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacancyService {
    @Autowired
    private VacancyRepository vacancyRepository;

    public VacancyDto create(VacancyDto vacancyDto){
        VacancyEntity entity=new VacancyEntity();
        entity.setContent_ru(vacancyDto.getContent_ru());
        entity.setContent_uz(vacancyDto.getContent_uz());
        entity.setTitle_ru(vacancyDto.getTitle_ru());
        entity.setTitle_uz(vacancyDto.getTitle_uz());
        if (vacancyDto.getFromSalary()<0||vacancyDto.getToSalary()<0){
            throw new BadRequestException("The salary must be greater than or equal to 0");
        }
        entity.setFromSalary(vacancyDto.getFromSalary());
        entity.setToSalary(vacancyDto.getToSalary());
        entity.setStatus(VacancyStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        vacancyRepository.save(entity);
        String jwt= JwtUtil.createJwtById(entity.getId());
        vacancyDto.setId(entity.getId());
        vacancyDto.setCreatedDate(entity.getCreatedDate());
        return vacancyDto;
    }

    public void update(VacancyDto dto){
        if (dto.getId()==null){
          throw new BadRequestException("Id can not be null");
        }
        VacancyEntity entity=vacancyRepository.getById(dto.getId());
        entity.setContent_uz(dto.getContent_uz());
        entity.setContent_ru(dto.getContent_ru());
        entity.setTitle_uz(dto.getTitle_uz());
        entity.setTitle_ru(dto.getTitle_ru());
        entity.setStatus(dto.getStatus());
        entity.setToSalary(dto.getToSalary());
        entity.setFromSalary(dto.getFromSalary());
        vacancyRepository.save(entity);
    }

    public VacancyEntity get(Integer id){
        return vacancyRepository.findById(id).orElseThrow(()->
                new ItemNotFoundException("Vacancy not found")
        );
    }

    public VacancyDto getById(Integer id, Language language){
        return toDto(get(id),language);
    }

    public void deleteByID(Integer id){
        if (!vacancyRepository.existsById(id)) {
            throw new ItemNotFoundException("Vacancy not found");
        }
        vacancyRepository.deleteById(id);
    }

    public PageImpl<VacancyDto> getAll(int page,int size,Language language){
        Pageable pageable= PageRequest.of(page,size, Sort.Direction.DESC,"createdDate");
        Page<VacancyEntity> entityPage=vacancyRepository.findAll(pageable);
        long totalElemants=entityPage.getTotalElements();
        List<VacancyEntity> entityList=entityPage.getContent();
        List<VacancyDto> dtoList= entityList.stream().map(entity -> toDto(entity,language)).toList();
        return new PageImpl<>(dtoList,pageable,totalElemants);
    }

    public VacancyDto toDto(VacancyEntity entity,Language language){
        VacancyDto dto=new VacancyDto();
        dto.setId(entity.getId());
        if (language.equals(Language.RU)) {
            dto.setContent_ru(entity.getContent_ru());
            dto.setTitle_ru(entity.getTitle_ru());
        }else {
            dto.setContent_uz(entity.getContent_uz());
            dto.setTitle_uz(entity.getTitle_uz());
        }
        dto.setStatus(entity.getStatus());
        dto.setToSalary(entity.getToSalary());
        dto.setFromSalary(entity.getFromSalary());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public VacancyDto blockedVacancy(Integer id,Language language){
        VacancyEntity entity=get(id);
        entity.setStatus(VacancyStatus.BLOCK);
        vacancyRepository.save(entity);
        return toDto(entity,language);
    }
}
