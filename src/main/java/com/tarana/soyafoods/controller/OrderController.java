//package com.tarana.soyafoods.controller;
//
//import com.tarana.soyafoods.model.*;
//import com.tarana.soyafoods.repository.OrderRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@Controller
//public class OrderController {
//
//    private final OrderRepository orderRepo;
//
//    public OrderController(OrderRepository orderRepo) {
//        this.orderRepo = orderRepo;
//    }
//
//    @PostMapping("/order/place")
//    public String placeOrder(@RequestParam String name,
//                             @RequestParam String phone,
//                             @RequestParam String address,
//                             @RequestParam String payment,
//                             HttpSession session) {
//
//        Map<Long, CartItem> cart =
//                (Map<Long, CartItem>) session.getAttribute("cart");
//
//        if (cart == null || cart.isEmpty()) {
//            return "redirect:/cart";
//        }
//
//        // 🔹 Calculate total
//        BigDecimal total = cart.values().stream()
//                .map(CartItem::getTotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // 🔹 Create order
//        Order order = new Order();
//        order.setCustomerName(name);
//        order.setPhone(phone);
//        order.setAddress(address);
//        order.setTotalAmount(total);
//        order.setPaymentMethod(payment);
//        order.setPaymentStatus(
//                payment.equals("ONLINE") ? "PENDING" : "PAID"
//        );
//        order.setOrderStatus("NEW");
//
//        // 🔹 Create order items
//        List<OrderItem> items = new ArrayList<>();
//
//        for (CartItem c : cart.values()) {
//            OrderItem item = new OrderItem();
//            item.setOrder(order);
//            item.setProductId(c.getProductId());
//            item.setProductName(c.getName());
//            item.setQuantity(c.getQuantity());
//            item.setPrice(c.getPrice());
//            items.add(item);
//        }
//
//        order.setItems(items);
//
//        // 🔹 Save everything (cascade)
//        orderRepo.save(order);
//
//        // 🔹 Clear cart
//        session.removeAttribute("cart");
//
//        if ("ONLINE".equals(payment)) {
//            return "redirect:/pay"; // Razorpay next
//        }
//
//        return "redirect:/order/success";
//    }
//
//    @GetMapping("/order/success")
//    public String success() {
//        return "order-success";
//    }
//}
