package com.kss.controller;

import com.kss.domains.enums.OrderStatus;
import com.kss.dto.OrderDTO;
import com.kss.dto.request.OrderRequest;
import com.kss.dto.request.OrderUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PageImpl<OrderDTO>> getAllOrdersWithPage(
            @RequestParam(value = "q",required = false) String query,
            @RequestParam(value = "status",required = false) List<OrderStatus> status,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "direction", required = false, defaultValue = "DESC")
            Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        PageImpl<OrderDTO> pageDTO = orderService.getAllOrdersWithPage(query, status, startDate, endDate, pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<OrderDTO> getOrdersById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findOrderById(id);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> getAuthOrdersById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findOrderByIdAndUser(id);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/auth")
    public ResponseEntity<Page<OrderDTO>> getAuthOrdersWithPage(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value = "direction",
                                                                   required = false, defaultValue = "DESC")Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<OrderDTO> pageDTO = orderService.findAuthOrderWithPage(pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<PageImpl<OrderDTO>> getOrdersWithUserIdAndPage
            (@PathVariable("id") Long userId,
             @RequestParam(value = "status",required = false) List<OrderStatus> status,
             @RequestParam(value = "date1",required = false) String startDate,
             @RequestParam(value = "date2",required = false) String endDate,
             @RequestParam("page") int page,
             @RequestParam("size") int size,
             @RequestParam("sort") String prop,
             @RequestParam(value = "direction", required = false, defaultValue = "DESC")
             Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        PageImpl<OrderDTO> pageDTO = orderService.findUserWithOrderAndPage(userId, startDate, endDate, status, pageable);
        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')or hasRole('CUSTOMER')")
    public ResponseEntity<KSSResponse> createOrder(@RequestHeader("cartUUID") String cartUUID, @Valid @RequestBody OrderRequest orderRequest){
      OrderDTO orderDTO=  orderService.createOrder(cartUUID,orderRequest);
      KSSResponse response=new KSSResponse(ResponseMessage.ORDER_CREATE_RESPONSE,
              true,orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> updateOrder(@Valid @RequestBody OrderUpdateRequest orderUpdateRequest){
        OrderDTO orderDTO=  orderService.updateOrder(orderUpdateRequest);
        KSSResponse response=new KSSResponse(ResponseMessage.ORDER_UPDATE_RESPONSE,
                true,orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/auth/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KSSResponse> updateOrderStatus(@PathVariable ("id") Long orderId, @RequestParam("status") OrderStatus status){
        OrderDTO orderDTO=  orderService.updateOrderStatus(orderId,status);
        KSSResponse response=new KSSResponse(ResponseMessage.ORDER_UPDATE_RESPONSE,true,orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

