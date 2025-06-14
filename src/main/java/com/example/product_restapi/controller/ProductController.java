package com.example.product_restapi.controller;

import com.example.product_restapi.entity.Product;
import com.example.product_restapi.entity.UserInfo;
import com.example.product_restapi.service.ProductService;
import com.example.product_restapi.service.UserInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    UserInfoDetailService userInfoDetailService;

    @PostMapping("/newUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoDetailService.addUser(userInfo);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/product/add")
    public Product addProductItem(@RequestBody Product product) {
        return productService.addProduct(product);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/product/get")
    public List<Product> getAllProductItems() {
        return productService.getAllProducts();
    }
    //http://localhost:8080/api/product/getById?ids=2
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/product/getById")
    public List<Product> getProductsByIds(@RequestParam List<Long> ids) {
        return productService.getProductsByIds(ids);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/product/delete/{id}")
    public void deleteProductItem(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/product/update/{id}")
    public Product updateProductItem(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}