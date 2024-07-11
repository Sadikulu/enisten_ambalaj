package com.kss.mapper;

import com.kss.domains.Coupons;
import com.kss.domains.Order;
import com.kss.domains.OrderCoupon;
import com.kss.domains.User;
import com.kss.domains.enums.CouponsType;
import com.kss.dto.OrderDTO;
import com.kss.dto.request.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, UserAddressMapper.class})
public interface OrderMapper {

    @Mapping(source = "orderItems",target = "orderItemsDTO")
    @Mapping(source = "user",target = "customer",qualifiedByName = "getUserFullName")
    @Mapping(source = "invoiceAddress",target = "invoiceAddressDTO")
    @Mapping(source = "shippingAddress",target = "shippingAddressDTO")
    @Mapping(target = "couponDiscount", expression = "java(getCouponDiscount(order.getOrderCoupons(),order.getSubTotal(),order.getDiscount(),order.getTax()))")
    OrderDTO orderToOrderDTO (Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order orderRequestToOrder (OrderRequest orderRequest);

    List<OrderDTO> map (List<Order> orderList);

    @Named("getUserFullName")
    public static String getUserId(User user){
        return user.getFirstName() + " " +user.getLastName();
    }

    public default Double getCouponDiscount(Set<OrderCoupon> orderCoupon,double subTotal, double discount, double tax){
        double orderDiscount = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        if (orderCoupon.size()>0){
            Coupons coupon = orderCoupon.stream().findFirst().get().getCoupons();
            if (coupon.getType().equals(CouponsType.EXACT_AMOUNT)){
                orderDiscount = coupon.getAmount();
            }else{
                orderDiscount = (((subTotal-discount)+tax)*coupon.getAmount())/100;
            }
        }
        return Double.parseDouble(df.format(orderDiscount).replaceAll(",","."));
    }

}