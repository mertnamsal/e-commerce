package com.ecommerce.service;

import com.ecommerce.model.Customer;
import com.ecommerce.rabbitmq.model.CreateCustomer;
import com.ecommerce.repository.ICustomerRepository;
import com.ecommerce.utility.ServiceManagerImpl;
import org.springframework.stereotype.Service;

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
}
