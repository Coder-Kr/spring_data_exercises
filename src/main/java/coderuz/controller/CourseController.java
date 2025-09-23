package coderuz.controller;

import coderuz.dto.CourseDTO;
import coderuz.entity.CourseEntity;
import coderuz.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    private ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
        CourseDTO result =  courseService.create(courseDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byId/{id}")
    private ResponseEntity<CourseDTO> getById(@PathVariable("id") Integer id){
        CourseDTO result =  courseService.getById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    private ResponseEntity<List<CourseDTO>> findAll(){
        List<CourseDTO> result = courseService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<CourseDTO> update(@PathVariable("id") Integer id,@RequestBody CourseDTO courseDTO){
        CourseDTO result = courseService.update(courseDTO, id);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/delete/{id}")
    private ResponseEntity<String> deleteById(@PathVariable("id") Integer id){
        String result = courseService.delete(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byName")
    private ResponseEntity<List<CourseDTO>> findByName(@RequestParam("name") String name){
        List<CourseDTO> result = courseService.getByName(name);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByPrice")
    private ResponseEntity<List<CourseDTO>> findbyPrice(@RequestParam("price") Double price){
        List<CourseDTO> result = courseService.getByPrice(price);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getByDuration")
    private ResponseEntity<List<CourseDTO>> findbyDuration(@RequestParam("duration") Double duration){
        List<CourseDTO> result = courseService.getByDuration(duration);
        return ResponseEntity.ok().body(result);
    }
}
