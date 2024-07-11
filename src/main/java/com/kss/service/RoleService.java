package com.kss.service;

import com.kss.domains.Role;
import com.kss.domains.enums.RoleType;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public Role findByRoleName(RoleType roleType) {

        return roleRepository.findByRoleName(roleType).orElseThrow(()->
                new ResourceNotFoundException(String.format(
                        ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name())));

    }
}
