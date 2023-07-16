package com.leehw.sbly.web.Dto.cart;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartSaveRequestDto {
    private Long member_id;
    private Long goods_id;

    @Builder
    public CartSaveRequestDto(Long member_id, Long goods_id){
        this.member_id = member_id;
        this.goods_id = goods_id;
    }
}
