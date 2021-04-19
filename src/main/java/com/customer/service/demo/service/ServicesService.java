package com.customer.service.demo.service;

import com.customer.service.demo.dto.Services;

import java.util.List;

public interface ServicesService {

    void createService(Services service);

    List<Services> getServices();

    void updateService(int serviceId);

    void deleteService(int serviceId);

}
