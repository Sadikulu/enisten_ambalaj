package com.kss.dto.request;

import com.kss.domains.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequest {

    @NotNull
    private Long orderId;
    @NotNull
    private List<OrderUpdateProduct> products;
}
