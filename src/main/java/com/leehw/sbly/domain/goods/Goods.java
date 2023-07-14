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

    @Column(nullable = false, name = "main_category")
    private int mainCategory;

    @Column(nullable = false, name = "sub_category")
    private int subCategory;

    @Column(nullable = false)
    private int deliveryTime;

    @OneToMany(mappedBy = "goods")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "goods")
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Goods(String name, int price, String image, int mainCategory, int subCategory, int deliveryTime){
        this.name = name;
        this.price = price;
        this.image = image;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.deliveryTime = deliveryTime;
    }
}
