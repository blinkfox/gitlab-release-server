package com.blinkfox.release.controller;

import com.blinkfox.release.bean.release.ReleaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页根控制器.
 *
 * @author blinkfox on 2019-06-14.
 */
@Slf4j
@CrossOrigin
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

    /**
     * index.html 首页请求.
     *
     * @return 字符串
     */
    @PostMapping("/release")
    @ResponseBody
    public ResponseEntity<Map<String, String>> release(@RequestBody ReleaseInfo releaseInfo) {
        log.info("release: {}", releaseInfo);
        Map<String, String> map = new HashMap<>(8);
        return ResponseEntity.ok(map);
    }

}
