package com.example.bootthymeleaf.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Test {
    private final String col1;
    private final String col2;
    private final String col3;
    private final LocalDateTime regDtm;
}
