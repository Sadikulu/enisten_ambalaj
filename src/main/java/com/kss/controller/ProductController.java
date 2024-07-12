package com.kss.controller;

import com.kss.domains.enums.ProductStatus;
import com.kss.dto.ProductDTO;
import com.kss.dto.request.ProductRequest;
import com.kss.dto.request.ProductUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageImpl<ProductDTO>> getProducts(@RequestParam(value = "q",required = false) String query,
                                                            @RequestParam(value = "categories",required = false) List<Long> categoryId,
                                                            @RequestParam(value = "brands",required = false) List<Long> brandId,
                                                            @RequestParam(value = "minPrice",required = false) Integer minPrice,
                                                            @RequestParam(value = "maxPrice",required = false) Integer maxPrice,
                                                            @RequestParam(value = "status",required = false) ProductStatus status,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sort") String prop,
                                                            @RequestParam(value = "direction",
                                                                            required = false,
                                                                            defaultValue = "DESC") Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        PageImpl<ProductDTO> productDTO = productService.findAllWithQueryAndPage(query,categoryId,brandId,minPrice,maxPrice,status,pageable);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/most-popular")
    public ResponseEntity<Page<ProductDTO>> getMostPopularProducts(@RequestParam("page") int page,
                                                                              @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductDTO> productDTOPage = productService.findMostPopularProductsOfLastMonth(pageable);
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/featured")
    public ResponseEntity<Page<ProductDTO>> getFeaturedProducts(@RequestParam("page") int page,
                                                                @RequestParam("size") int size,
                                                                @RequestParam("sort") String prop,
                                                                @RequestParam(value = "direction",
                                                                         required = false,
                                                                         defaultValue = "DESC")Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<ProductDTO> productDTOPage = productService.findFeaturedProducts(pageable);
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<ProductDTO>> getNewProducts(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value = "direction",
                                                                         required = false,
                                                                         defaultValue = "DESC")Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction, prop));
        Page<ProductDTO> productDTOPage = productService.findNewProducts(pageable);
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductWithId(@PathVariable Long id){
        ProductDTO productDTO = productService.getProductDTOById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping("/{id}/auth/favorite")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<KSSResponse> productSetFavorite(@PathVariable("id")Long id){
        ProductDTO productDTO = productService.setFavorite(id);
        KSSResponse response = new KSSResponse(ResponseMessage.PRODUCT_LIKE_RESPONSE_MESSAGE,true, productDTO);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> saveProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductDTO productDTO = productService.saveProduct(productRequest);
        KSSResponse response = new KSSResponse(ResponseMessage.PRODUCT_SAVED_RESPONSE_MESSAGE,true,productDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> updateProduct(@PathVariable Long id,
                                                     @Valid @RequestBody ProductUpdateRequest productUpdateRequest){
    ProductDTO productDTO = productService.updateProduct(id, productUpdateRequest);
    KSSResponse response = new KSSResponse(ResponseMessage.PRODUCT_UPDATED_RESPONSE_MESSAGE,true,productDTO);
    return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> deleteProduct(@PathVariable Long id){
        ProductDTO productDTO = productService.removeById(id);
        KSSResponse response = new KSSResponse(ResponseMessage.PRODUCT_DELETE_RESPONSE_MESSAGE,true,productDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/image/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KSSResponse>deleteProductImage(@PathVariable String id){
        productService.removeImageById(id);
        KSSResponse response =new KSSResponse(ResponseMessage.IMAGE_DELETE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);
    }
}
