package com.edgar.e_commerce_shop_backend.security;

import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.dao.LocalUserDAO;
import com.edgar.e_commerce_shop_backend.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTRequestFilterTest {
    private static final String AUTHENTICATED_PATH = "/api/auth/me";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserDAO localUserDAO;

    @Test
    public void testUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testBadToken() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "BadTokenThatIsNotValid"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer BadTokenThatIsNotValid"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testUnverifiedUser() throws Exception {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserB").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer " + token))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testSuccessful() throws Exception {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
