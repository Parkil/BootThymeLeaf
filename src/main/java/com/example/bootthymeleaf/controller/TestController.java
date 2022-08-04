package com.example.bootthymeleaf.controller;

import com.example.bootthymeleaf.vo.Param;
import com.example.springbootjsp.vo.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

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
}
