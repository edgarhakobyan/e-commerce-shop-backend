package com.edgar.e_commerce_shop_backend.model.dao;

import com.edgar.e_commerce_shop_backend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
