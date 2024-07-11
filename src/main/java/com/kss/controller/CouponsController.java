package com.kss.controller;

import com.kss.dto.CouponDTO;
import com.kss.dto.request.CouponMailRequest;
import com.kss.dto.request.CouponRequest;
import com.kss.dto.request.CouponUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.CouponsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponsController {

    private final CouponsService couponsService;

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<CouponDTO> getCouponWithPath(@PathVariable("id") Long id){
        CouponDTO couponDTO  = couponsService.findCoupon(id);
        return ResponseEntity.ok(couponDTO);
    }

    @GetMapping("auth/{couponCode}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<CouponDTO> controlCouponWithCouponCode(@PathVariable("couponCode") String couponCode){
        CouponDTO couponDTO  = couponsService.findCouponByCouponCode(couponCode);
        return ResponseEntity.ok(couponDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<CouponDTO>> getAllCoupons(){
        List<CouponDTO> coupons = couponsService.getAllCouponsDTO();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<CouponDTO>> getAllCouponsWithPage(@RequestParam("page") int page,
                                                                 @RequestParam("size") int size,
                                                                 @RequestParam("sort") String prop,
                                                                 @RequestParam(value = "direction", required = false,
                                                                         defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<CouponDTO> pageDTO = couponsService.getAllCouponsWithPage(pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<KSSResponse> createCoupon(@Valid @RequestBody CouponRequest couponRequest){
        CouponDTO couponDTO = couponsService.saveCoupon(couponRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.COUPON_CREATE_RESPONSE, true, couponDTO);
        return ResponseEntity.ok(KSSResponse);
    }

    @PostMapping("/auth/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<KSSResponse> sendCoupon(@Valid @RequestBody CouponMailRequest couponMailRequest){
        couponsService.sendCoupon(couponMailRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.COUPON_MAIL_SENT_RESPONSE, true);
        return ResponseEntity.ok(KSSResponse);
    }

    @PutMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> updateCoupon(@PathVariable Long id,
                                                    @Valid @RequestBody CouponUpdateRequest couponUpdateRequest){
        CouponDTO couponDTO = couponsService.updateCoupon(id, couponUpdateRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.COUPON_UPDATE_RESPONSE, true, couponDTO);
        return ResponseEntity.ok(KSSResponse);
    }

    @DeleteMapping("/{id}/admin")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<KSSResponse> deleteCoupon(@PathVariable Long id){
        couponsService.deleteCoupon(id);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.COUPON_DELETE_RESPONSE, true, null);

        return ResponseEntity.ok(KSSResponse);
    }
}