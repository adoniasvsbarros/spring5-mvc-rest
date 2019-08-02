package guru.springfamework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

public class CustomerServiceTest {
	
	private final static String FIRST_NAME = "Fred";
	private final static String LAST_NAME = "Mercury";
	
	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;
	

	@Before
	public void setupContext() throws Exception{
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}
	
	@Test
	public void testListCustomers() throws Exception {
		//given
		List<Customer> customers = Arrays.asList(new Customer(), new Customer());
		
		//when
		when(customerRepository.findAll()).thenReturn(customers);
		
		//then
		assertEquals(2, customerService.getAllCustomers().size());
	}
	
	@Test
	public void testGetCustomer() throws Exception {
		//given
		Customer customer = new Customer();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		customer.setId(1L);
		
		//when
		when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
		
		//then
		assertEquals(FIRST_NAME, customerService.getCustomerById(1L).getFirstname());
	}
	
	@Test
	public void createNewCustomer() throws Exception {
		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		//when
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
		
		//then
		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("api/v1/customers/1", savedDto.getCustomerUrl());
	}
}
