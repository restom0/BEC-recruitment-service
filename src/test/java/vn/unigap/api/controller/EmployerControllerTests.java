package vn.unigap.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.errorcode.ErrorCode;
import vn.unigap.common.exception.ApiException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployerController.class)
public class EmployerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployerService employerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenEmployerObject_whenCreateEmployer_thenReturnStatusCode201() throws Exception {
        // given - precondition or setup
        Employer employer = Employer.builder()
                .name("Duy")
                .email("duy@gmail.com")
                .province(1)
                .description("Duy Company")
                .build();

        given(employerService.createEmployer(any(EmployerDtoIn.class)))
                .willReturn(null); // Không trả về bất kỳ đối tượng nào

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/employers")
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Add the authentication token with "ROLE_ADMIN" to the request
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employer)));

        // then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void givenEmployerId_whenGetEmployerById_thenReturnEmployerObject() throws Exception{
        // given - precondition or setup
        long employerId = 1L;
        Employer employer = Employer.builder()
                .name("Duy")
                .email("duy@gmail.com")
                .province(1)
                .description("Duy Company")
                .build();
        given(employerService.getEmployer(employerId)).willReturn(EmployerDtoOut.from(employer,"Hồ Chí Minh"));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/{id}", employerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))); // Add the authentication token with "ROLE_ADMIN" to the request

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.object.name", is(employer.getName())))
                .andExpect(jsonPath("$.object.email", is(employer.getEmail())))
                .andExpect(jsonPath("$.object.provinceId", is(employer.getProvince())))
                .andExpect(jsonPath("$.object.provinceName", is("Hồ Chí Minh")))
                .andExpect(jsonPath("$.object.description", is(employer.getDescription())));
    }

    @Test
    public void givenInvalidEmployerId_whenGetEmployerById_thenReturnNotFound() throws Exception{
        // given - precondition or setup
        long employerId = 1L;
        Employer employer = Employer.builder()
                .name("Duy")
                .email("duy@gmail.com")
                .province(1)
                .description("Duy Company")
                .build();
        given(employerService.getEmployer(employerId))
                .willThrow(new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found"));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/employers/{id}", employerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))); // Add the authentication token with "ROLE_ADMIN" to the request

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenValidEmployerId_whenUpdateEmployer_thenReturnUpdatedEmployer() throws Exception {
        // given - precondition or setup
        long employerId = 1L;
        EmployerDtoIn updatedEmployerDto = new EmployerDtoIn();
        updatedEmployerDto.setName("Updated Duy");
        updatedEmployerDto.setEmail("updated_duy@gmail.com");
        updatedEmployerDto.setProvinceId(2);
        updatedEmployerDto.setDescription("Updated Duy Company");

        Employer updatedEmployer = Employer.builder()
                .name(updatedEmployerDto.getName())
                .email(updatedEmployerDto.getEmail())
                .province(updatedEmployerDto.getProvinceId())
                .description(updatedEmployerDto.getDescription())
                .build();

        given(employerService.updateEmployer(employerId, updatedEmployerDto))
                .willReturn(EmployerDtoOut.from(updatedEmployer, "Hồ Chí Minh"));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/employers/{id}", employerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Add the authentication token with "ROLE_ADMIN" to the request
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployerDto)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void givenInvalidEmployerId_whenUpdateEmployer_thenReturnNotFound() throws Exception {
        // given - precondition or setup
        long employerId = 1L;
        EmployerDtoIn updatedEmployerDto = new EmployerDtoIn();
        updatedEmployerDto.setName("Updated Duy");
        updatedEmployerDto.setEmail("updated_duy@gmail.com");
        updatedEmployerDto.setProvinceId(2);
        updatedEmployerDto.setDescription("Updated Duy Company");

        given(employerService.updateEmployer(employerId, updatedEmployerDto))
                .willThrow(new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found"));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/employers/{id}", employerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))) // Add the authentication token with "ROLE_ADMIN" to the request
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployerDto)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void givenValidEmployerId_whenDeleteEmployer_thenNoContent() throws Exception {
        // given - precondition or setup
        long employerId = 1L;

        // Mock service để không có lỗi nào được ném ra
        doNothing().when(employerService).deleteEmployer(employerId);

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/employers/{id}", employerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))); // Add the authentication token with "ROLE_ADMIN" to the request

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void givenInvalidEmployerId_whenDeleteEmployer_thenNotFound() throws Exception {
        // given - precondition or setup
        long invalidEmployerId = 999L;

        // Mock service để ném ra một ApiException khi nhận một employer id không hợp lệ
        doThrow(new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "Employer not found"))
                .when(employerService).deleteEmployer(invalidEmployerId);

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/employers/{id}", invalidEmployerId)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))); // Add the authentication token with "ROLE_ADMIN" to the request

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
