package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {

	@ApiModelProperty(required = true)
	private String name;

	@JsonProperty("vendor_url")
	private String vendorurl;
}
