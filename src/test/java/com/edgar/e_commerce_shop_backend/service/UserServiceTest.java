package com.edgar.e_commerce_shop_backend.service;

import com.edgar.e_commerce_shop_backend.dto.request.LoginBody;
import com.edgar.e_commerce_shop_backend.dto.request.RegistrationBody;
import com.edgar.e_commerce_shop_backend.exception.EmailFailureException;
import com.edgar.e_commerce_shop_backend.exception.UserAlreadyExistsException;
import com.edgar.e_commerce_shop_backend.exception.UserNotVerifiedException;
import com.edgar.e_commerce_shop_backend.model.VerificationToken;
import com.edgar.e_commerce_shop_backend.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Test
    @Transactional
    public void testRegisterUser() {
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("userA@test.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("Password123");

        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Username should already be in use.");

        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("user_a@test.com");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Email should already be in use.");

        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
                "User should register successfully.");
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123");
        Assertions.assertNull(userService.loginUser(body), "The user should not exist.");

        body.setUsername("UserA");
        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");

        body.setPassword("testtest");
        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");

        body.setUsername("UserB");
        body.setPassword("testtest");
        try {
            userService.loginUser(body);
            Assertions.fail("User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should be sent.");
        }

        try {
            userService.loginUser(body);
            Assertions.fail("User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be resent.");
        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist should return false.");

        LoginBody body = new LoginBody();
        body.setUsername("UserB");
        body.setPassword("testtest");
        try {
            userService.loginUser(body);
            Assertions.fail("User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            List<VerificationToken> tokens = verificationTokenDAO.findByUser_IdOrderByIdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token), "Token should be valid.");
            Assertions.assertNotNull(body, "The user should now be verified.");
        }
    }
}
