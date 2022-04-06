package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.dto.EmployeeDTO;
import com.company.enums.EmployeeRole;
import com.company.service.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Api(tags = "Employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    @ApiOperation(value = "New employee create method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class)
    })
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO dto){
        return ResponseEntity.ok(employeeService.create(dto));
    }

    @GetMapping("/list")
    @ApiOperation(value = "all employee get method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class,responseContainer = "List")
    })
    public ResponseEntity<List<EmployeeDTO>> list(){
        return ResponseEntity.ok(employeeService.list());
    }

    @PutMapping("/update")
    @ApiOperation(value = "Employee update method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class)
    })
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO dto){
        return ResponseEntity.ok(employeeService.update(dto));
    }

    @GetMapping("/filterByRole")
    @ApiOperation(value = "employee get method by employee role")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = EmployeeDTO.class,responseContainer = "List")
    })
    public ResponseEntity<List<EmployeeDTO>> filterByRole(
            @ApiParam(value = "role", required = true, example = "This id Employee role(  DIREKTOR,ZAUCH,OQITUVCHI,ISHCHI)")
            @RequestParam("role") EmployeeRole role){
        return ResponseEntity.ok(employeeService.filterByRole(role));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Employee delete method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = String.class)
    })
    public String delete(
            @ApiParam(value = "jwt", required = true, example = "This id jwt")
            @RequestParam("jwt") String jwt){
        employeeService.delete(jwt);
        return "Deleted";
    }

}
