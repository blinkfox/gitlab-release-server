package com.blinkfox.release.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blinkfox.release.bean.link.BaseLinkInfo;
import com.blinkfox.release.bean.link.LinkInfo;
import com.blinkfox.release.bean.release.ReleaseInfo;
import com.blinkfox.release.consts.Const;
import com.blinkfox.release.exception.RunException;
import com.blinkfox.release.kits.StringKit;
import com.blinkfox.release.service.LinkService;
import com.blinkfox.release.service.MinioService;
import com.blinkfox.release.service.ReleaseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 发布 release 版本相关的控制器类.
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
     * index.html 首页请求.
     *
     * @param file 上传的文件
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @return 字符串
     */
    @PostMapping("/assets/file")
    public ResponseEntity<Map<String, Object>> uploadAssets(
            @RequestParam("file") MultipartFile file,
            @RequestParam("gitlabUrl") String gitlabUrl,
            @RequestParam("projectId") String projectId,
            @RequestParam("tagName") String tagName,
            @RequestParam("fileName") String fileName) throws IOException {
        log.info("gitlabUrl: {}, projectId: {}, tagName: {}.", gitlabUrl, projectId, tagName);

        // 上传对象到 MinIO 中，并返回 URL.
        String url = minioService.putObject(this.buildObjectName(this.getCodeByGitlabUrl(gitlabUrl),
                projectId, tagName, fileName), file.getInputStream());

        // 返回结果，上传控件必须要求必须返回 status 为 200 才算成功.
        return this.getMapResponseEntity(fileName, url, StringKit.getUuid());
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
     * @param tagName 标签名称
     * @param fileName fileName
     * @return 对象名
     */
    private String buildObjectName(String gitlabCode, String projectId, String tagName, String fileName) {
        return gitlabCode + Const.SEP + projectId + Const.SEP + tagName + Const.SEP + fileName;
    }

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

    /**
     * 删除 Release 版本的接口.
     *
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @param token token
     * @param tagName tagName
     * @return Map信息
     */
    @DeleteMapping("/{projectId}/{tagName}")
    public ResponseEntity<Map<String, Object>> deleteRelease(
            @RequestParam("gitlabUrl") String gitlabUrl,
            @PathVariable("projectId") String projectId,
            @RequestParam("token") String token,
            @PathVariable("tagName") String tagName) {
        log.info("删除版本资源文件的信息 projectId: {}, tagName: {}.", projectId, tagName);
        // 先查询该版本是否存在，并得到对应的资源链接信息，然后从 MinIO 中删除这些资源.
        ReleaseInfo releaseInfo = new ReleaseInfo(gitlabUrl, projectId, token, tagName);
        String releaseJsonStr = releaseService.getReleaseByTagName(releaseInfo);
        JSONArray jsonArray = JSON.parseObject(releaseJsonStr).getJSONObject("assets").getJSONArray("links");
        if (jsonArray != null) {
            for (int i = 0, len = jsonArray.size(); i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                minioService.deleteObjectQuieylyByUrl(jsonObject.getString("url"));
            }
        }

        // 最后在 GitLab 中删除版本.
        releaseService.deleteRelease(releaseInfo);
        return ResponseEntity.ok(new HashMap<>(4));
    }

    /**
     * index.html 首页请求.
     *
     * @param file 上传的文件
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @return 字符串
     */
    @PostMapping("/{projectId}/{tagName}/assets/file")
    public ResponseEntity<Map<String, Object>> uploadReleaseAssets(
            @RequestParam("file") MultipartFile file,
            @RequestParam("gitlabUrl") String gitlabUrl,
            @PathVariable("projectId") String projectId,
            @RequestParam("token") String token,
            @PathVariable("tagName") String tagName,
            @RequestParam("fileName") String fileName) throws IOException {
        log.info("编辑版本上传资源文件的信息，gitlabUrl: {}, projectId: {}, tagName: {}.", gitlabUrl, projectId, tagName);

        // 上传对象到 MinIO 中，并返回 URL.
        String url = minioService.putObject(this.buildObjectName(this.getCodeByGitlabUrl(gitlabUrl),
                projectId, tagName, fileName), file.getInputStream());

        // 向 gitlab 中添加资源链接.
        String linkJsonStr = linkService.createLink(new LinkInfo(gitlabUrl, projectId, token, tagName)
                .setBaseLinkInfo(new BaseLinkInfo(fileName, url)));

        // 返回结果，上传控件必须要求必须返回 status 为 200 才算成功.
        // 添加资源连接成功之后，需要获取到资源连接ID，返回给前台.
        return this.getMapResponseEntity(fileName, url, JSON.parseObject(linkJsonStr).getString("id"));
    }

    /**
     * 返回上传时的前台参数.
     *
     * @param fileName fileName
     * @param url url
     * @param id id
     * @return Map
     */
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(String fileName,
            String url, String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("status", HttpStatus.OK.value());
        map.put("id", id);
        map.put("name", fileName);
        map.put("url", url);
        return ResponseEntity.ok(map);
    }

}
