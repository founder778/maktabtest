package com.company.controller;

import com.company.dto.EmployeeDTO;
import com.company.dto.VacancyDto;
import com.company.enums.Language;
import com.company.service.VacancyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/vacancy")
@Api(tags = "Vacancy")
public class VacancyController {
    @Autowired
    private VacancyService vacancyService;

    @PostMapping("/create")
    @ApiOperation(value = "New Vacancy create method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = VacancyDto.class)
    })
    public ResponseEntity<VacancyDto> create(@Valid @RequestBody VacancyDto dto){
        VacancyDto response=vacancyService.create(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Vacancy update method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = String.class)
    })
    public ResponseEntity<String> update(@Valid @RequestBody VacancyDto dto){
        vacancyService.update(dto);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Vacancy delete method by id")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = String.class)
    })
    public ResponseEntity<String> delete(@ApiParam(value = "this is vacancy's Id",readOnly = true )
                                             @PathVariable("id")Integer id){
       vacancyService.deleteByID(id);
       return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get/{id}/")
    @ApiOperation(value = "Vacancy get method by Id")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = VacancyDto.class)
    })
    public ResponseEntity<VacancyDto> getVacancy(
            @ApiParam(value ="this is vacancy's Id" ,readOnly = true)
            @PathVariable("id")Integer id,
            @ApiParam(value = "language",readOnly = true)
            @RequestParam("language") Language language){
        VacancyDto dto=vacancyService.getById(id,language);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getAll/")
    @ApiOperation(value = "All Vacancy get method ")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response =VacancyDto.class,responseContainer = "PageImpl")
    })
    public ResponseEntity<PageImpl<VacancyDto>> getAll(@RequestParam("size")int size,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("language") Language language){
        PageImpl<VacancyDto> dtos=vacancyService.getAll(page,size,language);
        return ResponseEntity.ok(dtos);
    }
}
