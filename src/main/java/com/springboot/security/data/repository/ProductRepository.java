package com.springboot.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.security.data.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
