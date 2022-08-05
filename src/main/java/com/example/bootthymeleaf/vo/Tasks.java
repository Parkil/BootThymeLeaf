package com.example.bootthymeleaf.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Tasks {
    private final int id;
    private final String title;
    private final String text;
    private final LocalDateTime dueTo;
}
