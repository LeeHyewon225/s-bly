package com.leehw.sbly.domain.member;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.order.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private long money;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Member(String email, String name, long money){
        this.email = email;
        this.name = name;
        this.money = money;
    }

    public Member update(String name){
        this.name = name;

        return this;
    }

    public void priceCalculate(long price){
        money -= price;
    }

    public void chargeMoney(long chargeMoney){
        this.money += chargeMoney;
    }

    public void refund(Goods goods){
        this.money += goods.getPrice();
    }
}
