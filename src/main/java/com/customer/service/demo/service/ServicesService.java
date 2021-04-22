package com.customer.service.demo.service;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;

import java.util.List;

public interface ServicesService {

    GenericResponse<Services> createService(Services service);

    GenericResponse<Services> createService(Services service, int customerId);



    GenericResponse<List<Services>> getServices();

    GenericResponse<List<Services>> getServices(int customerId);

    GenericResponse<Services> updateService(int serviceId, Services service);

    GenericResponse<Services> deleteService(int serviceId);

}
