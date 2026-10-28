//package com.tarana.soyafoods.model;
//
//import java.math.BigDecimal;
//
//public class CartItem {
//
//    private Long productId;
//    private String name;
//    private BigDecimal price;
//    private int quantity;
//
//    public CartItem(Long productId, String name, BigDecimal price, int quantity) {
//        this.productId = productId;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//    }
//
//    // ✅ GETTERS
//    public Long getProductId() {
//        return productId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    // ✅ SETTERS
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    // ✅ HELPER
//    public BigDecimal getTotal() {
//        return price.multiply(BigDecimal.valueOf(quantity));
//    }
//}
