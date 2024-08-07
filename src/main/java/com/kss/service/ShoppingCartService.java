package com.kss.service;

import com.kss.domains.Product;
import com.kss.domains.ShoppingCart;
import com.kss.domains.ShoppingCartItem;
import com.kss.dto.ShoppingCartDTO;
import com.kss.dto.ShoppingCartItemDTO;
import com.kss.dto.request.ShoppingCartRequest;
import com.kss.dto.request.ShoppingCartUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.exception.BadRequestException;
import com.kss.exception.ResourceNotFoundException;
import com.kss.exception.message.ErrorMessage;
import com.kss.mapper.ShoppingCartItemMapper;
import com.kss.mapper.ShoppingCartMapper;
import com.kss.repository.ShoppingCartItemRepository;
import com.kss.repository.ShoppingCartRepository;
import com.kss.reusableMethods.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    private final ProductService productService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final ShoppingCartItemMapper shoppingCartItemMapper;

    private final DiscountCalculator discountCalculator;


    public ShoppingCart findShoppingCartByUUID(String cartUUID) {
        return shoppingCartRepository.findByCartUUID(cartUUID).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,cartUUID)));
    }

    public ShoppingCartItemDTO createCartItem(String cartUUID, ShoppingCartRequest shoppingCartRequest) {
        ShoppingCart shoppingCart = findShoppingCartByUUID(cartUUID);
        Double totalPrice;
        Product product = productService.findProductById(shoppingCartRequest.getProductId());
        ShoppingCartItem foundItem = shoppingCartItemRepository.findByProductIdAndShoppingCartCartUUID(product.getId(), shoppingCart.getCartUUID());
        ShoppingCartItem shoppingCartItem = null;

        if (shoppingCartRequest.getQuantity() > product.getStockAmount()) {
            throw new BadRequestException(String.format(ErrorMessage.PRODUCT_OUT_OF_STOCK_MESSAGE, product.getId()));
        }

        if (foundItem != null) {
            Integer quantity = foundItem.getQuantity() + shoppingCartRequest.getQuantity();
            if (quantity > product.getStockAmount()) {
                throw new BadRequestException(String.format(ErrorMessage.PRODUCT_OUT_OF_STOCK_MESSAGE, product.getId()));
            }
            foundItem.setQuantity(quantity);
            totalPrice = quantity * product.getPrice();
            foundItem.setTotalPrice(totalPrice);
            foundItem.setUpdateAt(LocalDateTime.now());
            shoppingCartItemRepository.save(foundItem);
            shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() + shoppingCartRequest.getQuantity() * product.getPrice());
            shoppingCartItem = foundItem;
        } else {
            shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(shoppingCartRequest.getQuantity());
            shoppingCartItem.setShoppingCart(shoppingCart);
            totalPrice = shoppingCartRequest.getQuantity() * product.getPrice();
            shoppingCartItem.setTotalPrice(totalPrice);
            shoppingCartItemRepository.save(shoppingCartItem);
            shoppingCart.getShoppingCartItem().add(shoppingCartItem);
            shoppingCart.setGrandTotal(shoppingCart.getGrandTotal() + totalPrice);
        }
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.shoppingCartItemToShoppingCartItemDTO(shoppingCartItem);
    }

    public ShoppingCartItemDTO removeItemById(String cartUUID,Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByCartUUID(cartUUID).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE));
        ShoppingCartItem foundItem = shoppingCartItemRepository.findByProductIdAndShoppingCartCartUUID(productId,
               cartUUID);
        shoppingCart.setGrandTotal(shoppingCart.getGrandTotal()-foundItem.getTotalPrice());
        shoppingCartItemRepository.delete(foundItem);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.shoppingCartItemToShoppingCartItemDTO(foundItem);
    }

    public void cleanShoppingCart(String cartUUID){
        ShoppingCart shoppingCart = findShoppingCartByUUID(cartUUID);
        shoppingCartItemRepository.deleteAll(shoppingCart.getShoppingCartItem());
        shoppingCart.setGrandTotal(0.0);
    }

    public ShoppingCartItemDTO changeQuantity(String cartUUID,ShoppingCartUpdateRequest shoppingCartUpdateRequest, String op) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByCartUUID(cartUUID).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE));
        Product product = productService.findProductById(shoppingCartUpdateRequest.getProductId());
        ShoppingCartItem foundItem = shoppingCartItemRepository.findByProductIdAndShoppingCartCartUUID(product.getId(),shoppingCart.getCartUUID());
        switch (op){
            case "increase":
                foundItem.setQuantity(foundItem.getQuantity()+1);
                shoppingCart.setGrandTotal(shoppingCart.getGrandTotal()+foundItem.getProduct().getPrice());
                break;
            case "decrease":
                foundItem.setQuantity(foundItem.getQuantity()-1);
                shoppingCart.setGrandTotal(shoppingCart.getGrandTotal()-foundItem.getProduct().getPrice());
                break;
        }
        Double totalPrice = discountCalculator.totalPriceWithDiscountCalculate(foundItem.getQuantity(), product.getPrice(), product.getDiscount());
        foundItem.setTotalPrice(totalPrice);
        foundItem.setUpdateAt(LocalDateTime.now());
        shoppingCartItemRepository.save(foundItem);
        save(shoppingCart);

        return shoppingCartItemMapper.shoppingCartItemToShoppingCartItemDTO(foundItem);
    }


    public ShoppingCartDTO getShoppingCart(String cartUUID) {
        ShoppingCart shoppingCart;
        if (!cartUUID.isEmpty()){
            shoppingCart = findShoppingCartByUUID(cartUUID);
        } else{
            shoppingCart = new ShoppingCart();
            shoppingCart.setCartUUID(UUID.randomUUID().toString());
            shoppingCartRepository.save(shoppingCart);

        }
       return shoppingCartMapper.shoppingCartToShoppingCartDTO(shoppingCart);
    }

    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void removeAllNotOwnedByUsers() {
        shoppingCartRepository.deleteAllShoppingCartsWithoutUser();
    }
}

