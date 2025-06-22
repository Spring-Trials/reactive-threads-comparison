package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
