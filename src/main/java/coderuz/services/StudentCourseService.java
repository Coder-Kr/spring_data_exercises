package coderuz.services;

import coderuz.dto.StudentCourseDTO;
import coderuz.entity.StudentCourseEntity;
import coderuz.repository.StudetnCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {

    @Autowired
    private StudetnCourseRepository studetnCourseRepository;

    public StudentCourseDTO create(StudentCourseDTO studentCourseDTO) {
        StudentCourseEntity entity = new StudentCourseEntity();

        Optional<StudentCourseEntity> optional = studetnCourseRepository.findByStudentIdAndCourseId(studentCourseDTO.getStudentId(), studentCourseDTO.getCourseId());
        if (optional.isPresent()) {
            throw new IllegalArgumentException("Student already taken this course");
        }

        entity.setCourseId(studentCourseDTO.getCourseId());
        entity.setStudentId(studentCourseDTO.getStudentId());
        entity.setMark(studentCourseDTO.getMark());

        studetnCourseRepository.save(entity);

        studentCourseDTO.setId(entity.getId());
        studentCourseDTO.setCreatedAt(entity.getCreatedAt());
        studentCourseDTO.setUpdatedAt(entity.getUpdatedAt());
        return studentCourseDTO;
    }

    public List<StudentCourseDTO> findAll() {
        Iterable<StudentCourseEntity> iterable = studetnCourseRepository.findAll();
        if(!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student course not found");
        }
        List<StudentCourseDTO> list = new LinkedList<>();
        for(StudentCourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentCourseDTO getById(Integer id) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findById(id);
        if(optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        return toDTO(optional.get());
    }

    public StudentCourseDTO toDTO(StudentCourseEntity studentCourseEntity) {
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setId(studentCourseEntity.getId());
        studentCourseDTO.setMark(studentCourseEntity.getMark());
        studentCourseDTO.setCourseId(studentCourseEntity.getCourseId());
        studentCourseDTO.setStudentId(studentCourseEntity.getStudentId());
        studentCourseDTO.setCreatedAt(studentCourseEntity.getCreatedAt());
        studentCourseDTO.setUpdatedAt(studentCourseEntity.getUpdatedAt());
        return studentCourseDTO;
    }

    public StudentCourseDTO update(Integer id, StudentCourseDTO studentCourseDTO) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }

        StudentCourseEntity entity = optional.get();

        entity.setMark(studentCourseDTO.getMark());
        entity.setCourseId(studentCourseDTO.getCourseId());
        entity.setStudentId(studentCourseDTO.getStudentId());

        studetnCourseRepository.save(entity);
        studentCourseDTO.setId(entity.getId());
        studentCourseDTO.setUpdatedAt(entity.getUpdatedAt());
        studentCourseDTO.setCreatedAt(entity.getCreatedAt());
        return studentCourseDTO;

    }

    public String delete(Integer id) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        studetnCourseRepository.deleteById(id);
        return "Student course deleted";
    }


}
