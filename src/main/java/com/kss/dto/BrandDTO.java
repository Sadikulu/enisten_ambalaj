package com.kss.dto;

import com.kss.domains.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private BrandStatus status;

    private String image;

    private Boolean builtIn = false;

    private LocalDateTime createAt = LocalDateTime.now();

    private LocalDateTime updateAt;

}
