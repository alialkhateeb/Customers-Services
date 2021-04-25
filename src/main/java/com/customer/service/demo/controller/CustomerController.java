package com.customer.service.demo.controller;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createCustomer(@RequestBody Customer customer) {
        GenericResponse<Customer> response = this.customerService.createCustomer(customer);
        return new ResponseEntity<GenericResponse>(response, response.getStatus());

    }

    @GetMapping("/get/{customerid}")
    public ResponseEntity<GenericResponse> getCustomer(@PathVariable("customerid") int customerId) {
        GenericResponse<Customer> response = this.customerService.getCustomer(customerId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/get/all")
    public ResponseEntity<GenericResponse<List<Customer>>> getCustomers(){
        GenericResponse<List<Customer>> response = this.customerService.getCustomers();
        return new ResponseEntity<>(response, response.getStatus());

    }

    @PutMapping("/update/{customerid}")
    public ResponseEntity<GenericResponse> updateCustomer(@PathVariable("customerid") int customerId, @RequestBody Customer customer) {
        GenericResponse<Customer> response = this.customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/delete/{customerid}")
    public ResponseEntity<GenericResponse> deleteCustomer(@PathVariable("customerid") int customerId) {
        GenericResponse<Customer> response = this.customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
