package guru.springfamework.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.controllers.v1.CategoryController;
import guru.springfamework.services.CategoryService;

public class CategoryControllerTest {

	public static final String NAME_JIM = "Jim";
	public static final String NAME_BOB = "Bob";
	
	@Mock
	CategoryService categoryService;
	
	@InjectMocks
	CategoryController categoryController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}
	
	@Test
	public void testListCategories() throws Exception{
		
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName(NAME_JIM);
		
		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2L);
		category2.setName(NAME_BOB);
		
		List<CategoryDTO> categories = Arrays.asList(category1, category2);
		
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		
		mockMvc.perform(get(CategoryController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)));
			
	}
	
	@Test
	public void testGetByNameCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName(NAME_JIM);
		
		when(categoryService.getCategoryByName(anyString())).thenReturn(category1);
		
		mockMvc.perform(get(CategoryController.BASE_URL + "/jim")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME_JIM)));
	}
	
}
