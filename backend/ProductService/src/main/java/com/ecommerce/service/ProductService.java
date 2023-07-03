package com.ecommerce.service;

import com.ecommerce.dto.request.ProductAddRequestDto;
import com.ecommerce.dto.request.UpdateProductNameRequestDto;
import com.ecommerce.dto.request.UpdateProductStockRequestDto;
import com.ecommerce.dto.response.ProductResponseDto;
import com.ecommerce.exception.EErrorType;
import com.ecommerce.exception.ProductException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.IProductRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends ServiceManagerImpl<Product,Long> {
    private final IProductRepository productRepository;
    public ProductService(IProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    public void addProduct(ProductAddRequestDto dto) {
        validateProductBarcode(dto.getBarcode());
        Product product = Product.builder()
                .productName(dto.getProductName())
                .stock(dto.getStock())
                .barcode(dto.getBarcode())
                .build();
        save(product);
    }

    public void validateProductBarcode(String barcode){
        if(productRepository.existsByBarcode(barcode)){
            throw new ProductException(EErrorType.PRODUCT_ALREADY_EXIST);
        }
    }

    public List<ProductResponseDto> findAllProduct() {
        List<Product> productList = productRepository.findAll();
        return convertProductListToProductResponseDtoList(productList);

    }

    private List<ProductResponseDto> convertProductListToProductResponseDtoList(List<Product> productList){
        return productList.stream()
                .map(product -> new ProductResponseDto(product.getProductName(), product.getStock()))
                .collect(Collectors.toList());
    }

    public ProductResponseDto findProductById(Long id) {
        Product product = findById(id);
        return convertProductToProductResponseDto(product);

    }
    private ProductResponseDto convertProductToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .productName(product.getProductName())
                .stock(product.getStock())
                .build();
    }


    public void updateProductStockById(Long id, UpdateProductStockRequestDto dto) {
        Product product = findById(id);
        product.setStock(product.getStock()+dto.getChange());
        save(product);
    }

    public void updateProductNameById(Long id, UpdateProductNameRequestDto dto) {
        Product product = findById(id);
        product.setProductName(dto.getName());
        save(product);
    }

    public void deleteProductById(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

}
