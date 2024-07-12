package com.kss.mapper;

import com.kss.domains.Brand;
import com.kss.domains.Category;
import com.kss.domains.ImageFile;
import com.kss.domains.Product;
import com.kss.dto.FavoriteProductDTO;
import com.kss.dto.MostPopularProduct;
import com.kss.dto.ProductDTO;
import com.kss.dto.request.ProductRequest;
import com.kss.dto.request.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",uses = {ImageFileMapper.class,BrandMapper.class,CategoryMapper.class})
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productListToProductDTOList(List<Product> productList);

    @Mapping(source = "id",target = "productId")
    @Mapping(source = "image",target = "imageId",qualifiedByName = "getShowcaseImage")
    FavoriteProductDTO productToFavoriteProductDTO(Product product);

    List<FavoriteProductDTO> productListToFavoriteProductDTOList(List<Product> productList);


    @Mapping(target="brand", ignore=true)
    @Mapping(target="category", ignore=true)
    @Mapping(target="sku", ignore=true)
    @Mapping(target = "slug",ignore = true)
    @Mapping(target = "discountedPrice",ignore = true)
    Product productRequestToProduct(ProductRequest productRequest);

    @Mapping(target="brand", ignore=true)
    @Mapping(target="category", ignore=true)
    @Mapping(target="sku", ignore=true)
    @Mapping(target="image", ignore=true)
    Product productUpdateRequestToProduct(ProductUpdateRequest productUpdateRequest);


    @Named("getProductId")
    public static Long getProductId(Product product) {
        return product.getId();
    }

    @Named("getBrandId")
    public static Long getBrandId(Brand brand) {
        return brand.getId();
    }
    @Named("getCategoryId")
    public static Long getCategoryId(Category category) {
        return category.getId();
    }

    @Named("getShowcaseImage")
    public static String getShowcaseImage(Set<ImageFile> imageFiles) {
        return imageFiles.stream().filter(ImageFile::isShowcase).map(ImageFile::getId).findFirst().orElse(null);
    }
}
