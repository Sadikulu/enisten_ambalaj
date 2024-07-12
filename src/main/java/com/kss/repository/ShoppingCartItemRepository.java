package com.kss.repository;

import com.kss.domains.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    ShoppingCartItem findByProductIdAndShoppingCartCartUUID(Long id, String uuid);
}
