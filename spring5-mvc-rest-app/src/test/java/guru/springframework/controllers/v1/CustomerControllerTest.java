package guru.springframework.controllers.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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


import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.controllers.v1.RestResponseEntityExceptionHandler;
import guru.springframework.model.CustomerDTO;
import guru.springframework.services.CustomerService;

public class CustomerControllerTest extends AbstractRestControllerTest{
	
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
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
	
	@Test
	public void testGetAllCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRST_NAME);
		customer1.setLastname(LAST_NAME);
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Bob");
		customer2.setLastname("Marley");
		customer2.setCustomerUrl(CustomerController.BASE_URL + "/2");
		
		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));
		
		mockMvc.perform(get(CustomerController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRST_NAME);
		customer1.setLastname(LAST_NAME);
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
		
		mockMvc.perform(get(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception{
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(post(CustomerController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
				.andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
	}
	
	@Test
	public void testUpdateCustomerById() throws Exception{
		//given
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
		
		//when/then
		mockMvc.perform(put(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
		.andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
		.andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
		
	}
	
    @Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname("Flintstone");
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
    }
    
    @Test
    public void testDeleteCustomer() throws Exception{
    	mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk());
    	
    	verify(customerService, times(1)).deleteCustomerById(anyLong());
    	
    }
}
