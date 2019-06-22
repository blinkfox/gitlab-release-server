package com.blinkfox.release.controller;

import com.blinkfox.release.consts.Const;
import com.blinkfox.release.kits.StringKit;
import com.blinkfox.release.service.MinioService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传控制器.
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
     * @param file 上传的文件
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @return 字符串
     */
    @PostMapping("/assets")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadAssets(
            @RequestParam("file") MultipartFile file,
            @RequestParam("gitlabUrl") String gitlabUrl,
            @RequestParam("projectId") String projectId) throws IOException {
        log.info("gitlabUrl: {}", gitlabUrl);
        log.info("projectId: {}", projectId);

        // 上传对象到 MinIO 中，并返回 URL.
        String url = minioService.putObject(this.buildObjectName(
                this.getCodeByGitlabUrl(gitlabUrl), projectId, file.getOriginalFilename()), file.getInputStream());

        // 返回结果，上传控件必须要求必须返回 status 为 200 才算成功.
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", HttpStatus.OK.value());
        map.put("id", StringKit.getUuid());
        map.put("name", file.getOriginalFilename());
        map.put("url", url);
        return ResponseEntity.ok(map);
    }

    /**
     * 从GitLab URL 中提取关键标识名称，没有就返回 gitlab 字符串.
     *
     * @param gitlabUrl gitlabUrl
     * @return 关键
     */
    private String getCodeByGitlabUrl(String gitlabUrl) {
        String gitlabCode = gitlabUrl.startsWith(Const.HTTPS)
                ? StringUtils.substringBetween(gitlabUrl, Const.HTTPS, ".")
                : StringUtils.substringBetween(gitlabUrl, Const.HTTP, ".");
        return StringUtils.isBlank(gitlabCode) ? "gitlab" : gitlabCode;
    }

    /**
     * 生成对象名.
     *
     * @param gitlabCode gitlabCode
     * @param projectId projectId
     * @param fileName fileName
     * @return 对象名
     */
    private String buildObjectName(String gitlabCode, String projectId, String fileName) {
        return gitlabCode + Const.SEP + projectId + Const.SEP + fileName;
    }

}
