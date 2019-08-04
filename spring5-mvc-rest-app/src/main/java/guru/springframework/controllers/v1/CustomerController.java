package guru.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.model.CustomerDTO;
import guru.springframework.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Customer API")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
	
	public static final String BASE_URL = "/api/v1/customers"; 

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@ApiOperation(value = "List all the customers")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		CustomerListDTO customerListDTO = new CustomerListDTO();
		customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
		return customerListDTO;
	}
	
	@ApiOperation(value = "Get a costumer by id")
	@GetMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomer(@PathVariable Long id){
		return customerService.getCustomerById(id);
	}
	
	@ApiOperation(value = "Create a costumer")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO){
		return customerService.createNewCustomer(customerDTO);
	}
	
	@ApiOperation(value = "Update a costumer")
	@PutMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
		return customerService.saveCustomerByDTO(id, customerDTO);
	}
	
	@ApiOperation(value = "Update one or more properties of a costumer")
	@PatchMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
		return customerService.patchCustomer(id, customerDTO);
	}
	
	@ApiOperation(value = "Delete a costumer")
	@DeleteMapping({"/{id}"})
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomer(@PathVariable Long id){
		customerService.deleteCustomerById(id);
	}
}
