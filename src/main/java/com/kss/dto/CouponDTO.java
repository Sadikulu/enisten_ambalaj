package com.kss.dto;

import com.kss.domains.enums.CouponsStatus;
import com.kss.domains.enums.CouponsType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {

    private Long id;


    private String code;


    private double amount;


    private CouponsType type;


    private int life;


    private CouponsStatus status;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;



}
