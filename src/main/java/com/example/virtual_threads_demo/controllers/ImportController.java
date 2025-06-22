package com.example.virtual_threads_demo.controllers;

import com.example.virtual_threads_demo.services.CsvImporterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
@RequestMapping("/api/import")
public class ImportController {
    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private CsvImporterService importer;

    @PostMapping
    public String importAll() {
        long totalStartTime = System.currentTimeMillis();

        CompletableFuture<Void> productsFuture = importer.importCsv("products.csv");
        CompletableFuture<Void> employeesFuture = importer.importCsv("employees.csv");
        CompletableFuture<Void> itemsFuture = importer.importCsv("items.csv");

        // Wait for all imports to finish
        CompletableFuture.allOf(productsFuture, employeesFuture, itemsFuture).join();

        long totalElapsedSec = (System.currentTimeMillis() - totalStartTime) / 1000;
        logger.info("All files imported | Total time: {} seconds", totalElapsedSec);

        return "Imports completed in " + totalElapsedSec + " seconds";
    }
}
