package com.customer.service.demo.database;

import com.customer.service.demo.entity.Customer;
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
        Customer customer = generateCustomerObject("add new customer", "adding new customer to db",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customer);
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void update_customer() {
        Customer customer = generateCustomerObject("update customer", "create new customer and retrieve db and update it",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));

        try {
            this.customerRepository.save(customer);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Optional<Customer> customerUpdate = this.customerRepository.findById(customer.getCustomerId());
        customerUpdate.ifPresent(cust -> {
            cust.setFirstName("update customer 2");
            cust.setLastName("update createdasdf asdf afdafd asdf");
            cust.setBirthday(LocalDate.of(1999, 01, 01));
            cust.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            try {
                this.customerRepository.save(cust);
                assertTrue("the customer has been updated", true);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });


    }

    @Test
    public void get_customers() {
        add_new_customer();
        update_customer();
        List<Customer> customers = this.customerRepository.findAll();
        //assert that customers should contain something in the list
        if (customers.size() > 0){
            assertTrue("there are some customers in the list", true);
        }else {
            fail("there should be some customer");
        }

    }

    @Test
    public void get_customer() {
        Customer customer = generateCustomerObject("get customer", "create customer and get it",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));
        try {
            this.customerRepository.save(customer);
        } catch (Exception e) {
            //assert error
            fail("fail to add customer");
        }

        Optional<Customer> retrieveCustomer = this.customerRepository.findById(customer.getCustomerId());
        if (retrieveCustomer.isPresent() && retrieveCustomer.get().equals(customer)) {
            assertTrue("retrieved customer in db equal to created customer", true);
        } else {
            //assert false
            fail("create customer and stored customer are not equal");
        }
    }

    @Test
    public void delete_customer() {
        Optional<Customer> retrieveCustomer;
        Customer customer = generateCustomerObject("delete customer", "delete customer and check if it was deleted",
                LocalDate.of(1999, 01, 01), new Timestamp(System.currentTimeMillis()));

        try {
            this.customerRepository.save(customer);
            retrieveCustomer = this.customerRepository.findById(customer.getCustomerId());
            retrieveCustomer.ifPresent(value -> {
                this.customerRepository.deleteById(customer.getCustomerId());
            });
        } catch (Exception e) {
            //assert error
            System.err.println("exception happen");
        }
        retrieveCustomer = this.customerRepository.findById(customer.getCustomerId());
        retrieveCustomer.ifPresentOrElse((exised) -> {
                    assertTrue("user should not be found here", false);
                },
                () -> {
                    assertTrue("user was deleted", true);
                });

    }

    private Customer generateCustomerObject(String firstName, String lastName, LocalDate birthday, Date createdDate) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setBirthday(birthday);
        customer.setCreatedDate(createdDate);
        return customer;
    }


}
