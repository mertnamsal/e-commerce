package com.ecommerce.controller;

import com.ecommerce.dto.response.VendorResponseDto;
import com.ecommerce.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }
    @GetMapping
    public ResponseEntity<List<VendorResponseDto>> findAllVendor(){
        return ResponseEntity.ok(vendorService.findAllVendor());
    }
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> findVendorById(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.findVendorById(id));
    }
}
