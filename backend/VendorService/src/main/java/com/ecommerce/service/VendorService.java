package com.ecommerce.service;

import com.ecommerce.dto.response.VendorResponseDto;
import com.ecommerce.model.Vendor;
import com.ecommerce.repository.IVendorRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService extends ServiceManagerImpl<Vendor,Long> {

    private final IVendorRepository vendorRepository;

    public VendorService(IVendorRepository vendorRepository) {
        super(vendorRepository);
        this.vendorRepository = vendorRepository;
    }

    public List<VendorResponseDto> findAllVendor() {
        List<Vendor> vendorList = vendorRepository.findAll();
        return convertVendorListToVendortResponseDtoList(vendorList);
    }

    private List<VendorResponseDto> convertVendorListToVendortResponseDtoList(List<Vendor> vendorList) {
        return vendorList.stream()
                .map(vendor-> new VendorResponseDto(vendor.getName(),vendor.getSurname(),vendor.getSalesQuantity()))
                .collect(Collectors.toList());
    }

    public VendorResponseDto findVendorById(Long id) {
        Vendor vendor = findById(id);
        return convertVendorToVendorResponseDto(vendor);

    }
    private VendorResponseDto convertVendorToVendorResponseDto(Vendor vendor) {
        return VendorResponseDto.builder()
                .name(vendor.getName())
                .surname(vendor.getSurname())
                .salesQuantity(vendor.getSalesQuantity())
                .build();
    }
}
