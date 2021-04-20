package com.customer.service.demo.database;

import com.customer.service.demo.entity.Customer;
import com.customer.service.demo.entity.Service;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.repository.ServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class ServiceDatabaseTest {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void add_service_without_customer() {


        Service service = generateServiceObject(
                "add service", "this is adding new service w/o customer id",
                new Timestamp(System.currentTimeMillis()), null);

        try {
            this.serviceRepository.save(service);
            assertTrue("service has been added without customer", true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail("failed to add service w/o customer");
        }
    }

    @Test
    public void get_service_without_customer() {
        Service service = generateServiceObject(
                "get service 1", "getting new service after store it in database w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(service);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }
        Service retriveService = this.serviceRepository.findById(service.getServiceId()).get();
        if (retriveService.equals(service)) {
            assertTrue("services has been added and retrieved", true);
        } else {
            fail("retrieved services were not matched");
        }
    }

    @Test
    public void update_service_without_customer() {
        Service service = generateServiceObject(
                "update service", "this is to update new service after storing in db w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(service);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }

        Service retriveService = this.serviceRepository.findById(service.getServiceId()).get();
        retriveService.setServiceName("update service 2");
        service.setServiceDescription("this is to update new service after storing in db w/o customer 2");
        try {
            this.serviceRepository.save(service);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }
        if (service.equals(retriveService) &&
                (!service.getServiceName().equals(retriveService.getServiceName())
                        || !service.getServiceDescription().equals(retriveService.getServiceDescription()))
        ) {
            assertTrue("service were added and updated", true);
        } else {
            fail("services were added but not updated");
        }

    }


    @Test
    public void delete_service_without_customer() {
        Service service = generateServiceObject("delete service", "this is deleting the service after storing in db it w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(service);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }

        Optional<Service> deleteService = this.serviceRepository.findById(service.getServiceId());

        deleteService.ifPresent(removeService -> {
            this.serviceRepository.deleteById(removeService.getServiceId());

        });

        deleteService = this.serviceRepository.findById(service.getServiceId());
        deleteService.ifPresentOrElse((service1) -> {
                    fail("service were found but were not suppose to be found");
                },
                () -> {
                    assertTrue("service were added then deleted", true);
                });
    }

    @Test
    public void add_service_with_customer() {
        Customer customer = generateCustomerObject("ali", "alkhateeb",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customer);
        } catch (Exception e) {
            fail("failed to add customer, message = " + e.getMessage());
        }

        Service service = generateServiceObject("new service with customer",
                "This object will contain new service with customer id",
                new Timestamp(System.currentTimeMillis()),
                customer.getCustomerId());

        try{
            this.serviceRepository.save(service);
        }catch (Exception e){
            fail("failed to add service with customer, message = " + e.getMessage());
        }

        Optional<Service> retriveService = this.serviceRepository.findById(service.getServiceId());
        if (retriveService.isPresent() && (retriveService.get().getServiceId()==service.getServiceId() && retriveService.get().getCustomerId().equals(customer.getCustomerId()))){
            //assert true
            assertTrue("the service and the customer retrieved were matched", true);
        }else{
            //assert false
            fail("there were no match in the services retrieved or the customer as well.");
        }
    }


    private Customer generateCustomerObject(String firstName, String lastName, LocalDate birthday, Date createdDate) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setBirthday(birthday);
        customer.setCreatedDate(createdDate);
        return customer;
    }

    private Service generateServiceObject(String serviceName, String serviceDesc, Date createdDate, Integer customerId) {
        Service service = new Service();
        service.setServiceName(serviceName);
        service.setServiceDescription(serviceDesc);
        service.setServiceCreated(createdDate);
        service.setCustomerId(customerId);
        return service;
    }
}
