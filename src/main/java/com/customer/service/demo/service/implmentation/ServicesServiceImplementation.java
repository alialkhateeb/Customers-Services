package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.Services;
import com.customer.service.demo.dto.response.BodyResponse;
import com.customer.service.demo.dto.response.EmptyBodyResponse;
import com.customer.service.demo.dto.response.ResponseInterface;
import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.entity.ServiceEntity;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.repository.ServiceRepository;
import com.customer.service.demo.service.ServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "services", allEntries = true)
    @Override
    public ResponseInterface createService(Services service) {
        ServiceEntity serviceEntity = generateServiceEntity(service);
        try {
            this.serviceRepository.save(serviceEntity);
            return new EmptyBodyResponse("service has been created and stored in db", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGS.error("error creating customer");
            LOGS.error(e.getMessage());
            return new EmptyBodyResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "services")
    @Override
    public ResponseInterface getServices() {
        List<Services> services = this.serviceRepository.findAll() .stream()
                .map(value -> new Services(value.getServiceId(), value.getServiceName(), value.getServiceDescription()))
                .collect(Collectors.toList());
        return new BodyResponse<>("", HttpStatus.OK, services);
    }

    @CacheEvict(value = {"services", "services-customer"}, key = "#serviceId", allEntries = true)
    @Override
    public ResponseInterface updateService(int serviceId, Services service) {
        Optional<ServiceEntity> serviceEntity = this.serviceRepository.findById(serviceId);
        if (serviceEntity.isPresent()) {
            ServiceEntity value = serviceEntity.get();
            value.setServiceName(service.getServiceName());
            value.setServiceDescription(service.getServicesDescription());
            try {
                this.serviceRepository.save(value);
                return new EmptyBodyResponse("service has been updated and stored in db", HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                LOGS.error("error updating service");
                LOGS.error(e.getMessage());
                return new EmptyBodyResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new EmptyBodyResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @CacheEvict(value = {"services", "services-customer"}, key = "#serviceId", allEntries = true)
    @Override
    public ResponseInterface deleteService(int serviceId) {
        try {
            this.serviceRepository.deleteById(serviceId);
            return new EmptyBodyResponse("service has been deleted from db", HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new EmptyBodyResponse("the provided service id does not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGS.error("error deleting service");
            LOGS.error(e.getMessage());
            return new EmptyBodyResponse("Issue occurred while storing service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "services-customer", key = "#customerId")
    @Override
    public ResponseInterface getCustomerServices(int customerId) {
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            List<Services> services = customer.getServices().stream()
                    .map(value -> new Services(value.getServiceId(), value.getServiceName(), value.getServiceDescription()))
                    .collect(Collectors.toList());
            return new BodyResponse("", HttpStatus.OK, services);
        }

        return new EmptyBodyResponse("please check your customer id", HttpStatus.BAD_REQUEST);
    }

    @CacheEvict(cacheNames = {"services-customer"}, allEntries = true)
    @Override
    public ResponseInterface addServiceToCustomer(int serviceId, int customerId) {
        Optional<ServiceEntity> serviceOptional = this.serviceRepository.findById(serviceId);
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent() && serviceOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            customer.getServices().add(serviceOptional.get());
            this.customerRepository.save(customer);
            return new EmptyBodyResponse("service has been added to customer", HttpStatus.ACCEPTED);
        }
        return new EmptyBodyResponse("please check your customer/service id", HttpStatus.BAD_REQUEST);
    }

    private ServiceEntity generateServiceEntity(Services service) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName(service.getServiceName());
        serviceEntity.setServiceDescription(service.getServicesDescription());
        serviceEntity.setServiceCreated(new Timestamp(System.currentTimeMillis()));
        return serviceEntity;
    }

}
