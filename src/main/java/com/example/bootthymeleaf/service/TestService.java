package com.example.bootthymeleaf.service;

import com.example.bootthymeleaf.repo.TestRepo;
import com.example.bootthymeleaf.vo.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepo testRepo;

    public List<Test> getAllList() {
        return testRepo.findList();
    }

    public Test getOne(String col1) {
        return testRepo.findOne(col1);
    }
}