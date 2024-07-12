package com.kss.controller;

import com.kss.domains.enums.ReviewStatus;
import com.kss.dto.ReviewDTO;
import com.kss.dto.request.ReviewRequest;
import com.kss.dto.request.ReviewUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewService reviewService;

    @PostMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<KSSResponse> saveReview(@RequestBody ReviewRequest reviewRequest){
        ReviewDTO reviewDTO = reviewService.saveReview(reviewRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.REVIEW_SAVE_RESPONSE,true, reviewDTO);
       return new ResponseEntity<> (KSSResponse, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<PageImpl<ReviewDTO>> getAllReviewsWithFilter(@RequestParam(value = "q",required = false) String query,
                                                                       @RequestParam(value = "rate",required = false) Byte rate,
                                                                       @RequestParam(value = "status",required = false) ReviewStatus status,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size,
                                                                       @RequestParam("sort") String prop,
                                                                       @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        PageImpl<ReviewDTO> allReviews = reviewService.getAllReviewsWithFilter(query,rate,status,pageable);
        return ResponseEntity.ok(allReviews);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ReviewDTO> findReviewById(@PathVariable Long id){
        ReviewDTO reviewDTO = reviewService.findReviewById(id);
        return ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Page<ReviewDTO>> findReviewByProductId(@PathVariable("id") Long id,
                                                           @RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value="direction",
                                                                   required = false,
                                                                   defaultValue = "DESC") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<ReviewDTO> reviewDTO = reviewService.findReviewByProductId(id,pageable);
        return ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/auth")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<Page<ReviewDTO>> findAllUsersReviews(@RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sort") String prop,
                                                               @RequestParam(value = "direction", required = false,
                                                                       defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,prop));
        Page<ReviewDTO> userReviewDTOPage = reviewService.findAllUsersReviews(pageable);
        return ResponseEntity.ok(userReviewDTOPage);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<ReviewDTO> findUserReviewById(@PathVariable("id") Long id){
        ReviewDTO reviewDTO=reviewService.findByIdAndUser(id);
        return  ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/admin/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Page<ReviewDTO>> findUserAllReviews(@PathVariable("id") Long userId,
                                                        @RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sort") String prop,
                                                        @RequestParam(value="direction",
                                                                required = false,
                                                                defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<ReviewDTO> reviewDTO=reviewService.findUserAllReviews(userId,pageable);
        return  ResponseEntity.ok(reviewDTO);
    }

    @PutMapping("{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> updateReviewById(@Valid @PathVariable("id") Long id, @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        ReviewDTO reviewDTO=reviewService.updateReviewById(id, reviewUpdateRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.REVIEW_UPDATED_RESPONSE,true,reviewDTO);
        return ResponseEntity.ok(KSSResponse);
    }

    @DeleteMapping("{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> deleteReviewById(@PathVariable("id") Long id){
        ReviewDTO reviewDTO = reviewService.deleteReviewById(id);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.REVIEW_DELETE_RESPONSE,true,reviewDTO);
        return ResponseEntity.ok(KSSResponse);
    }
}
