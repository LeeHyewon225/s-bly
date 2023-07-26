package com.leehw.sbly.web.Dto.member;

import lombok.Getter;

@Getter
public class MoneyChargeRequestDto {
    private long money;

    public MoneyChargeRequestDto(long money){
        this.money = money;
    }
}
