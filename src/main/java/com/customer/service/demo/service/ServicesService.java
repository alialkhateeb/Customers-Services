package com.customer.service.demo.service;


import com.customer.service.demo.dto.Services;
import com.customer.service.demo.dto.response.ResponseInterface;


public interface ServicesService {

    ResponseInterface createService(Services service);

    ResponseInterface getServices();

    ResponseInterface updateService(int serviceId, Services service);

    ResponseInterface deleteService(int serviceId);

    ResponseInterface getCustomerServices(int customerId);

    ResponseInterface addServiceToCustomer(int serviceId, int customerId);

}
