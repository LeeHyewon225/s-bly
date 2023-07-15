package com.leehw.sbly.web.Dto.orders;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.order.Orders;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
    private Long id;
    private Member member;
    private Goods goods;
    private int deliver;
    private int cancelDeliver;
    private LocalDateTime createdDate;

    public OrderResponseDto(Orders orders){
        this.id = orders.getId();
        this.member = orders.getMember();
        this.goods = orders.getGoods();
        this.deliver = orders.getDeliver();
        this.cancelDeliver = orders.getCancelOrder();
        this.createdDate = orders.getCreatedDate();
    }
}
