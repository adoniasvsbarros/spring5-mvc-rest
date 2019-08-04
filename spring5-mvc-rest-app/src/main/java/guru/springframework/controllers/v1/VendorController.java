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

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Vendor API")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

	public static final String BASE_URL = "/api/v1/vendors";

	private VendorService vendorService;

	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}

	@ApiOperation(value = "List all vendors")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	VendorListDTO getAllVendors() {
		return new VendorListDTO(vendorService.getAllVendors());
	}

	@ApiOperation(value = "Get vendor by id")
	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	VendorDTO getVendorById(@PathVariable Long id) {
		return vendorService.getVendorById(id);
	}

	@ApiOperation(value = "Create a vendor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createVendor(vendorDTO);
	}

	@ApiOperation(value = "Update a vendor")
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.updateVendor(id, vendorDTO);
	}

	@ApiOperation(value = "Update one or more properties of a vendor")
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.patchVendor(id, vendorDTO);
	}

	@ApiOperation(value = "Delete a vendor")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteVendorById(@PathVariable Long id) {
		vendorService.deleteVendorById(id);
	}

}
