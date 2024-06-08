package com.edgar.e_commerce_shop_backend.service;

import com.edgar.e_commerce_shop_backend.dto.request.LoginBody;
import com.edgar.e_commerce_shop_backend.dto.request.RegistrationBody;
import com.edgar.e_commerce_shop_backend.exception.UserAlreadyExistsException;
import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private LocalUserDAO localUserDAO;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
                localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> userOptional = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (userOptional.isPresent()) {
            LocalUser user = userOptional.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }
}
