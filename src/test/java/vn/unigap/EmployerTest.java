package vn.unigap;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.qos.logback.core.net.ObjectWriter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.unigap.controllers.EmployerController;
import vn.unigap.dto.out.PagingResponse;
import vn.unigap.dto.out.ResponseHandler;
import vn.unigap.entities.Employer;
import vn.unigap.exceptions.MissingServletRequestParameterException;
import vn.unigap.services.EmployerService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private EmployerService employerService;
//    @Test
//    void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/employers")).andDo(print()).andExpect(status().isBadRequest());
//    }
//    @Test
//    void testGetAllEmployersMissingServletRequestParameterException() throws Exception {
//        this.mockMvc.perform(get("/employers"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("page is missing"));
//        this.mockMvc.perform(get("/employers")
//                        .param("pageSize","12"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("page is missing"));
//        this.mockMvc.perform(get("/employers")
//                        .param("page","0"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("pageSize is missing"));
//    }
//    @Test
//    void testGetAllEmployersWithInvalidAttributes() throws Exception {
//        this.mockMvc.perform(get("/employers")
//                .param("page","0")
//                .param("pageSize","12"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid page"));
//        this.mockMvc.perform(get("/employers")
//                        .param("page","-1")
//                        .param("pageSize","12"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid page"));
//        this.mockMvc.perform(get("/employers")
//                        .param("page","1")
//                        .param("pageSize","0"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid page's size"));
//        this.mockMvc.perform(get("/employers")
//                        .param("page","1")
//                        .param("pageSize","-12"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid page's size"));
//    }
//    @Test
//    void testGetAllEmployersSuccess() throws Exception {
//        //System.out.println(jsonPath("$.object"));
//        Iterable<Employer> employer = this.employerService.getAllEmployers(1,12);
//        this.mockMvc.perform(get("/employers")
//                .param("page","1")
//                .param("pageSize","12"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").isEmpty())
//                .andExpect(jsonPath("$.object.page").value(1))
//                .andExpect(jsonPath("$.object.pageSize").value(12))
//                .andExpect(jsonPath("$.object.totalElements").value(this.employerService.countEmployers()))
//                .andExpect(jsonPath("$.object.totalPages").value(Math.ceil((double) this.employerService.countEmployers() /(double) 12)));
//    }
//    @Test
//    void testGetEmployerByIdMissingServletRequestParameterException() throws Exception {
//        this.mockMvc.perform(get("/employers/"))
//                .andDo(print()).andExpect(status().isNotFound());
//        this.mockMvc.perform(get("/employers/1"))
//                .andDo(print()).andExpect(status().isNotFound());
//        this.mockMvc.perform(get("/employers/2"))
//                .andDo(print()).andExpect(status().isOk());
//    }
//    @Test
//    void testGetEmployerByIdWithInvalidAttributes() throws Exception {
//        this.mockMvc.perform(get("/employers/-1"))
//                .andDo(print()).andExpect(status().isBadRequest());
//        this.mockMvc.perform(get("/employers/"))
//                .andDo(print()).andExpect(status().isNotFound());
//    }
//    @Test
//    void testGetEmployerByIdSuccess() throws Exception {
//        this.mockMvc.perform(get("/employers/1"))
//                .andDo(print()).andExpect(status().isNotFound());
//        this.mockMvc.perform(get("/employers/2"))
//                .andDo(print()).andExpect(status().isOk());
//    }
//    @Test
//    void testAddEmployerMissingServletRequestParameterException() throws Exception {
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"provinceId\":\"12\",\"email\":\"hello\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("name is missing"));
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"hello\",\"email\":\"hello\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("province's id is missing"));
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"hello\",\"provinceId\":\"12\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("email is missing"));
//    }
//    @Test
//    void testAddEmployerWithInvalidAttributes() throws Exception {
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"provinceId\":\"12\",\"email\":\"hello@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid name"));
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"hellogmail.com\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid email"));
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid email"));
//    }
//    @Test
//    void testAddEmployerSuccess() throws Exception {
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"hello123@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isCreated());
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"hello123@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isConflict());
//        this.mockMvc.perform(post("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"hello6@gmail.com\",\"description\":\"hello6@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isConflict());
//        Employer employer= this.employerService.findEmployerByEmail("hello123@gmail.com");
//        long id =employer.getId();
//        this.mockMvc.perform(delete("/employers/"+ id)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()).andExpect(status().isOk());
//    }
//    @Test
//    void testUpdateEmployerMissingServletRequestParameterException() throws Exception {
//        this.mockMvc.perform(put("/employers")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()).andExpect(status().isMethodNotAllowed());
//        this.mockMvc.perform(put("/employers/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"provinceId\":\"12\",\"description\":\"hello6@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("name is missing"));
//        this.mockMvc.perform(put("/employers/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"abc\",\"description\":\"hello6@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("province's id is missing"));
//    }
//    @Test
//    void testUpdateEmployerInvalidAttributes() throws Exception {
//        this.mockMvc.perform(put("/employers/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"provinceId\":\"12\"}"))
//                .andDo(print()).andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Invalid name"));
//    }
//    @Test
//    void testUpdateEmployerSuccess() throws Exception {
//        this.mockMvc.perform(put("/employers/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"provinceId\":\"13\"}"))
//                .andDo(print()).andExpect(status().isOk());
//    }
//    @Test
//    void testDeleteEmployerMissingServletRequestParameterException() throws Exception {
//        this.mockMvc.perform(delete("/employers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"a\",\"provinceId\":\"12\",\"email\":\"hello@gmail.com\"}"))
//                .andDo(print()).andExpect(status().isMethodNotAllowed());
//
//    }
//    @Test
//    void testDeleteEmployerInvalidAttributes() throws Exception {
//        this.mockMvc.perform(delete("/employers/-1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print()).andExpect(status().isNotFound());
//
//    }
}
