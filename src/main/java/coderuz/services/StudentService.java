package coderuz.services;

import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;
import coderuz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO create(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setLevel(studentDTO.getLevel());

        studentRepository.save(studentEntity);
        studentDTO.setId(studentEntity.getId());
        studentDTO.setCreatedAt(studentEntity.getCreatedAt());

        return studentDTO;
    }
}
