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
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("Test find all students")
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

    @Test
    @DisplayName("Get by Id - should return student details when Id is provided")
    void testGetStudentById_whenProvideStudentId_returnStudentDetails(){

        //Arrange
        ObjectMapper mapper = new ObjectMapper();
        StudentDTO student = createStudent();

        StudentEntity entityStudent = mapper.convertValue(student, StudentEntity.class);
        entityStudent.setId(1);
        entityStudent.setCreatedAt(LocalDateTime.of(2025,12,04,10,0));

        Mockito.when(studentRepository.findById(entityStudent.getId())).thenReturn(Optional.of(entityStudent));

        //Act
        StudentDTO getStudentResult = studentService.getById(entityStudent.getId());

        //Assert
        Mockito.verify(studentRepository, Mockito.times(1)).findById(entityStudent.getId());
        Assertions.assertNotNull(getStudentResult, "The student was not retrieved");
        Assertions.assertEquals(entityStudent.getId(), getStudentResult.getId(), "The student id was not retrieved correctly");
        Assertions.assertEquals(entityStudent.getName(), getStudentResult.getName(), "The student name was not retrieved correctly");
        Assertions.assertEquals(entityStudent.getSurname(), getStudentResult.getSurname(),  "The student surname was not retrieved correctly");

    }

    @Test
    @DisplayName("Get by id - should throw IllegralArgumetnException when student not found")
    void testGetByStudentId_whenStudentNotFound_shouldThrowIllegalArgumentException(){
       //Arrange
        Integer studentId = 1;
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        //Assert & Act
        Assertions.assertThrows(IllegalArgumentException.class, ()-> studentService.getById(studentId), "Do not throw illegal argument exception");
        Mockito.verify(studentRepository, Mockito.times(1)).findById(studentId);

    }

    @Test
    @DisplayName("Update by Id - should return updated student details")
    void testUpdateStudentById_whenProvideNewStudentDetails_returnUpdatedStudentDetails() {
        // Arrange
        Integer studentId = 1;

        // Yangi kelgan DTO (clientdan keladigan data)
        StudentDTO newData = createStudent();

        // 1) update query 1 ta row update qildi deb mock qilamiz
        Mockito.when(studentRepository.updateStudentById(
                newData.getName(),
                newData.getSurname(),
                newData.getLevel(),
                newData.getAge(),
                newData.getGender(),
                studentId
        )).thenReturn(1);

        // 2) update'dan keyin DB'dan qayta o'qilgan student
        StudentEntity updatedEntity = new StudentEntity();
        updatedEntity.setId(studentId);
        updatedEntity.setName(newData.getName());
        updatedEntity.setSurname(newData.getSurname());
        updatedEntity.setAge(newData.getAge());
        updatedEntity.setLevel(newData.getLevel());
        updatedEntity.setGender(newData.getGender());
        updatedEntity.setCreatedAt(LocalDateTime.of(2025, 12, 4, 10, 0));

        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(updatedEntity));

        // Act
        StudentDTO result = studentService.updateById(studentId, newData);

        // Assert
        Mockito.verify(studentRepository, Mockito.times(1))
                .updateStudentById(
                        newData.getName(),
                        newData.getSurname(),
                        newData.getLevel(),
                        newData.getAge(),
                        newData.getGender(),
                        studentId
                );

        Mockito.verify(studentRepository, Mockito.times(1))
                .findById(studentId);

        Assertions.assertEquals(studentId, result.getId());
        Assertions.assertEquals(newData.getName(), result.getName());
        Assertions.assertEquals(newData.getSurname(), result.getSurname());
        Assertions.assertEquals(newData.getAge(), result.getAge());
        Assertions.assertEquals(newData.getLevel(), result.getLevel());
        Assertions.assertEquals(newData.getGender(), result.getGender());
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
