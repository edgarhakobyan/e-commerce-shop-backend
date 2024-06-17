package com.edgar.e_commerce_shop_backend.controller.auth;

import com.edgar.e_commerce_shop_backend.dto.request.RegistrationBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testRegisterUsernameNull() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("AuthenticationControllerTest$testRegister@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("Password123");
        body.setUsername(null);
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterUsernameEmpty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("AuthenticationControllerTest$testRegister@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("Password123");
        body.setUsername("");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterEmailNull() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail(null);
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("Password123");
        body.setUsername("test");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterEmailEmpty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("Password123");
        body.setUsername("test");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterPasswordNull() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("test1@test.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword(null);
        body.setUsername("test");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterPasswordEmpty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("test@test.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("");
        body.setUsername("test");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @Transactional
    public void testRegisterSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody body = new RegistrationBody();
        body.setEmail("test@test.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("testtest11");
        body.setUsername("test");
        mockMvc
                .perform(
                        post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(body)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
