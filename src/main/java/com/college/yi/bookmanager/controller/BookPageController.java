package com.college.yi.bookmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {
    @GetMapping("/books")
    public String contextLoads() {
        return "index"; // index.htmlを表示する
    }
}