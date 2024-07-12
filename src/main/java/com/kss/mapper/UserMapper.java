package com.kss.mapper;

import com.kss.domains.User;
import com.kss.dto.UserDTO;
import com.kss.dto.UserDeleteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring",uses = ProductMapper.class)
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> map(List<User> userList);

    UserDeleteDTO userToUserDeleteDTO(User user);

}
