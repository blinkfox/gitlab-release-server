package com.blinkfox.release.service;

import com.blinkfox.release.bean.release.ReleaseInfo;
import com.blinkfox.release.kits.HttpKit;

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
 * 操作 Release 相关的 Service 服务类.
 *
 * @author blinkfox on 2019-06-15.
 */
@Slf4j
@Service
public class ReleaseService {

    @Setter
    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据项目 Release 信息获取该项目所有的 Release 信息.
     *
     * @param releaseInfo 项目 Release 信息
     * @return 所有Release 信息
     */
    public String getAllReleases(ReleaseInfo releaseInfo) {
        ResponseEntity<String> response = restTemplate.exchange(releaseInfo.getReleaseUrl(), HttpMethod.GET,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(releaseInfo.getToken())), String.class);
        String resultJson = response.getBody();
        log.info("【获取所有 release 成功】响应结果: \n{}", resultJson);
        return resultJson;
    }

    /**
     * 根据项目和标签名称得到该标签对应的 Release 版本信息.
     *
     * @param releaseInfo 项目 Release 信息
     * @return 本标签名称对应的Release 信息
     */
    public String getReleaseByTagName(ReleaseInfo releaseInfo) {
        ResponseEntity<String> response = restTemplate.exchange(releaseInfo.getReleaseUrlWithTag(), HttpMethod.GET,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(releaseInfo.getToken())), String.class);
        String resultJsonStr = response.getBody();
        log.info("【获取单个 release 成功】响应结果: \n{}", resultJsonStr);
        return resultJsonStr;
    }

    /**
     * 创建发布一个 Release 版本.
     *
     * @param releaseInfo Release 信息对象
     */
    @SuppressWarnings("unchecked")
    public void createRelease(ReleaseInfo releaseInfo) {
        // 设置 headers.
        HttpHeaders headers = HttpKit.buildTokenHeaders(releaseInfo.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 设置发布 release 的相关参数.
        Map<String, Object> params = new HashMap<>(8);
        params.put("name", releaseInfo.getName());
        params.put("tag_name", releaseInfo.getTagName());
        params.put("ref", releaseInfo.getRef());
        params.put("description", releaseInfo.getDescription());
        params.put("assets", releaseInfo.getAssets());

        // 执行发布新版 release 的请求.
        ResponseEntity<String> response = restTemplate.postForEntity(releaseInfo.getReleaseUrl(),
                new HttpEntity(params, headers), String.class);
        log.info("【发布 release 成功】响应结果: \n{}", response.getBody());
    }

    /**
     * 更新 Release 版本信息.
     * <p>注：根据 tagName 只能更新 release 版本的发布名称(name)和描述信息(description).</p>
     *
     * @param releaseInfo Release 信息对象
     */
    public void updateRelease(ReleaseInfo releaseInfo) {
        // 设置更新 release 版本信息的相关参数.
        Map<String, Object> params = new HashMap<>(4);
        params.put("name", releaseInfo.getName());
        params.put("description", releaseInfo.getDescription());

        ResponseEntity<String> response = restTemplate.exchange(releaseInfo.getReleaseUrlWithTag(), HttpMethod.PUT,
                new HttpEntity<>(params, HttpKit.buildTokenHeaders(releaseInfo.getToken())), String.class);
        log.info("【更新 release 成功】响应结果: \n{}", response.getBody());
    }

    /**
     * 根据 release 信息中的 tagName 删除一个 release 版本.
     *
     * @param releaseInfo release 信息对象
     */
    public void deleteRelease(ReleaseInfo releaseInfo) {
        ResponseEntity<String> response = restTemplate.exchange(releaseInfo.getReleaseUrlWithTag(), HttpMethod.DELETE,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(releaseInfo.getToken())), String.class);
        log.info("【删除 release 成功】响应结果: \n{}", response.getBody());
    }

}
