package com.kss.dto.request;

import com.kss.domains.enums.ReviewStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

    private ReviewStatus status;
}
