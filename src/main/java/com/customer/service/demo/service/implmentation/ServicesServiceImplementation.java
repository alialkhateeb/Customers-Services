package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.Services;
import com.customer.service.demo.service.ServicesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesServiceImplementation implements ServicesService {


    @Override
    public void createService(Services service) {

    }

    @Override
    public List<Services> getServices() {
        return null;
    }

    @Override
    public void updateService(int serviceId) {

    }

    @Override
    public void deleteService(int serviceId) {

    }
}
