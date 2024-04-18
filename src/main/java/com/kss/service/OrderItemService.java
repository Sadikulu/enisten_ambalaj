package com.kss.service;

import com.kss.domains.OrderItem;
import com.kss.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
}
