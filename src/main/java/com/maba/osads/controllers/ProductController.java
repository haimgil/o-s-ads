package com.maba.osads.controllers;

import com.maba.osads.persistence.Product;
import com.maba.osads.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v0")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/retrieveProducts")
    public ResponseEntity<List<Product>> retrieve(){
        return ResponseEntity.ok(repository.findAll());
    }

}
