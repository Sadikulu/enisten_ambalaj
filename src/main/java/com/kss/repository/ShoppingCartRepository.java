package com.kss.repository;

import com.kss.domains.Product;
import com.kss.domains.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCartUUID(String uuid);

    @Modifying
    @Query("DELETE FROM ShoppingCart sc WHERE sc.id NOT IN (SELECT u.shoppingCart.id FROM User u)")
    void deleteAllShoppingCartsWithoutUser();
}