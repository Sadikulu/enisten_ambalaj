package com.kss.dto;

import com.kss.domains.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    private String email;

    private boolean builtIn;


    private Set<String> roles;


    public void setRoles(Set<Role> roles) {
        Set<String> roleStr=new HashSet<>();
        roles.forEach(r->{
            roleStr.add(r.getRoleName().getName());
        });
        this.roles=roleStr;
    }

}
