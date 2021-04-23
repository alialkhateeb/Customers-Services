package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.dto.Services;
import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.entity.ServiceEntity;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.repository.ServiceRepository;
import com.customer.service.demo.service.ServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesServiceImplementation implements ServicesService {

    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;
    private static final Logger LOGS = LoggerFactory.getLogger(ServicesServiceImplementation.class);

    public ServicesServiceImplementation(ServiceRepository serviceRepository, CustomerRepository customerRepository) {
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public GenericResponse<Services> createService(Services service) {
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
    public GenericResponse<List<Services>> getServices() {
        List<Services> services = this.serviceRepository.findAll() .stream()
                .map(value -> new Services(value.getServiceId(), value.getServiceName(), value.getServiceDescription()))
                .collect(Collectors.toList());
        return new GenericResponse<List<Services>>("", HttpStatus.OK, services);
    }

    @Override
    public GenericResponse<Services> updateService(int serviceId, Services service) {
        Optional<ServiceEntity> serviceEntity = this.serviceRepository.findById(serviceId);
        if (serviceEntity.isPresent()) {
            ServiceEntity value = serviceEntity.get();
            value.setServiceName(service.getServiceName());
            value.setServiceDescription(service.getServicesDescription());
            try {
                this.serviceRepository.save(value);
                return new GenericResponse("service has been updated and stored in db", HttpStatus.CREATED);
            } catch (Exception e) {
                LOGS.error("error updating service");
                LOGS.error(e.getMessage());
                return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new GenericResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GenericResponse<Services> deleteService(int serviceId) {
        try {
            this.serviceRepository.deleteById(serviceId);
            return new GenericResponse("service has been deleted from db", HttpStatus.ACCEPTED);
        } catch (EmptyResultDataAccessException e) {
            return new GenericResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGS.error("error deleting service");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericResponse<List<Services>> getCustomerServices(int customerId) {
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            List<Services> services = customer.getServices().stream()
                    .map(value -> new Services(value.getServiceId(), value.getServiceName(), value.getServiceDescription()))
                    .collect(Collectors.toList());
            return new GenericResponse<List<Services>>("", HttpStatus.OK, services);
        }

        return new GenericResponse<List<Services>>("please check your customer/service id", HttpStatus.BAD_REQUEST);
    }

    @Override
    public GenericResponse<Services> addServiceToCustomer(int serviceId, int customerId) {
        Optional<ServiceEntity> serviceOptional = this.serviceRepository.findById(serviceId);
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent() && serviceOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            customer.getServices().add(serviceOptional.get());
            this.customerRepository.save(customer);
            return new GenericResponse<Services>("service has been added to customer", HttpStatus.ACCEPTED);
        }
        return new GenericResponse<Services>("please check your customer/service id", HttpStatus.BAD_REQUEST);
    }

    private ServiceEntity generateServiceEntity(Services service) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName(service.getServiceName());
        serviceEntity.setServiceDescription(service.getServicesDescription());
        serviceEntity.setServiceCreated(new Timestamp(System.currentTimeMillis()));
        return serviceEntity;
    }

}
