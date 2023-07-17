package com.leehw.sbly.web;

import com.leehw.sbly.service.goods.GoodsService;
import com.leehw.sbly.web.Dto.goods.GoodsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GoodsApiController {

    private final GoodsService goodsService;

    @GetMapping("/api/goods/{id}")
    public GoodsResponseDto findById(@PathVariable Long id){
        return goodsService.findById(id);
    }
}
