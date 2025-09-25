package coderuz.repository;

import coderuz.entity.StudentCourseEntity;
import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {

    List<StudentEntity> findByName(String name);
    List<StudentEntity> findBySurname(String name);
    List<StudentEntity> findByLevel(Integer level);
    List<StudentEntity> findByAge(Integer age);
    List<StudentEntity> findByGender(Gender gender);
    List<StudentEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    //=======@Query========//

    @Query("from StudentEntity where age<19")
    List<StudentCourseEntity> getAllGraterThen18();

    @Query("from StudentEntity where name=:nameParam")
    List<StudentEntity> findAllByName(@Param("nameParam") String name);

    @Query("from StudentEntity where surname=:surnameParam")
    List<StudentEntity> findAllBySurname(@Param("surnameParam") String surname);

    @Query("from StudentEntity where level=:levelParam")
    List<StudentEntity> findAllByLevel(@Param("levelParam") String level);

    @Query("from StudentEntity where age=:ageParam")
    List<StudentEntity> findAllByAge(@Param("ageParam") Integer age);

    @Query("from StudentEntity where gender=:genderParam")
    List<StudentEntity> findAllByGender(@Param("genderParam")  Gender gender);


}
