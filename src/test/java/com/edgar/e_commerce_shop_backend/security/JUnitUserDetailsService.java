package com.edgar.e_commerce_shop_backend.security;

import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.dao.LocalUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JUnitUserDetailsService implements UserDetailsService {
    @Autowired
    private LocalUserDAO localUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);
        return opUser.orElse(null);
    }
}
