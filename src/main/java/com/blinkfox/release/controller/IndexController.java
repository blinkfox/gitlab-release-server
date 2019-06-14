package com.blinkfox.release.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页根控制器.
 *
 * @author blinkfox on 2019-06-14.
 */
@RestController
@RequestMapping
public class IndexController {

    /**
     * 首页请求.
     *
     * @return 测试数据
     */
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello Release Server.");
    }

}
