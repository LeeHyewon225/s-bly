package com.leehw.sbly.domain.goods;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.order.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column
    private String image;

    @Column(nullable = false)
    private int main_category;

    @Column(nullable = false)
    private int sub_category;

    @Column(nullable = false)
    private int delivery_time;

    @OneToMany(mappedBy = "orders_goods")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "cart_goods")
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Goods(String name, int price, String image, int main_category, int sub_category, int delivery_time){
        this.name = name;
        this.price = price;
        this.image = image;
        this.main_category = main_category;
        this.sub_category = sub_category;
        this.delivery_time = delivery_time;
    }
}
