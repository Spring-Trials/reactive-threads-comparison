package com.example.virtual_threads_demo.services;

import com.example.virtual_threads_demo.models.Employee;
import com.example.virtual_threads_demo.models.Item;
import com.example.virtual_threads_demo.models.Product;
import com.example.virtual_threads_demo.repository.EmployeeRepository;
import com.example.virtual_threads_demo.repository.ItemRepository;
import com.example.virtual_threads_demo.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class CsvImporterService {
    private static final Logger logger = LoggerFactory.getLogger(CsvImporterService.class);

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Async
    public CompletableFuture<Void> importCsv(String filePath) {
        long startTime = System.currentTimeMillis();
        logger.info("Starting import for: {}", filePath);

        try {
            if (filePath.contains("products.csv")) {
                importProducts(filePath);
            } else if (filePath.contains("employees.csv")) {
                importEmployees(filePath);
            } else if (filePath.contains("items.csv")) {
                importItems(filePath);
            }
        } catch (Exception e) {
            logger.error("Failed to import {}", filePath, e);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTimeSec = (endTime - startTime) / 1000;
        logger.info("Finished import for: {} | Elapsed time: {} seconds", filePath, elapsedTimeSec);

        return CompletableFuture.completedFuture(null);
    }

    private void importProducts(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build();

            List<Product> batch = new ArrayList<>(1000);
            csvToBean.stream().forEach(product -> {
                batch.add(product);
                if (batch.size() >= 1000) {
                    productRepo.saveAll(batch);
                    batch.clear();
                }
            });
            if (!batch.isEmpty()) productRepo.saveAll(batch);
        }
    }

    private void importEmployees(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .build();

            List<Employee> batch = new ArrayList<>(1000);
            csvToBean.stream().forEach(employee -> {
                batch.add(employee);
                if (batch.size() >= 1000) {
                    employeeRepo.saveAll(batch);
                    batch.clear();
                }
            });
            if (!batch.isEmpty()) employeeRepo.saveAll(batch);
        }
    }

    private void importItems(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CsvToBean<Item> csvToBean = new CsvToBeanBuilder<Item>(reader)
                    .withType(Item.class)
                    .build();

            List<Item> batch = new ArrayList<>(1000);
            csvToBean.stream().forEach(item -> {
                batch.add(item);
                if (batch.size() >= 1000) {
                    itemRepo.saveAll(batch);
                    batch.clear();
                }
            });
            if (!batch.isEmpty()) itemRepo.saveAll(batch);
        }
    }
}
