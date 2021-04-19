package com.customer.service.demo.service;

import com.customer.service.demo.dto.Customer;

import java.util.List;

public interface CustomerService {

    void createCustomer(Customer customer);

    void updateCustomer(int customerId, Customer customer);

    Customer getCustomer(int customerId);

    List<Customer> getCustomers();

    void deleteCustomer(int customerId);
}
