package com.blinkfox.release.controller;

import com.blinkfox.release.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> uploadAssets(@RequestParam("file") MultipartFile file)
            throws IOException {
        log.info("fileName: {}", file.getOriginalFilename());
        minioService.putObject(file.getOriginalFilename(), file.getInputStream());
        Map<String, String> map = new HashMap<>(8);
        return ResponseEntity.ok(map);
    }

}
