package com.leehw.sbly.domain.order;

import com.leehw.sbly.domain.BaseTimeEntity;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member orders_member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods orders_goods;

    @Column
    private int deliver;

    @Builder
    public Orders(Member orders_member, Goods orders_goods, int deliver){
        this.orders_member = orders_member;
        this.orders_goods = orders_goods;
        this.deliver = deliver;
    }

}
