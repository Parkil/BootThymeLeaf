package com.example.bootthymeleaf.controller;

import com.example.bootthymeleaf.vo.InsertVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestRestController {

    @PostMapping(value = "/rest/insert")
    public ResponseEntity<InsertVO> execInsert(@Valid InsertVO insertVO) {
        System.out.println(insertVO);
        return new ResponseEntity<>(insertVO, HttpStatus.OK);
    }
}
