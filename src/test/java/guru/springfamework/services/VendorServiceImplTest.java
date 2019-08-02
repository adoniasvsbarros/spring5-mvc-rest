package guru.springfamework.services;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;

public class VendorServiceImplTest {

	public static final String VENDOR_NAME = "7Eleven";
	public static final Long VENDOR_ID = 1L;
	
	@Mock
	VendorRepository vendorRepository;
	
	VendorService vendorService;
	
	@Before
	public void setupContext(){
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
	}
	
	@Test
	public void testGetAllVendors() throws Exception {
		//given
		List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
		
		//when
		when(vendorRepository.findAll()).thenReturn(vendors);
		
		//then
		assertEquals(3, vendorService.getAllVendors().size());
		
	}
	
	@Test
	public void testGetVendorById() throws Exception {
		//given
		Vendor vendor = new Vendor();
		vendor.setId(VENDOR_ID);
		vendor.setName(VENDOR_NAME);
		
		//when
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		VendorDTO vendorDTO = vendorService.getVendorById(VENDOR_ID);
		
		//then
		assertEquals(vendor.getName(), vendorDTO.getName());
		
	}
	
	@Test
	public void testCreateVendor() throws Exception {
		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(VENDOR_NAME);
		
		Vendor vendor = new Vendor();
		vendor.setId(VENDOR_ID);
		vendor.setName(vendorDTO.getName());
				
		//when
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
		VendorDTO createdVendorDTO = vendorService.createVendor(vendorDTO);
		
		//then
		assertEquals(vendorDTO.getName(), createdVendorDTO.getName());
		assertEquals(VendorController.BASE_URL + "/1", createdVendorDTO.getVendorurl());
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		//given
		Long id = VENDOR_ID;
		
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(VENDOR_NAME);
		
		Vendor vendor = new Vendor();
		vendor.setId(id);
		vendor.setName(vendorDTO.getName());
		
		//when
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
		VendorDTO updatedVendor = vendorService.updateVendor(id, vendorDTO);
		
		//then
		assertEquals(vendorDTO.getName(), updatedVendor.getName());
		assertEquals(VendorController.BASE_URL + "/1", updatedVendor.getVendorurl());
	}
	
//	@Test
//	public void testPatchNameVendor() throws Exception {
//		//given
//		Long id = VENDOR_ID;
//		
//		VendorDTO originalVendor = new VendorDTO();
//		originalVendor.setName(VENDOR_NAME);
//		
//		Vendor updatedVendor = new Vendor();
//		updatedVendor.setId(id);
//		updatedVendor.setName("updatedVendorName");
//		
//		String vendorName = originalVendor.getName();
//		
//		VendorDTO updatedVendorDTO = new VendorDTO();
//		updatedVendor.setName("updatedVendorName");
//		
//		//when
//		when(vendorRepository.save(any(Vendor.class))).thenReturn(updatedVendor);
//		VendorDTO savedVendor = vendorService.patchVendor(id, updatedVendorDTO);
//		
//		//then
//		assertNotEquals(originalVendor.getName(), savedVendor.getName());
//		
//	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		//given 
		Long id = VENDOR_ID;

		//when
		vendorService.deleteVendorById(id);
		
		//then
		verify(vendorRepository, times(1)).deleteById(anyLong());
	}
	
	
}
