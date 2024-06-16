package com.edgar.e_commerce_shop_backend.controller.order;

import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.WebOrder;
import com.edgar.e_commerce_shop_backend.service.WebOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private WebOrderService webOrderService;

    public OrderController(WebOrderService webOrderService) {
        this.webOrderService = webOrderService;
    }

    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser localUser) {
        return webOrderService.getOrders(localUser);
    }
}
