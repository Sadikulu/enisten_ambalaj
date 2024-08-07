package com.kss.dto;

import com.kss.domains.ShoppingCartItem;
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
public class ShoppingCartDTO {

    private Long id;
    private String cartUUID;
    private Double grandTotal;
    private LocalDateTime createAt;
    private List<ShoppingCartItemDTO> shoppingCartItemDTO;
}
