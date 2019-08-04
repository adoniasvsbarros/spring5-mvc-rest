package guru.springfamework.controllers.v1;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.services.VendorService;

public class VendorControllerTest extends AbstractRestControllerTest {

	@Mock
	VendorService vendorService;

	@InjectMocks
	VendorController vendorController;

	MockMvc mockMvc;

	@Before
	public void setUpContext() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();
	}

	@Test
	public void testGetAllVendors() throws Exception {
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName("Le Biscuit");

		VendorDTO vendor2 = new VendorDTO();
		vendor2.setName("Adidas");

		when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

		mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.vendors", hasSize(2)));
	}

	@Test
	public void testGetVendorById() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Bobs");

		when(vendorService.getVendorById(1L)).thenReturn(vendorDTO);

		mockMvc.perform(get(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
	}

	@Test
	public void testCreateNewVendor() throws Exception {
		VendorDTO vendor = new VendorDTO();
		vendor.setName("Marie Store");

		VendorDTO returnVendor = new VendorDTO();
		returnVendor.setName(vendor.getName());
		returnVendor.setVendorurl(VendorController.BASE_URL + "/1");

		when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(returnVendor);

		mockMvc.perform(post(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendor)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", equalTo("Marie Store")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testUpdateVendor() throws Exception {
		VendorDTO vendor = new VendorDTO();
		vendor.setName("Buba Store");

		VendorDTO returnVendor = new VendorDTO();
		returnVendor.setName(vendor.getName());

		when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

		String contentString = mockMvc.perform(put(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendor)))
				.andReturn().getResponse().getContentAsString();
		
		System.out.println(contentString);
		
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.name", equalTo("Buba Store")))
//				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testPatchVendor() throws Exception {
		VendorDTO vendor = new VendorDTO();
		vendor.setName("Jack Store");
		
		VendorDTO returnVendor = new VendorDTO();
		returnVendor.setName("Buba Store");
		returnVendor.setVendorurl(VendorController.BASE_URL + "/1");
		
		when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);
		
		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendor)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Buba Store")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testDeleteById() throws Exception {
		mockMvc.perform(delete(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(vendorService, times(1)).deleteVendorById(anyLong());
	}

}
