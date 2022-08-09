package com.example.bootthymeleaf.repo;

import com.example.bootthymeleaf.vo.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestRepo {
    List<Test> findList();

    Test findOne(String col1);
}
