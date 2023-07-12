package com.leehw.sbly.domain.goods;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
