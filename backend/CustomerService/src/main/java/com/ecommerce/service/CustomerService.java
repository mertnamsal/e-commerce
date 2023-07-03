package com.ecommerce.service;

import com.ecommerce.dto.response.CustomerResponseDto;
import com.ecommerce.model.Customer;
import com.ecommerce.rabbitmq.model.CreateCustomer;
import com.ecommerce.repository.ICustomerRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ServiceManagerImpl<Customer,Long> {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    public void save(CreateCustomer createCustomer) {
        Customer customer = createCustomerToCustomer(createCustomer);
        save(customer);
    }
    public Customer createCustomerToCustomer(CreateCustomer customer){
        return Customer.builder()
                .authid(customer.getAuthid())
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();
    }

    public List<CustomerResponseDto> findAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        return convertCustomerListToCustomerResponseDtoList(customerList);
    }
    private List<CustomerResponseDto> convertCustomerListToCustomerResponseDtoList(List<Customer> customerList){
        return customerList.stream()
                .map(customer -> new CustomerResponseDto(customer.getName(),customer.getSurname()))
                .collect(Collectors.toList());
    }

    public CustomerResponseDto findCustomerById(Long id) {
        Customer customer = findById(id);
        return convertCustomerToCustomerResponseDto(customer);
    }

    private CustomerResponseDto convertCustomerToCustomerResponseDto(Customer customer) {
        return CustomerResponseDto.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();
    }
}
