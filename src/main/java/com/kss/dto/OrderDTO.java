package com.kss.dto;

import com.kss.domains.User;
import com.kss.domains.UserAddress;
import com.kss.domains.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String code;
    private OrderStatus status;
    private Double subTotal;
    private Double discount;
    private Double couponDiscount;
    private Double tax;
    private String contactName;
    private String contactPhone;
    private Double shippingCost;
    private String shippingDetails;
    private Double grandTotal;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<OrderItemDTO> orderItemsDTO;
    private String customer;
    private UserAddressDTO shippingAddressDTO;
    private UserAddressDTO invoiceAddressDTO;



}
