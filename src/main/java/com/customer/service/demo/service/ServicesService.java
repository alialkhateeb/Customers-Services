package com.customer.service.demo.service;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;

import java.util.List;

public interface ServicesService {

    GenericResponse<Services> createService(Services service);

    GenericResponse<List<Services>> getServices();

    GenericResponse<Services> updateService(int serviceId, Services service);

    GenericResponse<Services> deleteService(int serviceId);

    GenericResponse<List<Services>> getCustomerServices(int customerId);

    GenericResponse<Services> addServiceToCustomer(int serviceId, int customerId);

}
