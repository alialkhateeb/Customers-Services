package com.customer.service.demo.service.implmentation;

import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.GenericResponse;
import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.service.CustomerService;
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
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger LOGS = LoggerFactory.getLogger(CustomerServiceImplementation.class);


    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public GenericResponse createCustomer(Customer customer) {

        CustomerEntity customerEntity = createCustomerEntity(customer);
        try {
            this.customerRepository.save(customerEntity);
            return new GenericResponse("customer has been stored in db", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGS.error("error creating customer");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occured while storing customer", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public GenericResponse updateCustomer(int customerId, Customer customer) {
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            CustomerEntity customerEntity = customerOptional.get();
            customerEntity.setFirstName(customer.getFirstName());
            customerEntity.setLastName(customer.getLastName());
            customerEntity.setBirthday(customer.getDateOfBirth());
            try {
                this.customerRepository.save(customerEntity);
                return new GenericResponse("customer has been updated and stored in db", HttpStatus.CREATED);
            } catch (Exception e) {
                LOGS.error("error updating customer");
                LOGS.error(e.getMessage());
                return new GenericResponse("Issue occurred while storing customer", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new GenericResponse("customer does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GenericResponse getCustomer(int customerId) {
        Optional<CustomerEntity> customerOptional = this.customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            CustomerEntity customerEntity = customerOptional.get();
            Customer customer = new Customer();
            customer.setFirstName(customerEntity.getFirstName());
            customer.setLastName(customerEntity.getLastName());
            customer.setDateOfBirth(customerEntity.getBirthday());
            customer.setCustomerId(customerEntity.getCustomerId());
            return new GenericResponse("customer successfully retrieved", HttpStatus.OK, customer);
        } else {
            LOGS.info("customer with this id" + customerId + "does not exist");
            return new GenericResponse("invalid user id", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public GenericResponse<List<Customer>> getCustomers() {
        List<Customer> customers = this.customerRepository.findAll().stream().map(value -> {
            Customer customer = new Customer();
            customer.setFirstName(value.getFirstName());
            customer.setLastName(value.getLastName());
            customer.setDateOfBirth(value.getBirthday());
            customer.setCustomerId(value.getCustomerId());
            return customer;
        }).collect(Collectors.toList());
        return new GenericResponse<List<Customer>>("", HttpStatus.OK, customers);
    }

    @Override
    public GenericResponse deleteCustomer(int customerId) {
        try {
            this.customerRepository.deleteById(customerId);
            return new GenericResponse("customer has been deleted", HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            LOGS.error("error deleting customer");
            LOGS.error(e.getMessage());
            return new GenericResponse("customer does not exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGS.error("error deleting customer 2");
            LOGS.error(e.getMessage());
            return new GenericResponse("Issue occurred while storing customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CustomerEntity createCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(customer.getFirstName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setBirthday(customer.getDateOfBirth());
        customerEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return customerEntity;
    }
}
