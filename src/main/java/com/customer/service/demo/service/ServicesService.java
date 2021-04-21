package com.customer.service.demo.service;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;

import java.util.List;

public interface ServicesService {

    GenericResponse createService(Services service);

    GenericResponse createService(Services service, int customerId);

    List<Services> getServices();

    List<Services> getServices(int customerId);

    GenericResponse updateService(int serviceId, Services service);

    GenericResponse deleteService(int serviceId);

}
