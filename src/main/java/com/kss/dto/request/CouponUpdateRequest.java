package com.kss.dto.request;

import com.kss.domains.enums.CouponsStatus;
import com.kss.domains.enums.CouponsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponUpdateRequest
{
    @NotNull(message = "Please provide amount")
    private Double amount;

    @NotNull(message = "Please provide type")
    private CouponsType type;

    @NotNull(message = "Please provide lifetime")
    private Integer life;

    @NotNull(message = "Please provide description")
    private CouponsStatus status;
}
