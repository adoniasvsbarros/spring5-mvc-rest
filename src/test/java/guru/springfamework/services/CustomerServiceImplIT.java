package guru.springfamework.services;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	CustomerService customerService;
	
	@Before
	public void setupContext() throws Exception{
		log.info("Loading customer data " + customerRepository.findAll().size());
		
		//setup data for testing 
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.run();
		
		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}
	
    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
    }
    
    @Test
    public void patchCustomerUpdateLastName() throws Exception {
    	//given
    	Long id = getCustomerIdValue();
    	String updatedLastName = "updatedLastName";
    	
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();
    	
    	CustomerDTO customerDTO = new CustomerDTO();
    	customerDTO.setLastname(updatedLastName);
    	
    	//when
    	customerService.patchCustomer(id, customerDTO);
    	
    	Customer updatedCustomer = customerRepository.findById(id).get();
    	
    	//then
    	assertEquals(originalFirstName, updatedCustomer.getFirstname());
    	assertNotEquals(originalLastName, updatedCustomer.getLastname());
    	
    }
    
    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
	
	
}
