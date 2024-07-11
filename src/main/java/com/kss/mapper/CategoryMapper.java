package com.kss.mapper;

import com.kss.domains.Category;
import com.kss.dto.CategoryDTO;
import com.kss.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);

    ProductCategoryDTO categoryToProductCategoryDTO(Category category);

    List<CategoryDTO> categoryListToCategoryDTOList(List<Category> categories);


}
