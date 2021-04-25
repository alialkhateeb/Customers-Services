package com.customer.service.demo.controller;

import com.customer.service.demo.dto.Map;
import com.customer.service.demo.dto.Services;
import com.customer.service.demo.dto.response.ResponseInterface;
import com.customer.service.demo.service.ServicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    private final ServicesService servicesService;

    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseInterface> createServiceForCustomer(@RequestBody Services service) {
        ResponseInterface response = this.servicesService.createService(service);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseInterface> viewAllServices() {
        ResponseInterface response = this.servicesService.getServices();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/update/{serviceid}")
    public ResponseEntity<ResponseInterface> updateService(@PathVariable("serviceid") Integer serviceId, @RequestBody Services service) {
        ResponseInterface response = this.servicesService.updateService(serviceId, service);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/delete/{serviceid}")
    public ResponseEntity<ResponseInterface> deleteService(@PathVariable("serviceid") int serviceId) {
        ResponseInterface response = this.servicesService.deleteService(serviceId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/get/services/{customerid}")
    public ResponseEntity<ResponseInterface> getCustomerServices(@PathVariable("customerid") int customerId) {
        ResponseInterface response = this.servicesService.getCustomerServices(customerId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/map")
    public ResponseEntity<ResponseInterface> mapServiceToCustomer(@RequestBody Map map) {
        ResponseInterface response = this.servicesService.addServiceToCustomer(map.getServiceId(), map.getCustomerId());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
