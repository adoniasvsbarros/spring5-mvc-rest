package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.mapping.TableOwner;
import org.springframework.stereotype.Service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl("/api/v1/customer/" + customer.getId());
					return customerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO)
				.orElseThrow(RuntimeException::new); 
				
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		
		Customer savedCustomer = customerRepository.save(customer);
		
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		
		returnDto.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
		
		return returnDto;
	}

}
