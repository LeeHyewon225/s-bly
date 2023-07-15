package com.leehw.sbly.web.Dto.orders;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersSaveRequestDto {
    private Long member_id;
    private Long goods_id;

    @Builder
    public OrdersSaveRequestDto(Long member_id, Long goods_id){
        this.member_id = member_id;
        this.goods_id = goods_id;
    }
}
