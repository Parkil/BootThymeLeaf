package com.example.bootthymeleaf.controller;

import com.example.bootthymeleaf.vo.Param;
import com.example.bootthymeleaf.vo.Tasks;
import com.example.springbootjsp.vo.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
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

    @GetMapping("/view_default")
    public String viewDefault() {
        return "/contents/view_default";
    }

    @GetMapping("/dialect/list")
    public String dialectList(Model model) {
        Tasks t1 = new Tasks(1, "t1", "text1", LocalDateTime.now());
        Tasks t2 = new Tasks(2, "t2", "text2", LocalDateTime.now());
        Tasks t3 = new Tasks(3, "t3", "text3", LocalDateTime.now());
        List<Tasks> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);

        model.addAttribute("tasks", list);
        return "/dialect/list";

    }

    @GetMapping("/dialect/banner")
    public String dialectBanner() {
        return "/dialect/banner";

    }
}
