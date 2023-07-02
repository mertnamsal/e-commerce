package com.ecommerce.controller;

import com.ecommerce.dto.request.ProductAddRequestDto;
import com.ecommerce.dto.request.UpdateProductNameRequestDto;
import com.ecommerce.dto.request.UpdateProductStockRequestDto;
import com.ecommerce.dto.response.ProductResponseDto;
import com.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductAddRequestDto dto){
        productService.addProduct(dto);
        return ResponseEntity.ok(true);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAllProduct(){
        return ResponseEntity.ok(productService.findAllProduct());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductById(id));
    }
    @PutMapping("/{id}/update-stock")
    public ResponseEntity<Boolean> updateProductStockById(@PathVariable Long id ,@RequestBody @Valid UpdateProductStockRequestDto dto){
        productService.updateProductStockById(id,dto);
        return ResponseEntity.ok(true);
    }
    @PutMapping("/{id}/update-productname")
    public ResponseEntity<Boolean> updateProductNameById(@PathVariable Long id ,@RequestBody @Valid UpdateProductNameRequestDto dto){
        productService.updateProductNameById(id,dto);
        return ResponseEntity.ok(true);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(true);
    }
}
