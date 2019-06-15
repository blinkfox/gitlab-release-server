package com.blinkfox.release.service;

import com.blinkfox.release.bean.ReleaseInfo;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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

        ResponseEntity<String> request = restTemplate.postForEntity(releaseInfo.getCreateReleaseUrl(),
                new HttpEntity(params, headers), String.class);
        log.info("获取得结果: \n{}", request.getBody());
    }

}
