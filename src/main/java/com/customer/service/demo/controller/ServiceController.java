package com.customer.service.demo.controller;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;
import com.customer.service.demo.service.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GenericResponse> createServiceForCustomer(@PathVariable("customerid") int customerId, @RequestBody Services service) {
        GenericResponse response = this.servicesService.createService(service, customerId);
        return new ResponseEntity<GenericResponse>(response, response.getStatus());
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createServiceForCustomer(@RequestBody Services service) {
        GenericResponse response = this.servicesService.createService(service);
        return new ResponseEntity<GenericResponse>(response, response.getStatus());
    }

    @GetMapping("/get-services/{customerid}")
    public ResponseEntity<GenericResponse<List<Services>>> viewServicesForCustomer(@PathVariable("customerid") int customerId) {
        GenericResponse<List<Services>> services = this.servicesService.getServices(customerId);
        return new ResponseEntity<GenericResponse<List<Services>>>(services, HttpStatus.OK);
    }

    @GetMapping("/get-services")
    public ResponseEntity<GenericResponse<List<Services>>> viewAllServices() {
        GenericResponse<List<Services>> services = this.servicesService.getServices();
        return new ResponseEntity<GenericResponse<List<Services>>>(services, HttpStatus.OK);
    }

    @PutMapping("/update/{serviceid}")
    public ResponseEntity<GenericResponse> updateService(@PathVariable("serviceid") Integer serviceId, @RequestBody Services service) {
        GenericResponse response = this.servicesService.updateService(serviceId, service);
        return new ResponseEntity<GenericResponse>(response, response.getStatus());
    }

    @DeleteMapping("/delete/{serviceid}")
    public ResponseEntity<GenericResponse> deleteService(@PathVariable("serviceid") int serviceId) {
        GenericResponse response = this.servicesService.deleteService(serviceId);
        return new ResponseEntity<GenericResponse>(response, response.getStatus());
    }

}
