package com.edgar.e_commerce_shop_backend.model.dao;

import com.edgar.e_commerce_shop_backend.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDAO extends ListCrudRepository<Address, Long> {
    List<Address> findByUser_Id(Long id);
}
