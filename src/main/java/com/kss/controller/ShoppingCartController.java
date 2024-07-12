package com.kss.controller;

import com.kss.dto.ShoppingCartDTO;
import com.kss.dto.ShoppingCartItemDTO;
import com.kss.dto.request.ShoppingCartRequest;
import com.kss.dto.request.ShoppingCartUpdateRequest;
import com.kss.dto.response.KSSResponse;
import com.kss.dto.response.ResponseMessage;
import com.kss.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@RequestHeader(value = "cartUUID", required = false) String cartUUID) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCart(cartUUID);
        return ResponseEntity.ok(shoppingCartDTO);
    }

    @PostMapping
    public ResponseEntity<KSSResponse> createCartItem(@RequestHeader(value = "cartUUID", required = false) String cartUUID,
                                                      @RequestBody ShoppingCartRequest shoppingCartRequest) {
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartService.createCartItem(cartUUID, shoppingCartRequest);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.ITEM_ADDED_RESPONSE_MESSAGE, true, shoppingCartItemDTO);
        return new ResponseEntity<>(KSSResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<KSSResponse> deleteCartItem(@RequestHeader("cartUUID") String cartUUID, @PathVariable("productId") Long productId) {
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartService.removeItemById(cartUUID, productId);
        KSSResponse KSSResponse = new KSSResponse(ResponseMessage.CART_ITEM_DELETED_RESPONSE_MESSAGE, true, shoppingCartItemDTO);
        return ResponseEntity.ok(KSSResponse);
    }

    @PutMapping("/{op}")
    public ResponseEntity<ShoppingCartItemDTO> changeQuantity(@RequestHeader("cartUUID") String cartUUID,
                                                              @RequestBody ShoppingCartUpdateRequest shoppingCartUpdateRequest, @PathVariable String op) {
        ShoppingCartItemDTO updatedItem = shoppingCartService.changeQuantity(cartUUID, shoppingCartUpdateRequest, op);
        return ResponseEntity.ok(updatedItem);
    }
}
