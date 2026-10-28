package com.tarana.soyafoods.controller;

import com.tarana.soyafoods.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam String name,
                            @RequestParam BigDecimal price,
                            @RequestParam int quantity,
                            HttpSession session) {

        Map<Long, CartItem> cart =
                (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        if (cart.containsKey(productId)) {
            cart.get(productId)
                    .setQuantity(cart.get(productId).getQuantity() + quantity);
        } else {
            cart.put(productId,
                    new CartItem(productId, name, price, quantity));
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        Map<Long, CartItem> cart =
                (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new HashMap<>();

        BigDecimal total = cart.values().stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout";
    }

    @GetMapping("/cart/mini")
    @ResponseBody
    public Collection<CartItem> miniCart(HttpSession session) {

        Map<Long, CartItem> cart =
                (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return List.of();
        }

        return cart.values();
    }

    @PostMapping("/cart/remove")
    @ResponseBody
    public void removeFromCart(@RequestParam Long productId,
                               HttpSession session) {

        Map<Long, CartItem> cart =
                (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.remove(productId);
        }
    }

    @PostMapping("/cart/update")
    @ResponseBody
    public void updateQuantity(@RequestParam Long productId,
                               @RequestParam int quantity,
                               HttpSession session) {

        Map<Long, CartItem> cart =
                (Map<Long, CartItem>) session.getAttribute("cart");

        if (cart == null) return;

        if (quantity <= 0) {
            cart.remove(productId);
        } else {
            CartItem item = cart.get(productId);
            if (item != null) {
                item.setQuantity(quantity);
            }
        }
    }


}

