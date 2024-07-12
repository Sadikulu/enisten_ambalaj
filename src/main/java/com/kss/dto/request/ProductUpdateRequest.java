package com.kss.dto.request;

import com.kss.domains.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;
import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    @NotBlank(message = "You must provide a title")
    @Size(min = 2, max = 150, message = "The Title you have entered '${validatedValue}' must be between {min} and {max} character long")
    private String title;

    @NotBlank
    @Size(max = 500, message = "The short description you have entered '${validatedValue}' must be {max} character long")
    private String shortDesc;
    @NotBlank
    @Size(max = 3500, message = "The long description you have entered '${validatedValue}' must be {max} character long" )
    private String longDesc;

    private Double price;

    private Double tax;

    @NotNull(message = "Please enter discount percentage")
    @Min(0)
    @Max(100)
    private Integer discount;

    private Integer stockAmount;

    private Integer stockAlarmLimit;

    @NotNull(message = "Please provide if the product is featured or not")
    private Boolean featured;

    private Set<String> imageId;

    private Boolean newProduct;

    private ProductStatus status;

    private Double width;

    private Double length;

    private Double height;

    private Long categoryId;

    private Long brandId;

}
