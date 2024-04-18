package com.kss.dto;

import com.kss.domains.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;

    private String userFullName;

    private String content;

    private Byte rating;

    private ProductStatus status;


    private LocalDateTime createdAt;


    private LocalDateTime publishedAt;

}
