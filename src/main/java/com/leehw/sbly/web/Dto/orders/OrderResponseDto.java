package com.leehw.sbly.web.Dto.orders;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.order.Orders;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class OrderResponseDto {
    private Long id;
    private Member member;
    private Goods goods;
    private boolean deliver;
    private boolean cancelDeliver;
    private String createdDate;
    private String modifiedDate;
    private String deliverDate;

    public OrderResponseDto(Orders orders){
        this.id = orders.getId();
        this.member = orders.getMember();
        this.goods = orders.getGoods();
        this.deliver = orders.isDeliver();
        this.cancelDeliver = orders.isCancelOrder();
        this.createdDate = orders.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.modifiedDate = orders.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.deliverDate = orders.getCreatedDate().plusDays(orders.getGoods().getDeliveryTime()).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
