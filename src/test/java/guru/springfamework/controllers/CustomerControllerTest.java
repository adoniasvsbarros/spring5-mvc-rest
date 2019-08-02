package guru.springfamework.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.services.CustomerService;

public class CustomerControllerTest extends AbstractRestControllerTest{

	private static final String API_V1_CUSTOMERS_URL = "/api/v1/customers/";
	private static final String FIRST_NAME = "Fred";
	private static final String LAST_NAME = "Mercury";

	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@Before
	public void setupContext() throws Exception{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
	public void getCustomersController() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRST_NAME);
		customer1.setLastname(LAST_NAME);
		customer1.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Bob");
		customer2.setLastname("Marley");
		customer2.setCustomerUrl("/api/v1/customer/2");
		
		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));
		
		mockMvc.perform(get("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}
	
	@Test
	public void getCustomerById() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRST_NAME);
		customer1.setLastname(LAST_NAME);
		customer1.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");
		
		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
		
		mockMvc.perform(get(API_V1_CUSTOMERS_URL + "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customer_url", equalTo(API_V1_CUSTOMERS_URL + "1")));
	}
	
	@Test
	public void createNewCustomer() throws Exception{
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");
		
		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);
		
		mockMvc.perform(post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
		.andExpect(jsonPath("$.customer_url", equalTo(API_V1_CUSTOMERS_URL + "1")));
	}
	
	@Test
	public void updateCustomerById() throws Exception{
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");
		
		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
		
		//when/then
		mockMvc.perform(put(API_V1_CUSTOMERS_URL + "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
		.andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
		.andExpect(jsonPath("$.customer_url", equalTo(API_V1_CUSTOMERS_URL + "1")));
		
	}

	
}
