package com.blinkfox.release.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.blinkfox.release.bean.release.BaseLinkInfo;
import com.blinkfox.release.bean.release.LinkInfo;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * LinkService 的单元测试类.
 *
 * @author blinkfox on 2019-06-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LinkServiceTest extends GitlabServiceTest {

    @InjectMocks
    private LinkService linkService;

    @Mock
    private RestTemplate restTemplate;

    /**
     * 创建一个 LinkService 的真实对象.
     *
     * @return LinkService 实例
     */
    private LinkService createRealLinkService() {
        LinkService realLinkService = new LinkService();
        realLinkService.setRestTemplate(new RestTemplate());
        return realLinkService;
    }

    /**
     * 构建基础的 Link 项目信息.
     *
     * @return Release 信息
     */
    private LinkInfo buildBaseLinkInfo() {
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.setGitlabUrl(super.gitlabUrl);
        linkInfo.setToken(super.token);
        linkInfo.setProjectId(super.projectId);
        linkInfo.setTagName("v1.0.1");
        return linkInfo;
    }

    /**
     * 构造需要删除的 link 信息的实例.
     *
     * @return LinkInfo 实例
     */
    private LinkInfo buildLinkInfoWithId() {
        return this.buildBaseLinkInfo().setLinkId("5197");
    }

    /**
     * 构造需要新增的 link 信息的实例.
     *
     * @return LinkInfo 实例
     */
    private LinkInfo buildCreateLinkInfo() {
        return this.buildBaseLinkInfo().setBaseLinkInfo(new BaseLinkInfo("QQ地址", "https://qq.com"));
    }

    /**
     * 真实测试获取所有资源链接的 link 信息，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realGetAllLinks() {
        Assert.assertNotNull(this.createRealLinkService().getAllLinks(this.buildBaseLinkInfo()));
    }

    /**
     * 真实测试获取单个资源链接的 link 信息，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realGetLinkById() {
        Assert.assertNotNull(this.createRealLinkService().getLinkById(this.buildLinkInfoWithId()));
    }

    /**
     * 真实创建资源链接的 link 信息，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realCreateLink() {
        this.createRealLinkService().createLink(this.buildCreateLinkInfo());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void getAllLinks() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(""));
        linkService.getAllLinks(this.buildBaseLinkInfo());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void getLinkById() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(""));
        linkService.getLinkById(this.buildLinkInfoWithId());
    }

    /**
     * mock 测试创建一个链接 link 信息.
     */
    @Test
    public void createLink() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok(""));
        linkService.createLink(this.buildCreateLinkInfo());
    }

}
