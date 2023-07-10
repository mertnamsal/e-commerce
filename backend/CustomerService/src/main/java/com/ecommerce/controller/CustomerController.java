package com.ecommerce.controller;

import com.ecommerce.dto.response.CustomerResponseDto;
import com.ecommerce.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> findAllConsumer(){
        return ResponseEntity.ok(customerService.findAllCustomer());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }
}
