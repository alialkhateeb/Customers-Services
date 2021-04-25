package com.customer.service.demo.controller;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.response.ResponseInterface;
import com.customer.service.demo.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseInterface> createCustomer(@RequestBody Customer customer) {
        ResponseInterface response = this.customerService.createCustomer(customer);
        return new ResponseEntity(response, response.getStatus());

    }

    @GetMapping("/get/{customerid}")
    public ResponseEntity<ResponseInterface> getCustomer(@PathVariable("customerid") int customerId) {
        ResponseInterface response = this.customerService.getCustomer(customerId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseInterface> getCustomers(){
        ResponseInterface response = this.customerService.getCustomers();
        return new ResponseEntity<>(response, response.getStatus());

    }

    @PutMapping("/update/{customerid}")
    public ResponseEntity<ResponseInterface> updateCustomer(@PathVariable("customerid") int customerId, @RequestBody Customer customer) {
        ResponseInterface response = this.customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/delete/{customerid}")
    public ResponseEntity<ResponseInterface> deleteCustomer(@PathVariable("customerid") int customerId) {
        ResponseInterface response = this.customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
