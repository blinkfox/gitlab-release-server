package com.blinkfox.release.service;

import com.blinkfox.release.bean.release.LinkInfo;
import com.blinkfox.release.kits.HttpKit;

import javax.annotation.Resource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 操作资源链接相关的服务类.
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

}
