package com.example.bootthymeleaf.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Param {
    private String paramStr;

    public void postConstruct() {

    }
}