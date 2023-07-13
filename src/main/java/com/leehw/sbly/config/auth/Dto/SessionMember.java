package com.leehw.sbly.config.auth.Dto;

import com.leehw.sbly.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;

    public SessionMember(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
