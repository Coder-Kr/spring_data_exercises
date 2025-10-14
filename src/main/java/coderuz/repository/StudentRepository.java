package coderuz.repository;

import coderuz.dto.StudentNameSurnameDTO;
import coderuz.entity.StudentCourseEntity;
import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import coderuz.mapper.StudentInfoMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {

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
    List<StudentEntity> findAllByGender(@Param("genderParam") Gender gender);

    @Query("from StudentEntity where name=:nameParam and surname=:surnameParam and age=:ageParam")
    List<StudentEntity> findAllByDetail(@Param("nameParam") String name, @Param("surnameParam") String surname, @Param("ageParam") Integer age);

    @Query("from StudentEntity where name=?1 and surname=?2 and age=?3")
    List<StudentEntity> findByAllByDetailPositional(String name, String surname, Integer age);

    @Query(value = "select * from student where name=?1 and surname=?2 and age=?3", nativeQuery = true)
    List<StudentEntity> findByAllByDetailPositionalNative(String name, String surname, Integer age);

//    @Modifying
//    @Transactional
//    @Query(value = "update student set name=?1, surname=?2 where id=?3", nativeQuery = true)
//    int updateNameAndSurname(String name, String surname, Integer id);

    @Modifying
    @Transactional
    @Query(value = "update StudentEntity set name=?1, surname=?2 where id=?3")
    int updateNameAndSurname(String name, String surname, Integer id);

    @Modifying
    @Transactional
    @Query("delete from StudentEntity where name=?1 and surname=?2")
    void deleteByNameAndSurname(String name, String surname);

    @Query("From StudentEntity order by name asc ")
    List<StudentEntity> findAllOrderByNameAsc();

    @Modifying
    @Transactional
    @Query("insert into StudentEntity(name, surname, gender, age, level) values(:name, :surname, :gender, :age, :level)")
    StudentEntity createByQuery(@Param("name") String name, @Param("surname") String surname, @Param("gender") Gender gender, @Param("age") Integer age, @Param("level") Integer level);

    @Query("from StudentEntity ")
    List<StudentEntity> findAllByQuery();

    @Query("from StudentEntity where id=:idParam")
    Optional<StudentEntity> findById(@Param("idParam") int idParam);


    @Modifying
    @Query("update StudentEntity set name=:name, surname=:surname, level=:level, age=:age, gender=:gender where id=:id")
    void updateStudentById(@Param("name") String name, @Param("surname") String surname, @Param("level") Integer level, @Param("age") Integer age, @Param("gender") Gender gender, @Param("id") Integer id);

    @Modifying
    @Query("delete StudentEntity where id=:idParam")
    void deleteByIdQ(@Param("idParam") Integer idParam);

    @Query("from StudentEntity where name=:nameParam")
    Optional<StudentEntity> findByNameQ(@Param("nameParam") String nameParam);

    @Query("from StudentEntity where surname=:surnameParam")
    Optional<StudentEntity> findBySurnameQ(@Param("surnameParam") String surnameParam);

    @Query("from StudentEntity where createdAt between :fromDate and :toDate")
    List<StudentEntity> findByDate(@Param("fromDate") LocalDateTime fromDate,  @Param("toDate") LocalDateTime toDate);

    //=========Partial select==========//
    @Query("Select s.name, s.surname from StudentEntity s")
    List<Object[]> getAllNameList();

    //=========Constructor select==========//
    @Query("Select new StudentEntity(s.surname, s.level) from StudentEntity s")
    List<StudentEntity> getAllStudents();

    //=========non entity constructor==========//
    @Query("Select new coderuz.dto.StudentNameSurnameDTO(s.name, s.surname) from StudentEntity s")
    List<StudentNameSurnameDTO> findAllStudentNameSurnameDTO();

    //=========Interface Mapper==========//
    @Query("select s.id as id, s.name as name, s.surname as surname from StudentEntity s")
    List<StudentInfoMapper> getStudentInfo();

    //=========Pagination==========//

    Page<StudentEntity> findByName(String name, Pageable pageable); //with name query generation

    @Query("select s from StudentEntity s where s.name=?1 and s.age=?2")
    Page<StudentEntity> findAllByNameAndAge(String name, int age, Pageable pageable);

}
