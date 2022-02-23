package com.estore.ecomerce.repository;

import com.estore.ecomerce.domain.Cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long>{
    
}
