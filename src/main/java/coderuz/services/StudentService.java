package coderuz.services;

import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;
import coderuz.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO create(StudentDTO studentDTO) {
        StudentEntity studentEntity = toEntity(studentDTO);

        studentRepository.save(studentEntity);
        studentDTO.setId(studentEntity.getId());
        studentDTO.setCreatedAt(studentEntity.getCreatedAt());

        return studentDTO;
    }

    public List<StudentDTO> findAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> list = new LinkedList<>();

        for(StudentEntity studentEntity: iterable){

            list.add(toDTO(studentEntity));
        }
        return list;
    }

    public StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setSurname(studentEntity.getSurname());
        studentDTO.setAge(studentEntity.getAge());
        studentDTO.setGender(studentEntity.getGender());
        studentDTO.setLevel(studentEntity.getLevel());
        studentDTO.setCreatedAt(studentEntity.getCreatedAt());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setLevel(studentDTO.getLevel());
        return studentEntity;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional =  studentRepository.findById(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
        return toDTO(optional.get());
    }

    public StudentDTO updateById(Integer id, StudentDTO studentDTO) {
        Optional<StudentEntity> optional =  studentRepository.findById(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
        StudentEntity entity = optional.get();
        entity.setName(studentDTO.getName());
        entity.setSurname(studentDTO.getSurname());
        entity.setAge(studentDTO.getAge());
        entity.setGender(studentDTO.getGender());
        entity.setLevel(studentDTO.getLevel());
        studentRepository.save(entity);

        return toDTO(entity);

    }

    public String deleteById(Integer id) {
        Optional<StudentEntity> optional =  studentRepository.findById(id);
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
         studentRepository.deleteById(id);
        return "Student with id " + id + " has been deleted";
    }
}
