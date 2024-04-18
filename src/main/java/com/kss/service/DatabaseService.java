package com.kss.service;

import com.kss.dto.DashboardCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final ContactMessageService contactMessageService;
    private final ShoppingCartService shoppingCartService;

    @Transactional
    public void resetAll(){
    userService.removeAllBuiltInFalseUsers();
    productService.removeAllBuiltInFalseProducts();
    categoryService.removeAllBuiltInFalseCategories();
    contactMessageService.removeAll();
    shoppingCartService.removeAllNotOwnedByUsers();
    orderService.removeAllNotOwnedByUser();
    }

    public DashboardCountDTO getCountOfAllRecords() {
        long userCount = userService.countUserRecords();
        long categoryCount = categoryService.countCategoryRecords();
        long productCount = productService.countProductRecords();
        long orderCount = orderService.countOrderRecords();
        long reviewCount = reviewService.countReviewRecords();
        long contactMessageCount = contactMessageService.countContactMessageRecords();
        DashboardCountDTO count = new DashboardCountDTO();
        count.setCustomerCount(userCount);
        count.setCategoryCount(categoryCount);
        count.setProductCount(productCount);
        count.setOrderCount(orderCount);
        count.setReviewCount(reviewCount);
        count.setContactMessageCount(contactMessageCount);
        return count;
    }
}
