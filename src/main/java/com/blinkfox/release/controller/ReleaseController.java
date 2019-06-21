package com.blinkfox.release.controller;

import com.blinkfox.release.bean.release.ReleaseInfo;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发布 release 的控制器类.
 *
 * @author blinkfox on 2019-06-21.
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/releases")
public class ReleaseController {

    /**
     * index.html 首页请求.
     *
     * @return 字符串
     */
    @PostMapping("/")
    public ResponseEntity<Map<String, String>> release(@RequestBody ReleaseInfo releaseInfo) {
        log.info("release: {}", releaseInfo);
        Map<String, String> map = new HashMap<>(8);
        return ResponseEntity.ok(map);
    }

}
