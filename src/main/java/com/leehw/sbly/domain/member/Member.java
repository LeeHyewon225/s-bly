package com.leehw.sbly.domain.member;

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
    private int money;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public Member(String email, String name, int money){
        this.email = email;
        this.name = name;
        this.money = money;
    }

    public Member update(String name){
        this.name = name;

        return this;
    }
}
