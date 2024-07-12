package com.kss.dto;

import com.kss.domains.Product;
import com.kss.domains.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCartItemDTO {

    private Long id;

    private Long productId;

    private Long quantity;

    private String title;

    private String imageId;

    private Double unitPrice;

    private Double discountedPrice;
    private Integer stockAmount;

    private Double totalPrice;

    private Integer discount;
    private Double tax;
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
