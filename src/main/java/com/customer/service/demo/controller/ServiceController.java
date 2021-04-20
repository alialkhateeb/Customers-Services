package com.customer.service.demo.controller;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.Services;
import com.customer.service.demo.service.ServicesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    private final ServicesService servicesService;

    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @PostMapping("/create/{customerid}")
    public void createServiceForCustomer(@PathVariable("customerid")  int customerId, @RequestBody Services service) {
        System.out.println("customer id = " + customerId);
        System.out.println("services  = " + service);
    }

    @GetMapping("/get-services/{customerid}")
    public List<Services> viewServicesForCustomer(@PathVariable("customerid") int customerId){
        System.out.println("customer id = " + customerId);
        return null;
    }

    @GetMapping("/get-services")
    public List<Services> viewAllServices(){
        System.out.println("get all services...");
        return null;
    }

    @PutMapping("/update/{serviceid}")
    public void updateService(@PathVariable("serviceid") int serviceId, @RequestBody Services services){
        System.out.println("service Id = " + serviceId);
        System.out.println(services);
    }

    @DeleteMapping("/delete/{serviceid}")
    public void deleteService(@PathVariable("serviceid") int serviceId){
        System.out.println("service Id = " + serviceId);

    }

}
