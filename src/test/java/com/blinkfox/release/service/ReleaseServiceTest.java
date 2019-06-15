package com.blinkfox.release.service;

import com.blinkfox.release.bean.AssetsInfo;
import com.blinkfox.release.bean.LinkInfo;
import com.blinkfox.release.bean.ReleaseInfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * ReleaseService 的单元测试类.
 *
 * @author blinkfox on 2019-06-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceTest {

    /** 一个测试的 token. */
    private static final String TOKEN = "DETUw9jfL9E4mtUg8uBN";

    /** 更新日志. */
    private static final String CREATE_CHANGE_LOG = "更新日志：\n"
            + "\n"
            + "- 新增了 XXXX 功能；\n"
            + "- 新增了 YYYY 功能；\n"
            + "- 修复了 ZZZZ 的 bug；\n"
            + "\n"
            + "-[测试链接](https://baidu.com)";

    @InjectMocks
    private ReleaseService releaseService;

    @Mock
    private RestTemplate restTemplate;

    /**
     * 创建一个 ReleaseService 的真实对象.
     *
     * @return ReleaseService 实例
     */
    private ReleaseService createRealReleaseService() {
        ReleaseService realReleaseService = new ReleaseService();
        realReleaseService.setRestTemplate(new RestTemplate());
        return realReleaseService;
    }

    /**
     * 构造资源链接信息.
     *
     * @return 链接信息集合
     */
    private List<LinkInfo> buildLinks() {
        List<LinkInfo> links = new ArrayList<>(2);
        links.add(new LinkInfo("my-ssdemo.zip", "https://gitlab.com/blinkfox/ssdemo"));
        links.add(new LinkInfo("my-ssdemo.tar.gz", "https://baidu.com"));
        return links;
    }

    /**
     * 构造发布 release 版本的相关参数.
     *
     * @return ReleaseInfo 实例
     */
    private ReleaseInfo buildReleaseInfo() {
        return new ReleaseInfo()
                .setGitlabUrl("https://gitlab.com")
                .setToken(TOKEN)
                .setProjectId("5725437")
                .setName("测试版本 v1.1.0")
                .setTagName("v1.1.0")
                .setRef("master")
                .setDescription(CREATE_CHANGE_LOG)
                .setAssets(new AssetsInfo(this.buildLinks()));
    }

    /**
     * 真实测试发布一个新的 release，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void createRelease() {
        // 真实的执行发布新的 release 版本.
        this.createRealReleaseService().createRelease(this.buildReleaseInfo());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void createReleaseWithMock() {
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(ResponseEntity.ok("{\"name\":\"测试版本 v1.1.1\", \"tag_name\":\"v1.1.1\"}"));
        releaseService.createRelease(this.buildReleaseInfo());
    }

}
