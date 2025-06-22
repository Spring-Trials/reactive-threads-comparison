package com.example.virtual_threads_demo.controllers;

import com.example.virtual_threads_demo.services.CsvImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CsvImporterService csvImporterService;

    @PostMapping("/import")
    public String importCsv() {
        csvImporterService.importCsv("products.csv");
        return "CSV import started";
    }
}
