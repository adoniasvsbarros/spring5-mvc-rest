package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
	
	@ApiModelProperty(required = true)
	private String firstname;
	
	@ApiModelProperty(required = true)
	private String lastname;
	
	@JsonProperty("customer_url")
	private String customerUrl;
}
