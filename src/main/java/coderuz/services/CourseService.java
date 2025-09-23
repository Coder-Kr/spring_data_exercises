package coderuz.services;

import coderuz.dto.CourseDTO;
import coderuz.dto.StudentDTO;
import coderuz.entity.CourseEntity;
import coderuz.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO create(CourseDTO courseDTO) {
        CourseEntity entity = new CourseEntity();
        entity.setName(courseDTO.getName());
        entity.setPrice(courseDTO.getPrice());
        entity.setDuration(courseDTO.getDuration());
        courseRepository.save(entity);

        courseDTO.setId(entity.getId());
        courseDTO.setCreatedAt(entity.getCreatedAt());
        courseDTO.setUpdatedAt(entity.getUpdatedAt());
        return courseDTO;
    }

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> entity = courseRepository.findById(id);
            if(entity.isEmpty()) {
                throw new IllegalArgumentException("Course not found");
            }
            return toDTO(entity.get());
    }

    public CourseDTO toDTO(CourseEntity  courseEntity) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(courseEntity.getName());
        courseDTO.setPrice(courseEntity.getPrice());
        courseDTO.setDuration(courseEntity.getDuration());
        courseDTO.setCreatedAt(courseEntity.getCreatedAt());
        courseDTO.setUpdatedAt(courseEntity.getUpdatedAt());
        courseDTO.setId(courseEntity.getId());
        return courseDTO;
    }

    public List<CourseDTO> getAll() {
        List<CourseDTO> List = new LinkedList<>();

        Iterable<CourseEntity> iterable = courseRepository.findAll();
        if(!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Course not found");
        }
        for(CourseEntity entity : iterable){
            List.add(toDTO(entity));
        }
        return List;
    }

    public CourseDTO update(CourseDTO courseDTO, Integer id) {
        Optional<CourseEntity> entity = courseRepository.findById(id);
        if(entity.isEmpty()) {
            throw new IllegalArgumentException("Course not found");
        }
        CourseEntity entity1 = entity.get();

        entity1.setName(courseDTO.getName());
        entity1.setPrice(courseDTO.getPrice());
        entity1.setDuration(courseDTO.getDuration());
        courseRepository.save(entity1);

        return toDTO(entity1);
    }

    public String delete(Integer id) {
        Optional<CourseEntity> entity = courseRepository.findById(id);
        if(entity.isEmpty()) {
            throw new IllegalArgumentException("Course not found");
        }
        courseRepository.deleteById(id);
        return "Course deleted";
    }

    public  List<CourseDTO> getByName(String name) {
        Iterable<CourseEntity> iterable = courseRepository.findByName(name);
        if(!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Course not found");
        }
        List<CourseDTO> list = new LinkedList<>();
        for(CourseEntity entity : iterable){
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByPrice(Double price) {
        Iterable<CourseEntity> iterable = courseRepository.findByPrice(price);
        if(!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Course not found");
        }
        List<CourseDTO> list = new LinkedList<>();
        for(CourseEntity entity : iterable){
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<CourseDTO> getByDuration(Double duration) {
        Iterable<CourseEntity> iterable = courseRepository.findByDuration(duration);
        if(!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Course not found");
        }
        List<CourseDTO> list = new LinkedList<>();
        for(CourseEntity entity : iterable){
            list.add(toDTO(entity));
        }
        return list;
    }
}
