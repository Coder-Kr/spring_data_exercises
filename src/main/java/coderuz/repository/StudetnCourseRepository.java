package coderuz.repository;

import coderuz.entity.StudentCourseEntity;
import coderuz.entity.StudentEntity;
import coderuz.mapper.StudentDetailInfoMapper;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudetnCourseRepository extends CrudRepository<StudentCourseEntity, Integer> {
    Optional<StudentCourseEntity> findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    List<StudentCourseEntity> findByStudentIdAndCreatedAtBetween(Integer studentId, LocalDateTime fromDate, LocalDateTime toDate);

    List<StudentCourseEntity> findByStudentIdOrderByCreatedAtDesc(Integer studentId);

    List<StudentCourseEntity> findByStudentIdAndCourseIdOrderByCreatedAtDesc(Integer studentId, Integer courseId);

    Optional<StudentCourseEntity> findTopByStudentIdOrderByCreatedAtDesc(Integer studentId);

    List<StudentCourseEntity> findTop3ByStudentIdOrderByCreatedAtDesc(Integer studentId);

    Optional<StudentCourseEntity> findTopByStudentIdOrderByCreatedAtAsc(Integer studentId);

    Optional<StudentCourseEntity> findByStudentIdAndCourseIdOrderByCreatedAtAsc(Integer studentId, Integer courseId);

    Optional<StudentCourseEntity> findTopByStudentIdAndCourseIdOrderByMarkDesc(Integer studentId, Integer courseId);

    List<StudentCourseEntity> findByStudentId(Integer studentId);

    StudentCourseEntity findTopByStudentId(Integer studentId);

    long countByStudentIdAndMarkGreaterThan(Integer studentId, Integer mark);

    Optional<StudentCourseEntity> findFirstByCourseIdOrderByMarkDesc(Integer courseId);

    long countByCourseId(Integer courseId);

    //========@Query annotation=========

    @Query("from StudentCourseEntity")
    List<StudentCourseEntity> findAllQ();

    @Modifying
    @Transactional
    @Query("update StudentCourseEntity set studentId=:studentId, courseId=:courseId, mark=:mark where id=:id")
    int update(Integer studentId, Integer courseId, Integer mark, Integer id);

    @Modifying
    @Transactional
    @Query("delete StudentCourseEntity where id=:id")
    void delete(@Param("id") Integer id);

    @Query("from StudentCourseEntity where id=:id")
    StudentCourseEntity findByIdQ(Integer id);

    @Query("from StudentCourseEntity where createdAt between :fromDate and :toDate and studentId=:studentId")
    List<StudentCourseEntity> getByDates(
            @Param("studentId") Integer studentId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate);

    @Query("from StudentCourseEntity where studentId=:studentId order by createdAt desc ")
    List<StudentCourseEntity> findByMarkDateDesc(@Param("studentId") Integer studentId);

    @Query("from StudentCourseEntity where courseId=:courseId order by createdAt desc")
    List<StudentCourseEntity> getByCourseIdMarkDateDesc(@Param("courseId") Integer courseId);

    @Query(value = "select * from student_course where student_id=:studentId order by created_at desc limit 1", nativeQuery = true)
    Optional<StudentCourseEntity> getLastMarkOfStudent(@Param("studentId") Integer studentId);

    @Query(nativeQuery = true, value = "select * from student_course where student_id=:studentId order by mark desc limit 3")
    List<StudentCourseEntity> getLast3TopMarkOfStudent(@Param("studentId")  Integer studentId);

    @Query(nativeQuery = true, value = "select * from student_course where student_id=:studentId order by created_at asc limit 1")
    StudentCourseEntity getFirstMarkOfStudent(@Param("studentId") Integer studentId);

    @Query(nativeQuery = true, value = "select * from student_course where student_id=:studentId and course_id=:courseId order by created_at asc limit 1")
    StudentCourseEntity getFirstMarkOfCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query(nativeQuery = true, value = "select * from student_course sc where sc.student_id=:studentId and sc.course_id=:courseId order by mark desc limit 1")
    StudentCourseEntity getTopMarkOfCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select avg(mark) from StudentCourseEntity where studentId=?1")
    double getAvgMarkOfStudent(Integer studentId);

    @Query("select avg(mark) from StudentCourseEntity where studentId=:studentId and courseId=:courseId")
    double getAvgMarkOfStudentByCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select count(*) from StudentCourseEntity where studentId=:studentId and mark > :mark")
    long getTotalMarkFromMark(@Param("mark") Integer mark, @Param("studentId")  Integer studentId);

    @Query(nativeQuery = true, value = "select sc.mark from student_course sc order by sc.mark desc  limit 1")
    Optional<Integer> getTopMark();

    @Query("select avg(mark) from StudentCourseEntity where courseId=:courseId")
    double getAvgMarkByCourse(@Param("courseId") Integer courseId);

    @Query("select count(mark) from StudentCourseEntity where courseId=:courseId")
    long getCountOfMark(@Param("courseId") Integer courseId);

    @Query("select sc.id as id, " +
            "sc.studentId as studentId, " +
            "sc.courseId as courseId, " +
            "sc.mark as mark, " +
            "sc.createdAt as createdDate, " +
            "s.name as studentName, " +
            "s.surname as studentSurname, " +
            "c.name as courseName " +
            "from StudentCourseEntity as sc " +
            "left join StudentEntity as s on s.id = sc.studentId  " +
            "left join CourseEntity as c on c.id = sc.courseId where sc.id=:id")
    Optional<StudentDetailInfoMapper> getStudentCourseDetailInfo(@Param("id") Integer id);

    @Query("select sc.id as id, " +
            "sc.studentId as studentId, " +
            "sc.courseId as courseId, " +
            "sc.mark as mark, " +
            "sc.createdAt as createdDate, " +
            "s.name as studentName, " +
            "s.surname as studentSurname, " +
            "c.name as courseName " +
            "From StudentCourseEntity as sc " +
            "left join  sc.student as s " +
            "left join  sc.course as c " +
            "where sc.id=:id")
    Optional<StudentDetailInfoMapper> getStudentCourseDetailInfoHql(@Param("id") Integer id);
}
