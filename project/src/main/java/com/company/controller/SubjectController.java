package com.company.controller;

import com.company.dto.EmployeeDTO;
import com.company.dto.SubjectDTO;
import com.company.service.SubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
@Api(tags = "Subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    @ApiOperation(value = "New subject create method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = SubjectDTO.class)
    })
    public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO dto){
        return ResponseEntity.ok(subjectService.create(dto));
    }

    @GetMapping("/list")
    @ApiOperation(value = "All subject get method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = SubjectDTO.class,responseContainer = "List")
    })
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(subjectService.list());
    }


    @PutMapping("/update")
    @ApiOperation(value = "Subject update method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class)
    })
    public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO dto){
        return ResponseEntity.ok(subjectService.update(dto));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete Subject ")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class)
    })
    public String delete(@ApiParam(value = "this is subject's id",readOnly = true,example = "1")
                             @RequestParam("id") Integer id){
        subjectService.delete(id);
        return "Deleted";
    }
}
