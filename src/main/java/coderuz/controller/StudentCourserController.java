package coderuz.controller;

import coderuz.dto.StudentCourseDTO;
import coderuz.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student-course")
public class StudentCourserController {

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/create")
    private ResponseEntity<StudentCourseDTO> create(@RequestBody StudentCourseDTO studentCourseDTO) {
        StudentCourseDTO result = studentCourseService.create(studentCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    private ResponseEntity<List<StudentCourseDTO>> getAll() {
        List<StudentCourseDTO> result = studentCourseService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byId/{id}")
    private ResponseEntity<StudentCourseDTO> getById(@PathVariable("id") Integer id) {
        StudentCourseDTO result = studentCourseService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<StudentCourseDTO> update(@PathVariable("id") Integer id, @RequestBody StudentCourseDTO studentCourseDTO) {
        StudentCourseDTO result = studentCourseService.update(id, studentCourseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/delete/{id}")
    private ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        String result = studentCourseService.delete(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByIdDetail/{id}")
    private ResponseEntity<StudentCourseDTO> getByIdDetail(@PathVariable("id") Integer id) {
        StudentCourseDTO result = studentCourseService.getByIdDetail(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByStudentIdDate")
    private ResponseEntity<List<StudentCourseDTO>> getByStudentIdAndCreatedAtBetween(
            @RequestParam("id") Integer studentId,
            @RequestParam("date") LocalDate date) {
        List<StudentCourseDTO> result = studentCourseService.getByStudentIdAndCreatedAtBetween(studentId, date);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByStudentIdDates")
    private ResponseEntity<List<StudentCourseDTO>> getByStudentIdAndDatesBetween(
            @RequestParam("id") Integer studentId,
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate) {
        List<StudentCourseDTO> result = studentCourseService.getByStudentIdAndDatesBetween(studentId, fromDate, toDate);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getStudetnMarks/{id}")
    private ResponseEntity<List<StudentCourseDTO>> findByStudentIdOrderByCreatedAtDesc(@PathVariable("id") Integer studentId) {
        List<StudentCourseDTO> result = studentCourseService.findByStudentIdOrderByCreatedAtDesc(studentId);
        return ResponseEntity.ok().body(result);
    }

}
