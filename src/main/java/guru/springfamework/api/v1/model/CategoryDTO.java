package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CategoryDTO {
    private Long id;
    
    @ApiModelProperty(required = true)
    private String name;
}
