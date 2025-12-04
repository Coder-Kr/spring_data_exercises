package coderuz.controller;

import coderuz.dto.StudentDTO;
import coderuz.enums.Gender;
import coderuz.services.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StudentService studentService;

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenUserDetailsProvided_returnCreateUserDetails() throws Exception {

        //Arrange
        StudentDTO requestDTO = createStudentDTO();

        StudentDTO responseDTO = createStudentDTO();
        responseDTO.setId(1);

        Mockito.when(studentService.create(Mockito.any(StudentDTO.class))).thenReturn(responseDTO);

        RequestBuilder  requestBuilder = MockMvcRequestBuilders.post("/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDTO));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        StudentDTO storedStudent = new ObjectMapper().readValue(responseBodyAsString, StudentDTO.class);

        //Assertion
        Assertions.assertEquals(requestDTO.getName(), storedStudent.getName(), "Student name is incorrect.");
        Assertions.assertEquals(requestDTO.getSurname(), storedStudent.getSurname(), "Surname is incorrect.");
        Assertions.assertEquals(requestDTO.getLevel(), storedStudent.getLevel(), "Level is incorrect.");
        Assertions.assertEquals(requestDTO.getAge(), storedStudent.getAge(), "Age is incorrect.");
        Assertions.assertEquals(requestDTO.getGender(), storedStudent.getGender(), "Gender is incorrect.");
        Assertions.assertNotNull(storedStudent.getId(), "ID is incorrect.");
    }

    @Test
    @DisplayName("Get all users")
    void testGetAllUsers_whenUsersDetailsProvided_returnAllUsers() throws Exception {
        //Arrange
        StudentDTO student1 = createStudentDTO();
        StudentDTO student2 = createStudentDTO();
        StudentDTO student3 = createStudentDTO();

        List<StudentDTO> students = List.of(student1, student2, student3);
        Mockito.when(studentService.findAll()).thenReturn(students);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/all")
                .accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        List<StudentDTO> storedStudents = new ObjectMapper().readValue(responseBodyAsString, new TypeReference<List<StudentDTO>>() {});

        //Assertion
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Status code should be 200");
        Assertions.assertEquals(3, storedStudents.size(), "Number of students should be correct.");
        Assertions.assertEquals(student1.getName(), storedStudents.get(0).getName(), "Student name is incorrect.");
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType(), "Content-Type should be application/json.");
        Mockito.verify(studentService, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Get Student by Id")
    void testGetStudentById_whenStudentIdProvided_returnStudentDetails() throws Exception {
        //Arrange
        StudentDTO student1 = createStudentDTO();
        student1.setId(1);

        Mockito.when(studentService.getById(Mockito.eq(1))).thenReturn(student1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/byId/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        StudentDTO storedStudent = new ObjectMapper().readValue(responseBodyAsString, StudentDTO.class);

        //Assertion
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Status code should be 200");
        Assertions.assertEquals(1, storedStudent.getId(), "ID is incorrect.");
        Assertions.assertEquals(student1.getName(), storedStudent.getName(), "Student name is incorrect.");
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType(), "Content-Type should be application/json.");
        Mockito.verify(studentService, Mockito.times(1)).getById(Mockito.eq(1));

    }

    @Test
    @DisplayName("Student update by id")
    void testUpdateStudentById_whenStudentDetailsProvided_returnUpdateStudentDetails() throws Exception {
        //Arrange
        StudentDTO studentRequest = createStudentDTO();
        studentRequest.setId(1);

        StudentDTO studentResponse = createStudentDTO();
        studentResponse.setName("Abdulloh");
        studentResponse.setId(1);

        Mockito.when(studentService.updateById(Mockito.eq(1), Mockito.any(StudentDTO.class))).thenReturn(studentResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student/update/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studentRequest));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        StudentDTO updatedStudent = new ObjectMapper().readValue(responseBodyAsString, StudentDTO.class);

        //Assertion
        Mockito.verify(studentService, Mockito.times(1)).updateById(Mockito.eq(1), Mockito.any(StudentDTO.class));
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Status code should be 200");
        Assertions.assertNotEquals(studentRequest.getName(), updatedStudent.getName(), "Student name did not changed.");
        Assertions.assertEquals(studentResponse.getName(), updatedStudent.getName(), "Student name is incorrect.");

    }

    @Test
    @DisplayName("Delete by id")
    void testDeleteStudentById_whenStudentIdProvided_returnDeleteMessage() throws Exception {
        //Arrange
        String response = "Deleted";
        Mockito.when(studentService.deleteById(Mockito.eq(1))).thenReturn(response);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student/delete/{id}", 1);

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();


        //Assertion
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Status code should be 200");
        Mockito.verify(studentService, Mockito.times(1)).deleteById(Mockito.eq(1));
    }

    @Test
    @DisplayName("Student find by Name")
    void testFindStudentsByName_whenStudentNameProvided_returnStudentDetailsList() throws Exception {
        //Arrange
        String name = "Jasurbek";
        StudentDTO student1 = createStudentDTO();
        student1.setId(1);
        StudentDTO student2 = createStudentDTO();
        student2.setId(2);
        List<StudentDTO> studentList = List.of(student1, student2);
        Mockito.when(studentService.findAllByName(Mockito.eq(name))).thenReturn(studentList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/findByName")
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON);

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        List<StudentDTO> listOfStudents = new ObjectMapper().readValue(responseBodyAsString, new TypeReference<List<StudentDTO>>() {});

        //Assertion
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus(), "Status code should be 200");
        Assertions.assertEquals(2, listOfStudents.size(), "should return 2 students");
        Assertions.assertEquals(student1.getName(), listOfStudents.get(0).getName(), "Student name is incorrect.");
        Mockito.verify(studentService, Mockito.times(1)).findAllByName(Mockito.eq(name));
    }

    private StudentDTO createStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jasurbek");
        studentDTO.setSurname("Odilov");
        studentDTO.setLevel(2);
        studentDTO.setAge(28);
        studentDTO.setGender(Gender.MALE);
        return studentDTO;
    }

}
