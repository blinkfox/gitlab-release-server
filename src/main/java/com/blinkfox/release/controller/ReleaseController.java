package com.blinkfox.release.controller;

import com.blinkfox.release.bean.link.LinkInfo;
import com.blinkfox.release.bean.release.ReleaseInfo;
import com.blinkfox.release.exception.RunException;
import com.blinkfox.release.service.LinkService;
import com.blinkfox.release.service.MinioService;
import com.blinkfox.release.service.ReleaseService;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Resource
    private LinkService linkService;

    @Resource
    private MinioService minioService;

    /**
     * 发布版本信息的接口.
     *
     * @param releaseInfo releaseInfo
     * @return map
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
     * @param releaseInfo releaseInfo
     * @return map
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

    /**
     * 删除版本资源信息的接口.
     *
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @param token token
     * @param tagName tagName
     * @param linkId linkId
     * @param linkUrl linkUrl
     * @return Map信息
     */
    @DeleteMapping("/{projectId}/{tagName}/links/{linkId}")
    public ResponseEntity<Map<String, Object>> updateRelease(
            @RequestParam("gitlabUrl") String gitlabUrl,
            @PathVariable("projectId") String projectId,
            @RequestParam("token") String token,
            @PathVariable("tagName") String tagName,
            @PathVariable("linkId") String linkId,
            @RequestParam("linkUrl") String linkUrl) {
        log.info("删除版本资源文件的信息 projectId: {}, tagName: {}, linkId: {}.", projectId, tagName, linkId);
        // 删除版本资源信息和 MinIO 中的文件.
        linkService.deleteLink(new LinkInfo(gitlabUrl, projectId, token, tagName).setLinkId(linkId));
        minioService.deleteObjectQuieylyByUrl(linkUrl);
        return ResponseEntity.ok(new HashMap<>(4));
    }

}
