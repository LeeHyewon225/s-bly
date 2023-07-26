package com.leehw.sbly.web;

import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.service.orders.OrdersService;
import com.leehw.sbly.web.Dto.orders.OrdersSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrdersApiController {
    private final OrdersService ordersService;

    @PostMapping("/api/orders")
    public Long save(@RequestBody OrdersSaveRequestDto ordersSaveRequestDto){
        Long result = ordersService.save(ordersSaveRequestDto);
        return result;
    }

    @PutMapping("/api/orders/{id}")
    public Long cancel(@PathVariable Long id){
        return ordersService.cancel(id);
    }
}
