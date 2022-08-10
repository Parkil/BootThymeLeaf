package com.example.bootthymeleaf.service;

import com.example.bootthymeleaf.repo.TestRepo;
import com.example.bootthymeleaf.vo.Test;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    private final TestRepo testRepo;

    public List<Test> getAllList() {
        return testRepo.findList();
    }

    public Test getOne(String col1) {
        return testRepo.findOne(col1);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public int transactionTest() throws Exception{
        Test test = testRepo.findOne("1");
        logger.info("find result : {}", test);

        Test insert = new Test("111", "222", "333", LocalDateTime.now());
        int insertCnt = testRepo.add(insert);

        if(insertCnt == 0) {
            throw new Exception("user define exception");
        }

        Test modifyTest = new Test(test.getCol1(), "mod-1", "mod-2", test.getRegDtm());
        int updateCnt = testRepo.update(modifyTest);

        return insertCnt + updateCnt;
    }
}