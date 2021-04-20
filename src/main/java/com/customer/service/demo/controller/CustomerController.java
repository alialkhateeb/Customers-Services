package com.customer.service.demo.controller;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.service.CustomerService;
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
    public void createCustomer(@RequestBody Customer customer) {
        this.customerService.createCustomer(customer);

    }

    @GetMapping("/get/{customerid}")
    public Customer getCustomer(@PathVariable("customerid") int customerId) {
        return this.customerService.getCustomer(customerId);

    }

    @GetMapping("/get/all")
    public List<Customer> getCustomers() {
        return this.customerService.getCustomers();
    }

    @PutMapping("/update/{customerid}")
    public void updateCustomer(@PathVariable("customerid") int customerId, @RequestBody Customer customer) {
        this.customerService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/delete/{customerid}")
    public void deleteCustomer(@PathVariable("customerid") int customerId) {
        this.customerService.deleteCustomer(customerId);
    }
}
