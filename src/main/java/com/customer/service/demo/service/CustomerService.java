package com.customer.service.demo.service;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.GenericResponse;

import java.util.List;

public interface CustomerService {

    GenericResponse<Customer> createCustomer(Customer customer);

    GenericResponse<Customer> updateCustomer(int customerId, Customer customer);

    GenericResponse<Customer> getCustomer(int customerId);

    GenericResponse<List<Customer>> getCustomers();

    GenericResponse<Customer> deleteCustomer(int customerId);
}
