package com.example.bootthymeleaf.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class InsertVO {
    @NotBlank
    private String textValue;

    @NotBlank
    private String textValue2;

    @NotBlank
    private String textValueSizeLimit;
}
