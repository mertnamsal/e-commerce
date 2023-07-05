package com.ecommerce.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class FallBackController {

    @GetMapping("/fallbackauthentication")
    public ResponseEntity<String> fallbackauthentication() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Authentication service is temporarily disabled. Please try again later.");
    }

    @GetMapping("/fallbackcustomer")
    public ResponseEntity<String> fallbackcustomer() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Customer service is temporarily disabled. Please try again later.");
    }

    @GetMapping("/fallbackproduct")
    public ResponseEntity<String> fallbackproduct() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Product service is temporarily disabled. Please try again later.");
    }

    @GetMapping("/fallbackvendor")
    public ResponseEntity<String> fallbackvendor() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Vendor service is temporarily disabled. Please try again later.");
    }
}