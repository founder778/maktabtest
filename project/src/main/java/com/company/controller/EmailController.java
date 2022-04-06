package com.company.controller;

import com.company.dto.EmailDTO;
import com.company.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmailDTO dto){
        return ResponseEntity.ok(emailService.create(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(emailService.list());
    }

    @PutMapping("/update")
    public ResponseEntity<EmailDTO> update(@RequestBody EmailDTO dto){
        return ResponseEntity.ok(emailService.update(dto));
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") Integer id){
        emailService.delete(id);
        return "Deleted";
    }

    @GetMapping("/verification/{id}")
    public ResponseEntity<?> verification(@PathVariable("id") Integer id){
        emailService.verification(id);
        return ResponseEntity.ok().build();
    }
}
