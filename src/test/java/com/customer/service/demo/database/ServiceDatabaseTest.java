package com.customer.service.demo.database;

import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.entity.ServiceEntity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


        ServiceEntity serviceEntity = generateServiceObject(
                "add service", "this is adding new service w/o customer id",
                new Timestamp(System.currentTimeMillis()), null);

        try {
            this.serviceRepository.save(serviceEntity);
            assertTrue("service has been added without customer", true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail("failed to add service w/o customer");
        }
    }

    @Test
    public void get_service_without_customer() {
        ServiceEntity serviceEntity = generateServiceObject(
                "get service 1", "getting new service after store it in database w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(serviceEntity);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }
        ServiceEntity retriveServiceEntity = this.serviceRepository.findById(serviceEntity.getServiceId()).get();
        if (retriveServiceEntity.equals(serviceEntity)) {
            assertTrue("services has been added and retrieved", true);
        } else {
            fail("retrieved services were not matched");
        }
    }

    @Test
    public void update_service_without_customer() {
        ServiceEntity serviceEntity = generateServiceObject(
                "update service", "this is to update new service after storing in db w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(serviceEntity);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }

        ServiceEntity retriveServiceEntity = this.serviceRepository.findById(serviceEntity.getServiceId()).get();
        retriveServiceEntity.setServiceName("update service 2");
        serviceEntity.setServiceDescription("this is to update new service after storing in db w/o customer 2");
        try {
            this.serviceRepository.save(serviceEntity);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }
        if (serviceEntity.equals(retriveServiceEntity) &&
                (!serviceEntity.getServiceName().equals(retriveServiceEntity.getServiceName())
                        || !serviceEntity.getServiceDescription().equals(retriveServiceEntity.getServiceDescription()))
        ) {
            assertTrue("service were added and updated", true);
        } else {
            fail("services were added but not updated");
        }

    }


    @Test
    public void delete_service_without_customer() {
        ServiceEntity serviceEntity = generateServiceObject("delete service", "this is deleting the service after storing in db it w/o customer",
                new Timestamp(System.currentTimeMillis()), null);
        try {
            this.serviceRepository.save(serviceEntity);
        } catch (Exception e) {
            fail("failed to add service w/o customer, message = " + e.getMessage());
        }

        Optional<ServiceEntity> deleteService = this.serviceRepository.findById(serviceEntity.getServiceId());

        deleteService.ifPresent(removeServiceEntity -> {
            this.serviceRepository.deleteById(removeServiceEntity.getServiceId());

        });

        deleteService = this.serviceRepository.findById(serviceEntity.getServiceId());
        deleteService.ifPresentOrElse((serviceEntity1) -> {
                    fail("service were found but were not suppose to be found");
                },
                () -> {
                    assertTrue("service were added then deleted", true);
                });
    }

    @Test
    public void add_service_with_customer() {
        CustomerEntity customerEntity = generateCustomerObject("ali", "alkhateeb",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customerEntity);
        } catch (Exception e) {
            fail("failed to add customer, message = " + e.getMessage());
        }

        ServiceEntity serviceEntity = generateServiceObject("new service with customer",
                "This object will contain new service with customer id",
                new Timestamp(System.currentTimeMillis()),
                customerEntity.getCustomerId());

        try{
            this.serviceRepository.save(serviceEntity);
        }catch (Exception e){
            fail("failed to add service with customer, message = " + e.getMessage());
        }

        Optional<ServiceEntity> retriveService = this.serviceRepository.findById(serviceEntity.getServiceId());
        if (retriveService.isPresent() && (retriveService.get().getServiceId()== serviceEntity.getServiceId() && retriveService.get().getCustomerId().equals(customerEntity.getCustomerId()))){
            //assert true
            assertTrue("the service and the customer retrieved were matched", true);
        }else{
            //assert false
            fail("there were no match in the services retrieved or the customer as well.");
        }
    }

    @Test
    public void add_ten_services_with_one_customer() {
        CustomerEntity customerEntity = generateCustomerObject("ali", "alkhateeb",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customerEntity);
        } catch (Exception e) {
            fail("failed to add customer, message = " + e.getMessage());
        }
        List<ServiceEntity> servicesEntities = new ArrayList<>();
        for (int i = 1; i <= 10; i++){
            ServiceEntity serviceEntity = generateServiceObject("new service with customer " + i,
                    "This object will contain new service with customer id " + i ,
                    new Timestamp(System.currentTimeMillis()),
                    customerEntity.getCustomerId());
            servicesEntities.add(serviceEntity);
        }
        try{
            this.serviceRepository.saveAll(servicesEntities);
        }catch (Exception e){
            fail("failed to add service with customer, message = " + e.getMessage());
        }
        List<ServiceEntity> list = this.serviceRepository.findAllServicesByCustomerId(customerEntity.getCustomerId());
        if (list.size() != servicesEntities.size()){

            fail("the size of stored data are not equal the one received from db");
        }
        list.stream().forEach(value -> {
            if (value.getCustomerId() != customerEntity.getCustomerId()){
                System.err.println(value);
                fail("there is a service that does not has a customer where it should have one");
            }
        });
    }




    private CustomerEntity generateCustomerObject(String firstName, String lastName, LocalDate birthday, Date createdDate) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setBirthday(birthday);
        customerEntity.setCreatedDate(createdDate);
        return customerEntity;
    }

    private ServiceEntity generateServiceObject(String serviceName, String serviceDesc, Date createdDate, Integer customerId) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName(serviceName);
        serviceEntity.setServiceDescription(serviceDesc);
        serviceEntity.setServiceCreated(createdDate);
        serviceEntity.setCustomerId(customerId);
        return serviceEntity;
    }
}
