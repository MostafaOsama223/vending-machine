package com.flapkap.vending.machine.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/public/hello")
    public String hello() {
        return "Hello, World! This is a public API.";
    }

    @PostMapping("/products")
    public String seller() {
        return "Hello, Seller! This is a protected API.";
    }

    @PostMapping("/deposit")
    public String buyer() {
        return "Hello, Buyer! This is a protected API.";
    }
}
