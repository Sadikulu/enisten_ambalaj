package com.kss.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponMailRequest {

    @NotNull
    private Long couponId;

    @NotNull
    private List<Long> userId;

    @NotBlank
    @Size(min = 25,message = "Your message must be at least {min} characters long.")
    private String message;
}
