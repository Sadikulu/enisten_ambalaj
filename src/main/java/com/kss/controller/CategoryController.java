package com.kss.controller;

import com.kss.domains.enums.CategoryStatus;
import com.kss.dto.CategoryDTO;
import com.kss.dto.request.CategoryRequest;
import com.kss.dto.request.CategoryUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/option")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<CategoryDTO>> getAllBrandsForOption(){
        return ResponseEntity.ok(categoryService.getAllCategoryList());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO= categoryService.saveCategory(categoryRequest);
        KSSResponse response = new KSSResponse(ResponseMessage.CATEGORY_CREATED_RESPONSE_MESSAGE, true,categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesWithPage(@RequestParam(value = "q",required = false)String query,
                                                                      @RequestParam(value = "status",required = false)CategoryStatus status,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size, @RequestParam("sort") String prop,
                                                                      @RequestParam(value = "direction", required = false, defaultValue = "DESC") Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<CategoryDTO> pageDTO = categoryService.findAllWithPage(query,status,pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        CategoryDTO categoryDTO= categoryService.findCategoryById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        CategoryDTO categoryDTO=categoryService.updateCategory(id,categoryUpdateRequest);
        KSSResponse response = new KSSResponse(ResponseMessage.CATEGORY_UPDATED_RESPONSE_MESSAGE, true,categoryDTO);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> deleteCategory(@PathVariable Long id){
        CategoryDTO categoryDTO=categoryService.removeById(id);
        KSSResponse response = new KSSResponse(ResponseMessage.CATEGORY_DELETED_RESPONSE_MESSAGE, true,categoryDTO);
        return ResponseEntity.ok(response);
    }
}
