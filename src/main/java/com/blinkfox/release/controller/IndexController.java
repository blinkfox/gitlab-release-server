package com.blinkfox.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页根控制器.
 *
 * @author blinkfox on 2019-06-14.
 */
@Controller
@RequestMapping
public class IndexController {

    /**
     * index.html 首页请求.
     *
     * @return 字符串
     */
    @GetMapping
    public ModelAndView index(ModelAndView modelView) {
        modelView.setViewName("index");
        return modelView;
    }

}
