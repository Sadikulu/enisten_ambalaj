package com.kss.dto;

import com.kss.domains.User;
import lombok.*;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {


    private Long id;

    private String title;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String address;

    private String province;

    private String city;

    private String country;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long userId;


}
