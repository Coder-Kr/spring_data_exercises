package coderuz.service;

import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import coderuz.repository.StudentRepository;
import coderuz.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class StudentServiceUnitTest {
    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Test
    @DisplayName("Create a student")
    void testCreateStudent_whenProvidesStudentDetails_returnStudentDetails(){
        //Arrange
        ObjectMapper mapper = new ObjectMapper();
        StudentDTO request = createStudent();
        StudentEntity response = mapper.convertValue(request, StudentEntity.class);
        response.setCreatedAt(LocalDateTime.of(2025,12,04,10,0));
        response.setId(1);

        Mockito.when(studentRepository.save(Mockito.any(StudentEntity.class))).thenReturn(response);

        //Act
        StudentDTO storedStudent = studentService.create(request);

        //Assertion
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(StudentEntity.class));
        Assertions.assertNotNull(storedStudent, "The student was not created");
        Assertions.assertEquals(response.getId(), storedStudent.getId(), "storedStudentId is not the same");
        Assertions.assertEquals(request.getName(), storedStudent.getName(), "The student was not created");
        Assertions.assertEquals(request.getSurname(), storedStudent.getSurname(), "Surname changed unexpectedly");
        Assertions.assertEquals(response.getCreatedAt().toLocalDate(), storedStudent.getCreatedAt(), "CreatedAt is not save correctly");

    }

    @Test
    @DisplayName("Test find all method")
    void testFindAllStudents_returnAllStudents(){
        //Arrange
        ObjectMapper mapper = new ObjectMapper();
        StudentDTO student1 = createStudent();
        StudentDTO student2 = createStudent();
        student2.setName("Ali");
        StudentDTO student3 = createStudent();
        student3.setName("Vali");

        StudentEntity entityStudent1 = mapper.convertValue(student1, StudentEntity.class);
        entityStudent1.setCreatedAt(LocalDateTime.of(2025,12,04,10,0));
        StudentEntity entityStudent2 = mapper.convertValue(student2, StudentEntity.class);
        entityStudent2.setCreatedAt(LocalDateTime.of(2025,12,04,10,0));
        StudentEntity entityStudent3 = mapper.convertValue(student3, StudentEntity.class);
        entityStudent3.setCreatedAt(LocalDateTime.of(2025,12,04,10,0));

        List<StudentEntity> entityStudents = List.of(entityStudent1, entityStudent2, entityStudent3);
        List<StudentDTO> students = List.of(student1, student2, student3);

        Mockito.when(studentRepository.findAll()).thenReturn(entityStudents);

        //Act
        List<StudentDTO> studenstList = studentService.findAll();

        //Assertion
        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(studenstList, "The students was not created");
        Assertions.assertEquals(entityStudents.size(), studenstList.size(), "The students size is not the same");

    }





    private StudentDTO createStudent(){
        StudentDTO dto = new StudentDTO();
        dto.setName("Jasurbek");
        dto.setSurname("Odilov");
        dto.setAge(25);
        dto.setLevel(3);
        dto.setGender(Gender.MALE);
        return dto;
    }

}
