package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Override
    public void createCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(int customerId, Customer customer) {

    }

    @Override
    public Customer getCustomer(int customerId) {
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }

    @Override
    public void deleteCustomer(int customerId) {

    }
}
