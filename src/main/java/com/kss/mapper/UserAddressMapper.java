package com.kss.mapper;

import com.kss.domains.User;
import com.kss.domains.UserAddress;
import com.kss.dto.UserAddressDTO;
import com.kss.dto.request.UserAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {

    @Mapping(target = "userId",source = "user",qualifiedByName = "getUserIdForAddress")
    UserAddressDTO userAddressToUserAddressDTO(UserAddress userAddress);

    List<UserAddressDTO> map(List<UserAddress> userAddressList);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "user",ignore = true)
    UserAddress userAddressRequestToUserAddress(UserAddressRequest userAddressRequest);

    @Named("getUserIdForAddress")
    public static Long getUserIdForAddress(User user){
        return user.getId();
    }
}
