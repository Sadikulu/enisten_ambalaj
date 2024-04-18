package com.kss.service;

import com.kss.domains.Category;
import com.kss.domains.Order;
import com.kss.domains.Product;
import com.kss.domains.User;
import com.kss.dto.MostPopularProduct;
import com.kss.exception.message.ErrorMessage;
import com.kss.report.ExcelReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Service
public class ReportService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

// **************************** REPORT *********************

    public ByteArrayInputStream getReport() {


        List<Category> categories=categoryService.getAllCategories();

        List<Product> products=productService.getAllProducts();
        List<Order> orders=orderService.getAllOrdersWithPage();
        List<User> users =  userService.getAllUsers();




        try {
            return ExcelReporter.getExcelReport(categories,products,orders,users);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }


    }

 //   *********Report Orders*********
    public ByteArrayInputStream getOrderReport(LocalDate date1, LocalDate date2, String type) {

        List<Order> orders =orderService.getAllOrdersWithPage();

        try {
            return ExcelReporter.getOrderExcelReport(orders,date1,date2,type);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }
    //*********Most Popular Products Report*********
    public ByteArrayInputStream getReportMostPopularProduct(int amount) {

        List<MostPopularProduct> products = productService.findMostPopularProductsOfLastMonthWithoutPage();


        try {
            return ExcelReporter.getReportMostPopularProduct (products,amount);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }

    //*********Stock Alarm Report*********
    public ByteArrayInputStream getReportStockAlarm() {

        List<Product> products =productService.getAllProducts();

        try {
            return ExcelReporter.getReportStockAlarm(products);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }
    //*********Unordered Product Report*********
    public ByteArrayInputStream getReportUnorderedProducts() {

        List<Product> products =productService.getAllProducts();
        List<Order> orders =orderService.getAllOrdersWithPage();


        try {
            return ExcelReporter.getReportUnorderedProducts(products,orders);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }


}
