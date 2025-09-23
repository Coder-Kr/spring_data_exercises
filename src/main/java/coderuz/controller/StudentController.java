package coderuz.controller;

import coderuz.dto.StudentDTO;

import coderuz.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/all")
    private ResponseEntity<List<StudentDTO>> findAll() {
        List<StudentDTO> result = studentService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byId/{id}")
    private ResponseEntity<StudentDTO> getById(@PathVariable("id") Integer id) {
        StudentDTO result =  studentService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO, @PathVariable("id") Integer id) {
        StudentDTO result = studentService.updateById(id, studentDTO);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/delete/{id}")
    private ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        String result = studentService.deleteById(id);
        return ResponseEntity.ok().body(result);
    }
}
