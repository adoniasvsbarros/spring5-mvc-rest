package guru.springframework.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	//@Mapping(source="name",target="name")
	CategoryDTO categoryToCategoryDTO(Category category);
}
