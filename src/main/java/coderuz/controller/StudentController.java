package coderuz.controller;

import coderuz.dto.StudentDTO;

import coderuz.enums.Gender;
import coderuz.mapper.StudentInfoMapper;
import coderuz.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
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
        StudentDTO result = studentService.getById(id);
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

    @GetMapping("/findByName")
    private ResponseEntity<List<StudentDTO>> findAllByName(@RequestParam(value = "name") String name) {
        List<StudentDTO> result = studentService.findAllByName(name);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findBySurname")
    private ResponseEntity<List<StudentDTO>> findAllBySurname(@RequestParam(value = "surname") String surname) {
        List<StudentDTO> result = studentService.findAllBySurname(surname);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findByLevel")
    private ResponseEntity<List<StudentDTO>> findAllByLevel(@RequestParam(value = "level") Integer level) {
        List<StudentDTO> result = studentService.findAllByLevel(level);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findByAge")
    private ResponseEntity<List<StudentDTO>> findAllByAge(@RequestParam(value = "age") Integer age) {
        List<StudentDTO> result = studentService.findAllByAge(age);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findByGender")
    private ResponseEntity<List<StudentDTO>> findAllByGender(@RequestParam(value = "gender") Gender gender) {
        List<StudentDTO> result = studentService.findAllByGender(gender);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findByDate")
    private ResponseEntity<List<StudentDTO>> findAllByCreatedAtDate(@RequestParam("date") LocalDate date) {
        List<StudentDTO> result = studentService.findAllByCreatedAtDate(date);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findByDateBetween")
    private ResponseEntity<List<StudentDTO>> findAllByCreatedAtBetween(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        List<StudentDTO> result = studentService.findAllByCreatedAtBetween(start, end);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByDetail")
    private ResponseEntity<List<StudentDTO>> findAllByDetail(@RequestBody StudentDTO studentDTO) {
        List<StudentDTO> result = studentService.findByAllByDetailPositionalNative(studentDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/deleteByNameAndSurname")
    private ResponseEntity<String> deleteByNameAndSurname(@RequestBody StudentDTO studentDTO) {
        studentService.deleteByNameAndSurname(studentDTO);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/getAllNameList")
    private ResponseEntity<List<StudentDTO>> getAllNameList(){
        List<StudentDTO> result = studentService.getAllNameList();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getShortInfo")
    private ResponseEntity<List<StudentInfoMapper>> getShortInfo(){
        List<StudentInfoMapper> result = studentService.getShortInfo();
        return ResponseEntity.ok().body(result);
    }
}
