package guru.springframework.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.v1.VendorController;
import guru.springframework.domain.Vendor;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	VendorRepository vendorRepository;
	VendorMapper vendorMapper;

	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository.findAll()
				.stream()
				.map(vendor -> {
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setVendorurl(getUrl(vendor.getId()));
					return vendorDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id)
					.map(vendor -> {
						VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
						vendorDTO.setVendorurl(getUrl(id));
						return vendorDTO;
					})
					.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createVendor(VendorDTO vendorDTO) {
		Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDTO);
		Vendor savedVendor = vendorRepository.save(vendorToSave);
		VendorDTO returnVendorDto = vendorMapper.vendorToVendorDTO(savedVendor);
		returnVendorDto.setVendorurl(getUrl(savedVendor.getId()));
		
		return returnVendorDto;
	}

	@Override
	public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
		Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDTO);
		vendorToSave.setId(id);
		Vendor savedVendor = vendorRepository.save(vendorToSave);
		VendorDTO returnVendorDto = vendorMapper.vendorToVendorDTO(savedVendor);
		returnVendorDto.setVendorurl(getUrl(savedVendor.getId()));
		
		return returnVendorDto;
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id)
				.map(vendor -> {
					if(vendorDTO.getName() != null) {
						vendor.setName(vendorDTO.getName());						
					}
					
					VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
					returnDTO.setVendorurl(getUrl(id));
					
					return returnDTO;
				}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		vendorRepository.deleteById(id);
		
	}
	
	private String getUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}

}
