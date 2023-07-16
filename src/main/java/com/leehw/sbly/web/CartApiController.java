package com.leehw.sbly.web;

import com.leehw.sbly.service.cart.CartService;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartApiController {
    private final CartService cartService;

    @PostMapping("/api/cart")
    public Long save(@RequestBody CartSaveRequestDto cartSaveRequestDto){
        return cartService.save(cartSaveRequestDto);
    }
}
