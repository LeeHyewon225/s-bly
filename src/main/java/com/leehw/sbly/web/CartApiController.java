package com.leehw.sbly.web;

import com.leehw.sbly.service.cart.CartService;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartApiController {
    private final CartService cartService;

    @PostMapping("/api/cart")
    public Long save(@RequestBody CartSaveRequestDto cartSaveRequestDto){
        return cartService.save(cartSaveRequestDto);
    }

    @DeleteMapping("/api/cart")
    public Long delete(@RequestBody List<Long> deleteCart){
        return cartService.delete(deleteCart);
    }

    @DeleteMapping("/api/cart/{id}")
    public Long order(@PathVariable Long id, @RequestBody List<Long> orderCart){
        return cartService.order(id, orderCart);
    }
}
