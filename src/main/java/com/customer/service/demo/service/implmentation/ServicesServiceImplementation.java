package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;
import com.customer.service.demo.entity.ServiceEntity;
import com.customer.service.demo.repository.ServiceRepository;
import com.customer.service.demo.service.ServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesServiceImplementation implements ServicesService {

    private final ServiceRepository serviceRepository;
    private static final Logger LOGS = LoggerFactory.getLogger(ServicesServiceImplementation.class);

    public ServicesServiceImplementation(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public GenericResponse createService(Services service) {
        ServiceEntity serviceEntity = generateServiceEntity(service);
        try {
            this.serviceRepository.save(serviceEntity);
            return new GenericResponse("service has been created and stored in db", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGS.error("error creating customer");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericResponse createService(Services service, int customerId) {
        ServiceEntity serviceEntity = generateServiceEntity(service);
        serviceEntity.setCustomerId(customerId);
        try {
            this.serviceRepository.save(serviceEntity);
            return new GenericResponse("service has been created and stored in db", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGS.error("error creating customer");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Services> getServices() {
        List<Services> services = this.serviceRepository.findAll().stream().map(value -> {
            Services service = new Services();
            service.setServiceId(value.getServiceId());
            service.setServiceName(value.getServiceName());
            service.setServicesDescription(value.getServiceDescription());
            return service;
        }).collect(Collectors.toList());
        return services;
    }

    @Override
    public List<Services> getServices(int customerId) {
        List<Services> servicesList = this.serviceRepository.findAllServicesByCustomerId(customerId).stream().map(value -> {
            Services service = new Services();
            service.setServiceId(value.getServiceId());
            service.setServiceName(value.getServiceName());
            service.setServicesDescription(value.getServiceDescription());
            return service;
        }).collect(Collectors.toList());
        return servicesList;
    }

    @Override
    public GenericResponse updateService(int serviceId, Services service) {
        Optional<ServiceEntity> serviceEntity = this.serviceRepository.findById(serviceId);
        if (serviceEntity.isPresent()){
            ServiceEntity value = serviceEntity.get();
            value.setServiceName(service.getServiceName());
            value.setServiceDescription(service.getServicesDescription());
            try {
                this.serviceRepository.save(value);
                return new GenericResponse("service has been updated and stored in db", HttpStatus.CREATED);
            }catch (Exception e){
                LOGS.error("error updating service");
                LOGS.error(e.getMessage());
                return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new GenericResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GenericResponse deleteService(int serviceId) {
        try {
            this.serviceRepository.deleteById(serviceId);
            return new GenericResponse("service has been deleted from db", HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new GenericResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            LOGS.error("error deleting service");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ServiceEntity generateServiceEntity(Services service) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName(service.getServiceName());
        serviceEntity.setServiceDescription(service.getServicesDescription());
        serviceEntity.setServiceCreated(new Timestamp(System.currentTimeMillis()));
        return serviceEntity;
    }
}
