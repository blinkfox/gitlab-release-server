package com.blinkfox.release.controller;

import com.blinkfox.release.kits.StringKit;
import com.blinkfox.release.service.MinioService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadController.
 *
 * @author blinkfox on 2019-06-20.
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private MinioService minioService;

    /**
     * index.html 首页请求.
     *
     * @return 字符串
     */
    @PostMapping("/assets")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadAssets(@RequestParam("file") MultipartFile file)
            throws IOException {
        log.info("fileName: {}", file.getOriginalFilename());
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", 200);
        map.put("id", StringKit.getUuid());
        map.put("name", file.getOriginalFilename());
        map.put("url", minioService.putObject(file.getOriginalFilename(), file.getInputStream()));
        return ResponseEntity.ok(map);
    }

}
