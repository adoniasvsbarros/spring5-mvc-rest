package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
	
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
			
	//@Mapping(source=,target=)
	CustomerDTO customerToCustomerDTO(Customer customer);
	
	Customer customerDtoToCustomer(CustomerDTO customerDTO);
	
}
