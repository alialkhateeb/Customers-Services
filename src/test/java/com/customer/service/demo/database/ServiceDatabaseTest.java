package com.customer.service.demo.database;

import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.entity.ServiceEntity;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.repository.ServiceRepository;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class ServiceDatabaseTest {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void add_service() {


        ServiceEntity serviceEntity = generateServiceObject(
                "add service", "this is adding new service w/o customer id",
                new Timestamp(System.currentTimeMillis()));

        try {
            this.serviceRepository.save(serviceEntity);
            assertTrue("service has been added without customer", true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail("failed to add service w/o customer");
        }
    }

    @Test
    public void get_service() {
        Optional<ServiceEntity> service = this.serviceRepository.findAll().stream().findFirst();
        if (service.isPresent()) {
            assertTrue("service has been retrieved", true);
        } else {
            fail("no service were retrieved");
        }
    }

    @Test
    public void update_service() {

        Optional<ServiceEntity> serviceOptional = this.serviceRepository.findAll().stream().findFirst();
        if (serviceOptional.isPresent()) {
            ServiceEntity service = serviceOptional.get();
            service.setServiceName("update service");
            service.setServiceDescription("this is updating service");
            try {
                this.serviceRepository.save(service);
                assertTrue("service were updated", true);
            } catch (Exception e) {
                fail("failed to update service" + e.getMessage());
            }
        } else {
            fail("no service were found");
        }
    }


    @Test
    public void delete_service() {
        Optional<ServiceEntity> deleteService = this.serviceRepository.findAll().stream().findAny();
        if (deleteService.isPresent()) {
            this.serviceRepository.deleteById(deleteService.get().getServiceId());
        } else {
            fail("service were found but were not suppose to be found");
        }
        Optional<ServiceEntity> afterDeletion = this.serviceRepository.findById(deleteService.get().getServiceId());
        if (afterDeletion.isEmpty()) {
            assertTrue("the service has been deleted", true);
        } else {
            fail("the service were not deleted");
        }
    }

    @Test
    public void add_ten_services_add_three_customer() {
        List<ServiceEntity> services = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ServiceEntity service = generateServiceObject("Service " + i,
                    "this is service " + i, new Date());
            services.add(service);
        }
        List<CustomerEntity> customers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CustomerEntity customer = generateCustomerObject("Ali " + i, "Alkhateeb " + i,
                    LocalDate.of(1900, 01, 01), new Date());
            customers.add(customer);
        }

        try {
            this.serviceRepository.saveAll(services);
            this.customerRepository.saveAll(customers);
            assertTrue("ten services were added and three customers were added", true);
        } catch (Exception e) {
            fail("failed to add either services or customers");
        }
    }

    @Test
    public void map_services_to_single_customer() {
        List<ServiceEntity> services = this.serviceRepository.findAll();
        Optional<CustomerEntity> customerOptional = this.customerRepository.findAll().stream().findAny();
        if (customerOptional.isPresent()) {
            CustomerEntity customer = customerOptional.get();
            customer.setServices(services.stream().collect(Collectors.toSet()));
            try {
                this.customerRepository.save(customer);
                assertTrue("all services were added to a single customer", true);
            } catch (Exception e) {
                fail("were not able to add services to a single customer");
            }
        }else{
            fail("no customer were found");
        }
    }

    @Test
    public void map_same_services_to_two_customers(){
        List<CustomerEntity> customers = this.customerRepository.findAll();
        List<ServiceEntity> services = new ArrayList<>();
        customers.stream().forEach(value -> {
            if (value.getServices().size() > 0){
                services.addAll(value.getServices());
            }else{
                value.setServices(services.stream().collect(Collectors.toSet()));
                try {
                    this.customerRepository.save(value);
                }catch (Exception e){
                    fail("error while adding services to customers");
                }
            }
        });
        assertTrue("services were added to all customers", true);
    }


    private CustomerEntity generateCustomerObject(String firstName, String lastName, LocalDate birthday, Date createdDate) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setBirthday(birthday);
        customerEntity.setCreatedDate(createdDate);
        return customerEntity;
    }

    private ServiceEntity generateServiceObject(String serviceName, String serviceDesc, Date createdDate) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName(serviceName);
        serviceEntity.setServiceDescription(serviceDesc);
        serviceEntity.setServiceCreated(createdDate);
        return serviceEntity;
    }

}
