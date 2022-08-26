package com.example.bootthymeleaf.controller;

import com.example.bootthymeleaf.service.TestService;
import com.example.bootthymeleaf.vo.InsertVO;
import com.example.bootthymeleaf.vo.Param;
import com.example.springbootjsp.vo.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    /*
    자연이랑 구조
    header(고정)
    footer(고정)
    right(고정)
    main 부분만 변경

    fragment로 분리할만한 부분
    - 상품리스트의 상품 정보
        -> 상품 정보 표시는 큰 영역에서는 비슷하지만 세부사항이 다른 경우가 많아서
        따로 분리하는게 나을듯
    - 하단 페이징
    - 상품리스트의 리스트 (분류/정렬 조건 나오는 부분까지)
     */

    private final TestService testService;

    @GetMapping("/post")
    public String test(Param param, Model model) {
        Post post1 = new Post(1, "lee", "book1");
        Post post2 = new Post(2, "choi", "book2");
        Post post3 = new Post(3, "kim", "book3");
        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);

        System.out.println("param  : "+param);

        boolean testBoolean = "1".equals(param.getParamStr());

        model.addAttribute("list", list);
        model.addAttribute("param", param);
        model.addAttribute("testBoolean", testBoolean);
        return "/contents/home";
    }

    @GetMapping("/view1")
    public String view1() {
        return "/contents/view1";
    }

    @GetMapping("/view_default")
    public String viewDefault() {
        return "/contents/view_default";
    }

    @GetMapping("/dialect/list")
    public String dialectList(Model model) {
        /*
        Tasks t1 = new Tasks(1, "t1", "text1", LocalDateTime.now());
        Tasks t2 = new Tasks(2, "t2", "text2", LocalDateTime.now());
        Tasks t3 = new Tasks(3, "t3", "text3", LocalDateTime.now());
        List<Tasks> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);
         */
        model.addAttribute("tasks", testService.getAllList());
        return "/dialect/list";

    }

    @GetMapping("/dialect/banner")
    public String dialectBanner() {
        return "/dialect/banner";
    }

    @GetMapping("/dialect/banner/transaction")
    public String dialectBannerTransaction() throws Exception{
        int cnt = testService.transactionTest();
        System.out.println("cnt : "+cnt);
        return "/dialect/banner";
    }

    @GetMapping("/login")
    public String login() {
        return "/dialect/login";
    }

    @GetMapping("/dialect/insert")
    public String insertHTML(InsertVO insertVO) {
        return "/dialect/insert";
    }

    @PostMapping("/dialect/insert")
    public String execInsert(@Valid InsertVO insertVO, BindingResult bindingResult) {
        System.out.println("insertVO : "+insertVO);

//        if(bindingResult.hasErrors()) {
//            System.out.println("111 : "+bindingResult);
//            System.out.println("222 : "+bindingResult.getAllErrors());
//            return "/dialect/insert";
//        }

        return "/dialect/insert";
    }

    @GetMapping("/")
    public String index() {
        return "/dialect/banner";
    }
}
