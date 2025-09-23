package coderuz.repository;

import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

    List<StudentEntity> findByName(String name);
    List<StudentEntity> findBySurname(String name);
    List<StudentEntity> findByLevel(Integer level);
    List<StudentEntity> findByAge(Integer age);
    List<StudentEntity> findByGender(Gender gender);
    List<StudentEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
