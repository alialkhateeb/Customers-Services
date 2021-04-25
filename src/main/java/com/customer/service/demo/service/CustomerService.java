package com.customer.service.demo.service;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.response.ResponseInterface;

public interface CustomerService {

    ResponseInterface createCustomer(Customer customer);

    ResponseInterface updateCustomer(int customerId, Customer customer);

    ResponseInterface getCustomer(int customerId);

    ResponseInterface getCustomers();

    ResponseInterface deleteCustomer(int customerId);
}
