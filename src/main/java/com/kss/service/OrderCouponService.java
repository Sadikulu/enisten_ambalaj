package com.kss.service;

import com.kss.domains.OrderCoupon;
import com.kss.domains.User;
import com.kss.dto.OrderCouponDTO;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.mapper.OrderCouponMapper;
import com.kss.repository.OrderCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCouponService {

    private final UserService userService;
    private final OrderCouponMapper orderCouponMapper;
    private final OrderCouponRepository orderCouponRepository;

    public Page<OrderCouponDTO> getAuthCouponsWithPage(Long id,Pageable pageable) {
        Page<OrderCoupon> orderCoupons = orderCouponRepository.findByUserId(id,pageable);
        return orderCoupons.map(orderCouponMapper::orderCouponToOrderCouponDTO);
    }
}
