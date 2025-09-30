package coderuz.services;

import coderuz.dto.CourseDTO;
import coderuz.dto.StudentCourseDTO;
import coderuz.dto.StudentDTO;
import coderuz.entity.CourseEntity;
import coderuz.entity.StudentCourseEntity;
import coderuz.entity.StudentEntity;
import coderuz.repository.StudetnCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {

    @Autowired
    private StudetnCourseRepository studetnCourseRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

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
        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student course not found");
        }
        List<StudentCourseDTO> list = new LinkedList<>();
        for (StudentCourseEntity entity : iterable) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentCourseDTO getById(Integer id) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found test");
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

    public StudentCourseDTO getByIdDetail(Integer id) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        StudentCourseEntity entity = optional.get();

        StudentDTO student = studentService.getById(entity.getStudentId());
        CourseDTO course = courseService.getById(entity.getCourseId());

        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();

        studentCourseDTO.setId(entity.getId());
        studentCourseDTO.setMark(entity.getMark());
        studentCourseDTO.setCreatedAt(entity.getCreatedAt());
        studentCourseDTO.setStudent(student);
        studentCourseDTO.setCourse(course);

        return studentCourseDTO;
    }

    public List<StudentCourseDTO> getByStudentIdAndCreatedAtBetween(Integer studentId, LocalDate date) {
        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.MAX);

        List<StudentCourseEntity> result = studetnCourseRepository.getByDates(studentId, startDate, endDate);
        List<StudentCourseDTO> list = new LinkedList<>();
        for (StudentCourseEntity entity : result) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentCourseDTO> getByStudentIdAndDatesBetween(Integer studentId, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime startDate = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(toDate, LocalTime.MAX);

        List<StudentCourseEntity> result = studetnCourseRepository.findByStudentIdAndCreatedAtBetween(studentId, startDate, endDate);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student course not found!");
        }
        List<StudentCourseDTO> list = new LinkedList<>();
        for (StudentCourseEntity entity : result) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentCourseDTO> findByStudentIdOrderByCreatedAtDesc(Integer studentId) {
        List<StudentCourseEntity> result = studetnCourseRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        List<StudentCourseDTO> list = new LinkedList<>();
        for (StudentCourseEntity entity : result) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentCourseDTO> findByStudentIdAndCourseIdOrderByCreatedAtDesc(Integer studentId, Integer courseId) {
        List<StudentCourseEntity> result =  studetnCourseRepository.findByStudentIdAndCourseIdOrderByCreatedAtDesc(studentId, courseId);
        List<StudentCourseDTO> list = new LinkedList<>();
        for(StudentCourseEntity entity: result){
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentCourseDTO findTopByStudentIdOrderByCreatedAtDesc(Integer studentId) {
        Optional<StudentCourseEntity> result = studetnCourseRepository.getLastMarkOfStudent(studentId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        CourseDTO course = courseService.getById(result.get().getCourseId());
        StudentCourseEntity entity = result.get();
        StudentCourseDTO studentCourseDTO = toDTO(entity);
        studentCourseDTO.setCourse(course);
        return studentCourseDTO;
    }

    public List<StudentCourseDTO> findThreeTopByStudentIdOrderByCreatedAtDesc(Integer studentId) {
//        List<StudentCourseEntity> result = studetnCourseRepository.findTop3ByStudentIdOrderByCreatedAtDesc(studentId);
        List<StudentCourseEntity> result = studetnCourseRepository.getLast3TopMarkOfStudent(studentId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        CourseDTO course = courseService.getById(result.get(0).getCourseId());
        List<StudentCourseDTO> list = new LinkedList<>();
        for (StudentCourseEntity entity : result) {
            StudentCourseDTO studentCourseDTO = toDTO(entity);
            studentCourseDTO.setCourse(course);
            list.add(studentCourseDTO);
        }
        return list;
    }

    public StudentCourseDTO findAvgMarkByStudentId(Integer studentId) {
        List<StudentCourseEntity> result = studetnCourseRepository.findByStudentId(studentId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        StudentCourseEntity student = studetnCourseRepository.findTopByStudentId(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }
        StudentCourseDTO studentCourseDTO = toDTO(student);
        int count = 0;
        int sum = 0;
        for (StudentCourseEntity entity : result) {
            sum += entity.getMark();
            count++;
        }
        Integer avgMark = sum / count;
        studentCourseDTO.setMark(avgMark);
        return studentCourseDTO;

    }

    public StudentCourseDTO findFirstByCourseIdOrderByMarkDesc(Integer courseId) {
        Optional<StudentCourseEntity> optional = studetnCourseRepository.findFirstByCourseIdOrderByMarkDesc(courseId);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student course not found");
        }
        StudentCourseEntity entity = optional.get();
        return toDTO(entity);

    }

    public StudentCourseDTO getFirstMarkOfStudent(Integer studentId) {
        StudentCourseEntity studentCourseEntity = studetnCourseRepository.getFirstMarkOfStudent(studentId);
        if (studentCourseEntity == null) {
            throw new IllegalArgumentException("Student course not found");
        }
        CourseDTO course = courseService.getById(studentCourseEntity.getCourseId());
        StudentDTO studentDTO = studentService.getById(studentCourseEntity.getStudentId());
        StudentCourseDTO studentCourseDTO = toDTO(studentCourseEntity);
        studentCourseDTO.setCourse(course);
        studentCourseDTO.setStudent(studentDTO);
        return studentCourseDTO;
    }

    public StudentCourseDTO getFirstMarkOfCourse(Integer courseId, Integer studentId){
        StudentCourseEntity studentCourseEntity = studetnCourseRepository.getFirstMarkOfCourse(courseId, studentId);
        if (studentCourseEntity == null) {
            throw new IllegalArgumentException("Student course not found");
        }
        CourseDTO course = courseService.getById(studentCourseEntity.getCourseId());
        StudentDTO studentDTO = studentService.getById(studentCourseEntity.getStudentId());
        StudentCourseDTO studentCourseDTO = toDTO(studentCourseEntity);
        studentCourseDTO.setCourse(course);
        studentCourseDTO.setStudent(studentDTO);
        return studentCourseDTO;
    }

    public double getAvgMarkOfStudent(Integer studentId) {
        double studentCourseEntity = studetnCourseRepository.getAvgMarkOfStudent(studentId);
        return studentCourseEntity;

    }

    public double getAvgMarkOfStudentByCourse(Integer studentId, Integer courseId) {
        return studetnCourseRepository.getAvgMarkOfStudentByCourse(studentId, courseId);
    }

    public long getTotalMarkFromMark(Integer mark, Integer studentId) {
        return studetnCourseRepository.getTotalMarkFromMark(mark, studentId);
    }

}
