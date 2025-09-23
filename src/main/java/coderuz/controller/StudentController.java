package coderuz.controller;

import coderuz.dto.StudentDTO;

import coderuz.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    private ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO result = studentService.create(studentDTO);
        return ResponseEntity.ok().body(result);
    }
}
