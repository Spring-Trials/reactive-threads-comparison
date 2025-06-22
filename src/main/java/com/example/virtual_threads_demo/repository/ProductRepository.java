package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
