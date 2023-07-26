package com.leehw.sbly.config.auth.Dto;

import com.leehw.sbly.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long id;
    private String name;
    private String email;
    private long money;

    public SessionMember(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.money = member.getMoney();
    }
}
