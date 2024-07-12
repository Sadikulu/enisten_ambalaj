package com.kss.dto;

import com.kss.domains.*;
import com.kss.domains.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private LocalDate birthDate;

    private String email;
    private UserStatus status;
    private boolean builtIn;
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Set<String> roles;

    private List<FavoriteProductDTO> favoriteList;


    public void setRoles(Set<Role> roles) {
        Set<String> roleStr=new HashSet<>();
        roles.forEach(r->{
            roleStr.add(r.getRoleName().getName());
        });
        this.roles=roleStr;
    }

}
