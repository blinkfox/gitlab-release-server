package com.blinkfox.release.service;

import com.blinkfox.release.bean.ReleaseInfo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 发布 Release 相关的 Service 服务类.
 *
 * @author blinkfox on 2019-06-15.
 */
@Slf4j
@Service
public class ReleaseService {

    /**
     * 发送给 GitLab 的 token 的关键参数名常量.
     */
    private static final String TOKEN_KEY = "PRIVATE-TOKEN";

    @Setter
    @Resource
    private RestTemplate restTemplate;

    /**
     * 创建发布一个 Release 版本.
     *
     * @param releaseInfo Release 信息对象
     */
    @SuppressWarnings("unchecked")
    public void createRelease(ReleaseInfo releaseInfo) {
        // 设置 headers.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(TOKEN_KEY, releaseInfo.getToken());

        // 设置发布 release 的相关参数.
        Map<String, Object> params = new HashMap<>();
        params.put("name", releaseInfo.getName());
        params.put("tag_name", releaseInfo.getTagName());
        params.put("ref", releaseInfo.getRef());
        params.put("description", releaseInfo.getDescription());
        params.put("assets", releaseInfo.getAssets());

        // 执行发布新版 release 的请求.
        ResponseEntity<String> response = restTemplate.postForEntity(releaseInfo.getCreateReleaseUrl(),
                new HttpEntity(params, headers), String.class);
        log.info("【发布新的 release 成功】响应结果: \n{}", response.getBody());
    }

    /**
     * 根据 release 信息中的 tagName 删除一个 release 版本.
     *
     * @param releaseInfo release 信息对象
     */
    public void deleteRelease(ReleaseInfo releaseInfo) {
        // 在 HttpHeaders 设置删除 release 所需要的 token.
        HttpHeaders headers = new HttpHeaders();
        headers.set(TOKEN_KEY, releaseInfo.getToken());

        // 执行删除 release 版本的请求.
        ResponseEntity<String> response = restTemplate.exchange(releaseInfo.getDeleteReleaseUrl(),
                HttpMethod.DELETE, new HttpEntity<>(null, headers), String.class);
        log.info("【删除 release 成功】响应结果: \n{}", response.getBody());
    }

}
