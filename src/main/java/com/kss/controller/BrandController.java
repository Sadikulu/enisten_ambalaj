package com.kss.controller;

import com.kss.domains.enums.BrandStatus;
import com.kss.dto.BrandDTO;
import com.kss.dto.request.BrandRequest;
import com.kss.dto.request.BrandUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/option")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<BrandDTO>> getAllBrandsForOption(){
       return ResponseEntity.ok(brandService.getAllBrandList());
    }

    @GetMapping
    public ResponseEntity<PageImpl<BrandDTO>> getAllBrands(@RequestParam(value = "q",required = false)String query,
                                                           @RequestParam(value = "status",required = false)BrandStatus status,
                                                           @RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value="direction",required = false,
                                                               defaultValue = "DESC")Sort.Direction direction){
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        PageImpl<BrandDTO> brandDTOPage=brandService.getAllBrandsByPage(query,status,pageable);
        return ResponseEntity.ok(brandDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable("id") Long id){
        BrandDTO brandDTO=brandService.getBrandById(id);
        return ResponseEntity.ok(brandDTO);
    }

    @PostMapping("/{imageId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public  ResponseEntity<KSSResponse> createBrand(@PathVariable("imageId")String imageId, @Valid @RequestBody BrandRequest brandRequest){
        BrandDTO brandDTO = brandService.createBrand(imageId,brandRequest);
        KSSResponse KSSResponse =new KSSResponse(ResponseMessage.BRAND_CREATE_RESPONSE_MESSAGE,true,brandDTO);
        return ResponseEntity.ok(KSSResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> updateBrand(@Valid @PathVariable("id") Long id, @RequestBody BrandUpdateRequest brandUpdateRequest){
     BrandDTO brandDTO=brandService.updateBrand(id,brandUpdateRequest);
        KSSResponse KSSResponse =new KSSResponse(ResponseMessage.BRAND_UPDATE_RESPONSE_MESSAGE,true,brandDTO);
        return  ResponseEntity.ok(KSSResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> deleteBrandById(@PathVariable("id") Long id){
        BrandDTO brandDTO=brandService.deleteBrandById(id);
        KSSResponse KSSResponse =new KSSResponse(ResponseMessage.BRAND_DELETE_RESPONSE_MESSAGE,true,brandDTO);
        return ResponseEntity.ok(KSSResponse);
    }
}

