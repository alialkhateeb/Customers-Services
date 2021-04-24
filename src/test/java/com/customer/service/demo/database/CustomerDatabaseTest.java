package com.customer.service.demo.database;

import com.customer.service.demo.entity.CustomerEntity;
import com.customer.service.demo.repository.CustomerRepository;
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
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class CustomerDatabaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void add_new_customer() {
        CustomerEntity customerEntity = generateCustomerObject("add new customer", "adding new customer to db",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customerEntity);
            assertTrue("customer were created and added", true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void update_customer() {

        Optional<CustomerEntity> customerUpdate = this.customerRepository.findAll().stream().findAny();
        if (customerUpdate.isPresent()){
            CustomerEntity cust = customerUpdate.get();
            cust.setFirstName("update customer from unit test");
            cust.setLastName("we have retrieved the customer to update its fields");
            cust.setBirthday(LocalDate.of(1999, 01, 01));
            cust.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            this.customerRepository.save(cust);
        }else{
            fail("no customer were found");
        }


    }

    @Test
    public void get_customers() {
        List<CustomerEntity> customerEntities = this.customerRepository.findAll();
        if (customerEntities.size() > 0){
            assertTrue("there are some customers in the list", true);
        }else {
            fail("there should be some customer");
        }

    }

    @Test
    public void get_customer() {
        CustomerEntity customerEntity = generateCustomerObject("get customer", "create customer and get it",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customerEntity);
        } catch (Exception e) {
            //assert error
            fail("fail to add customer");
        }

        Optional<CustomerEntity> retrieveCustomer = this.customerRepository.findById(customerEntity.getCustomerId());
        if (retrieveCustomer.isPresent() && retrieveCustomer.get().equals(customerEntity)) {
            assertTrue("retrieved customer in db equal to created customer", true);
        } else {
            //assert false
            fail("create customer and stored customer are not equal");
        }
    }

    @Test
    public void delete_customer() {

        Optional<CustomerEntity> customerOptional = this.customerRepository.findAll().stream().findAny();
        if (customerOptional.isPresent()){
            try{
                CustomerEntity customer = customerOptional.get();
                System.out.println("customer id =" + customer.getCustomerId());
                this.customerRepository.deleteById(customer.getCustomerId());
            }catch (Exception e){
                fail("there were an issue deleting a customer, error message =" + e.getMessage());
            }
        }
    }

    private CustomerEntity generateCustomerObject(String firstName, String lastName, LocalDate birthday, Date createdDate) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setBirthday(birthday);
        customerEntity.setCreatedDate(createdDate);
        return customerEntity;
    }


}
