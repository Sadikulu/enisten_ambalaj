package com.kss.service;

import com.kss.domains.User;
import com.kss.domains.UserAddress;
import com.kss.domains.enums.UserStatus;
import com.kss.dto.UserAddressDTO;
import com.kss.dto.request.UserAddressRequest;
import com.kss.dto.request.UserAddressUpdate;
import com.kss.exception.BadRequestException;
import com.kss.exception.ConflictException;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.mapper.UserAddressMapper;
import com.kss.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserAddressMapper userAddressMapper;
    private final UserService userService;
    private final OrderService orderService;

    public List<UserAddressDTO> getAllAddresses() {
        User user=userService.getCurrentUser();
        List<UserAddress> userAddresses=userAddressRepository.findAllByUser(user);
        return userAddressMapper.map(userAddresses);
    }

    public UserAddressDTO getAddressesById(Long id) {
        UserAddress userAddress=userAddressRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        return userAddressMapper.userAddressToUserAddressDTO(userAddress);
    }

    public UserAddressDTO saveAddress(UserAddressRequest userAddressRequest) {
        User user=userService.getCurrentUser();
        UserAddress userAddress= userAddressMapper.userAddressRequestToUserAddress(userAddressRequest);
        userAddress.setUser(user);
        userAddressRepository.save(userAddress);
        return userAddressMapper.userAddressToUserAddressDTO(userAddress);
    }

    public UserAddressDTO authUpdateAddress(Long id, UserAddressUpdate userAddressUpdate) {
        User user=userService.getCurrentUser();
        UserAddress userAddress=getAddressById(id);

        userAddress.setUser(user);
        userAddress.setTitle(userAddressUpdate.getTitle());
        userAddress.setFirstName(userAddressUpdate.getFirstName());
        userAddress.setLastName(userAddressUpdate.getLastName());
        userAddress.setEmail(userAddressUpdate.getEmail());
        userAddress.setPhone(userAddressUpdate.getPhone());
        userAddress.setProvince(userAddressUpdate.getProvince());
        userAddress.setAddress(userAddressUpdate.getAddress());
        userAddress.setCity(userAddressUpdate.getCity());
        userAddress.setCountry(userAddressUpdate.getCountry());
        userAddress.setUpdateAt(userAddressUpdate.getUpdateAt());

        userAddressRepository.save(userAddress);
        return userAddressMapper.userAddressToUserAddressDTO(userAddress);
    }

    public UserAddress getAddressById(Long id) {
        return userAddressRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
    }


    public void removeUserAddressById(Long id) {
        User user=userService.getCurrentUser();
        UserAddress userAddress = getAddressById(id);
        boolean existInvoiceAddress= orderService.existsByInvoiceAddress(userAddress);
        boolean existShippingAddress= orderService.existsByShippingAddress(userAddress);
        if (existInvoiceAddress && existShippingAddress) {
            throw new BadRequestException(String.format(ErrorMessage.USER_ADDRESS_USED_MESSAGE));
        }
        userAddressRepository.delete(userAddress);
    }

    public List<UserAddressDTO> getAllAddressesByUserId(Long userId) {
        User user=userService.getById(userId);
        List<UserAddress> userAddresses=userAddressRepository.findAllByUser(user);
        return userAddressMapper.map(userAddresses);
    }

    public UserAddressDTO adminGetAddressById(Long id) {
        UserAddress userAddress=getAddressById(id);
        return userAddressMapper.userAddressToUserAddressDTO(userAddress);
    }

    public UserAddressDTO adminUpdateAddress(Long id, UserAddressUpdate userAddressUpdate) {
        UserAddress userAddress=getAddressById(id);

        userAddress.setTitle(userAddressUpdate.getTitle());
        userAddress.setFirstName(userAddressUpdate.getFirstName());
        userAddress.setLastName(userAddressUpdate.getLastName());
        userAddress.setEmail(userAddressUpdate.getEmail());
        userAddress.setPhone(userAddressUpdate.getPhone());
        userAddress.setProvince(userAddressUpdate.getProvince());
        userAddress.setAddress(userAddressUpdate.getAddress());
        userAddress.setCity(userAddressUpdate.getCity());
        userAddress.setCountry(userAddressUpdate.getCountry());
        userAddress.setUpdateAt(userAddressUpdate.getUpdateAt());

        userAddressRepository.save(userAddress);
        return userAddressMapper.userAddressToUserAddressDTO(userAddress);
    }

    public void removeAdminUserAddressById(Long id) {
        UserAddress userAddress=getAddressById(id);
        boolean existInvoiceAddress= orderService.existsByInvoiceAddress(userAddress);
        boolean existShippingAddress= orderService.existsByShippingAddress(userAddress);
        if (existInvoiceAddress && existShippingAddress) {
            throw new BadRequestException(String.format(ErrorMessage.USER_ADDRESS_USED_MESSAGE));
        }
        userAddressRepository.delete(userAddress);
    }
}
