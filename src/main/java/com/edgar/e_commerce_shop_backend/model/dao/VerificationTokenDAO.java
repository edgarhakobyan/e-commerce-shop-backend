package com.edgar.e_commerce_shop_backend.model.dao;

import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);

    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
