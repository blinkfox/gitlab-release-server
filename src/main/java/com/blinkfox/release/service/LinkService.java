package com.blinkfox.release.service;

import com.blinkfox.release.bean.release.LinkInfo;
import com.blinkfox.release.kits.HttpKit;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 操作资源链接 link 相关的 service 服务类.
 *
 * @author blinkfox on 2019-06-16.
 */
@Slf4j
@Service
public class LinkService {

    @Setter
    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据项目 Release 信息获取该项目所有的 Release 信息.
     *
     * @param linkInfo release 版本的链接信息
     * @return 版本的所有 links 信息
     */
    public String getAllLinks(LinkInfo linkInfo) {
        ResponseEntity<String> response = restTemplate.exchange(linkInfo.getLinkUrl(), HttpMethod.GET,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(linkInfo.getToken())), String.class);
        String resultJson = response.getBody();
        log.info("【获取所有 link 成功】响应结果: \n{}", resultJson);
        return resultJson;
    }

    /**
     * 根据项目 Release 信息获取该项目所有的 Release 信息.
     *
     * @param linkInfo release 版本的链接信息
     * @return 版本的所有 links 信息
     */
    public String getLinkById(LinkInfo linkInfo) {
        ResponseEntity<String> response = restTemplate.exchange(linkInfo.getLinkUrlWithId(), HttpMethod.GET,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(linkInfo.getToken())), String.class);
        String resultJson = response.getBody();
        log.info("【获取单个 link 成功】响应结果: \n{}", resultJson);
        return resultJson;
    }

    /**
     * 创建新增或更新 Link 信息需要的参数 Map.
     *
     * @param linkInfo 链接信息
     * @return map
     */
    private Map<String, Object> createLinkParamsMap(LinkInfo linkInfo) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("name", linkInfo.getBaseLinkInfo().getName());
        params.put("url", linkInfo.getBaseLinkInfo().getUrl());
        return params;
    }

    /**
     * 创建发布一个新的资源链接 link 信息.
     *
     * @param linkInfo 链接信息
     */
    public void createLink(LinkInfo linkInfo) {
        ResponseEntity<String> response = restTemplate.exchange(linkInfo.getLinkUrl(), HttpMethod.POST,
                new HttpEntity<>(this.createLinkParamsMap(linkInfo), HttpKit.buildTokenHeaders(linkInfo.getToken())),
                String.class);
        log.info("【创建 link 成功】响应结果: \n{}", response.getBody());
    }

    /**
     * 更新一个已有的资源链接 link 信息.
     *
     * @param linkInfo 链接信息
     */
    public void updateLink(LinkInfo linkInfo) {
        ResponseEntity<String> response = restTemplate.exchange(linkInfo.getLinkUrlWithId(), HttpMethod.PUT,
                new HttpEntity<>(this.createLinkParamsMap(linkInfo), HttpKit.buildTokenHeaders(linkInfo.getToken())),
                String.class);
        log.info("【更新 link 成功】响应结果: \n{}", response.getBody());
    }

    /**
     * 删除一个已有的资源链接 link 信息.
     *
     * @param linkInfo 链接信息
     */
    public void deleteLink(LinkInfo linkInfo) {
        ResponseEntity<String> response = restTemplate.exchange(linkInfo.getLinkUrlWithId(), HttpMethod.DELETE,
                new HttpEntity<>(null, HttpKit.buildTokenHeaders(linkInfo.getToken())), String.class);
        log.info("【删除 link 成功】响应结果: \n{}", response.getBody());
    }

}
