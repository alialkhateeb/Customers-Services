package com.customer.service.demo.service;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.GenericResponse;

import java.util.List;

public interface CustomerService {

    GenericResponse createCustomer(Customer customer);

    GenericResponse updateCustomer(int customerId, Customer customer);

    GenericResponse getCustomer(int customerId);


    GenericResponse<List<Customer>> getCustomers();

    GenericResponse deleteCustomer(int customerId);
}
