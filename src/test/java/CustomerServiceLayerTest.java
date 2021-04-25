import com.customer.service.demo.dto.Customer;
import com.customer.service.demo.dto.response.ResponseInterface;
import com.customer.service.demo.repository.CustomerRepository;
import com.customer.service.demo.service.CustomerService;
import com.customer.service.demo.service.implmentation.CustomerServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CustomerServiceLayerTest {

    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerMockRepository;

    private Customer customer;

    @Before
    public void init(){
        customerMockRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImplementation(customerMockRepository);
        customer = new Customer();
        customer.setFirstName("ALIIII");
        customer.setLastName("ALKHAAAAA");
        customer.setDateOfBirth(LocalDate.of(2021,01,01));
    }

    @Test
    public void insert_customer_using_service_layer(){

        ResponseInterface response = customerService.createCustomer(customer);
        Assertions.assertEquals(response.getStatus(), HttpStatus.CREATED);
    }


    @Test
    public void get_customers_using_service_layer(){
        ResponseInterface response = customerService.getCustomers();
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK);
    }

    @Test
    public void delete_customers_using_service_layer(){
        ResponseInterface response = customerService.deleteCustomer(0);
        Assertions.assertEquals(response.getStatus(), HttpStatus.NO_CONTENT);
    }

}
