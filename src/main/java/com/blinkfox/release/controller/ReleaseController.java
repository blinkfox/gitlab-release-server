package com.blinkfox.release.controller;

import com.blinkfox.release.bean.release.ReleaseInfo;
import com.blinkfox.release.exception.RunException;
import com.blinkfox.release.service.ReleaseService;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private ReleaseService releaseService;

    /**
     * 发布版本信息的接口.
     *
     * @return 字符串
     */
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createRelease(@RequestBody ReleaseInfo releaseInfo) {
        log.info("开始发布版本，版本信息为：{}", releaseInfo);
        releaseService.createRelease(releaseInfo);
        return ResponseEntity.ok(new HashMap<>(4));
    }

    /**
     * 更新版本信息的接口.
     *
     * @return 字符串
     */
    @PutMapping("/{projectId}/{tagName}")
    public ResponseEntity<Map<String, Object>> updateRelease(@RequestBody ReleaseInfo releaseInfo,
            @PathVariable("projectId") String projectId, @PathVariable("tagName") String tagName) {
        log.info("开始发布版本，版本信息为：{}", releaseInfo);
        if (!projectId.equals(releaseInfo.getProjectId()) || !tagName.equals(releaseInfo.getTagName())) {
            throw new RunException("填写的 projectId 或者 tagName 与 URL 请求中的参数不符！");
        }

        // 更新 release 版本信息.
        releaseService.updateRelease(releaseInfo);
        return ResponseEntity.ok(new HashMap<>(4));
    }

}
