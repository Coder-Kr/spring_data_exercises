package coderuz.controller;

import coderuz.dto.StudentCourseDTO;
import coderuz.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-course")
public class StudentCourserController {

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/create")
    private ResponseEntity<StudentCourseDTO> create(@RequestBody StudentCourseDTO studentCourseDTO){
        StudentCourseDTO result = studentCourseService.create(studentCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    private ResponseEntity<List<StudentCourseDTO>> getAll(){
        List<StudentCourseDTO> result = studentCourseService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byId/{id}")
    private ResponseEntity<StudentCourseDTO> getById(@PathVariable("id") Integer id){
        StudentCourseDTO result = studentCourseService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<StudentCourseDTO> update(@PathVariable("id") Integer id, @RequestBody StudentCourseDTO studentCourseDTO){
        StudentCourseDTO result = studentCourseService.update(id, studentCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/delete/{id}")
    private ResponseEntity<String> deleteById(@PathVariable("id") Integer id){
        String result = studentCourseService.delete(id);
        return ResponseEntity.ok().body(result);
    }
}
