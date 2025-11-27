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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Jasurbek");
        studentDTO.setSurname("Odilov");
        studentDTO.setLevel(2);
        studentDTO.setAge(28);
        studentDTO.setGender(Gender.MALE);

        StudentDTO studentDTOResponse = new StudentDTO();
        studentDTOResponse.setName("Jasurbek");
        studentDTOResponse.setSurname("Odilov");
        studentDTOResponse.setLevel(2);
        studentDTOResponse.setAge(28);
        studentDTOResponse.setGender(Gender.MALE);
        studentDTOResponse.setId(1);

        Mockito.when(studentService.create(Mockito.any(StudentDTO.class))).thenReturn(studentDTOResponse);

        RequestBuilder  requestBuilder = MockMvcRequestBuilders.post("/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(studentDTO));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        StudentDTO responseDTO = new ObjectMapper().readValue(responseBodyAsString, StudentDTO.class);

        //Assertion
        Assertions.assertEquals(studentDTO.getName(), responseDTO.getName(), "Student name is incorrect.");
        Assertions.assertEquals(studentDTO.getSurname(), responseDTO.getSurname(), "Surname is incorrect.");
        Assertions.assertEquals(studentDTO.getLevel(), responseDTO.getLevel(), "Level is incorrect.");
        Assertions.assertEquals(studentDTO.getAge(), responseDTO.getAge(), "Age is incorrect.");
        Assertions.assertEquals(studentDTO.getGender(), responseDTO.getGender(), "Gender is incorrect.");
        Assertions.assertNotNull(responseDTO.getId(), "ID is incorrect.");
    }


}
