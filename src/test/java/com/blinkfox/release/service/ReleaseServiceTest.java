package com.blinkfox.release.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.blinkfox.release.bean.link.BaseLinkInfo;
import com.blinkfox.release.bean.release.AssetsInfo;
import com.blinkfox.release.bean.release.ReleaseInfo;

import java.util.ArrayList;
import java.util.List;

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
 * ReleaseService 的单元测试类.
 *
 * @author blinkfox on 2019-06-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceTest extends GitlabServiceTest {

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
    private List<BaseLinkInfo> buildLinks() {
        List<BaseLinkInfo> links = new ArrayList<>(2);
        links.add(new BaseLinkInfo("my-ssdemo.zip", "https://gitlab.com/blinkfox/ssdemo"));
        links.add(new BaseLinkInfo("my-ssdemo.tar.gz", "https://baidu.com"));
        return links;
    }

    /**
     * 构建基础的 Release 信息.
     *
     * @return Release 信息
     */
    private ReleaseInfo buildBaseReleaseInfo() {
        ReleaseInfo releaseInfo = new ReleaseInfo();
        releaseInfo.setGitlabUrl(super.gitlabUrl);
        releaseInfo.setToken(super.token);
        releaseInfo.setProjectId(super.projectId);
        return releaseInfo;
    }

    /**
     * 构造含 tagName 的 release 信息的实例.
     *
     * @return ReleaseInfo 实例
     */
    private ReleaseInfo buildReleaseInfoWithTagName() {
        return this.buildBaseReleaseInfo().setTagName("v1.0.1");
    }

    /**
     * 构造发布 release 版本的相关参数.
     *
     * @return ReleaseInfo 实例
     */
    private ReleaseInfo buildReleaseInfo() {
        return this.buildBaseReleaseInfo()
                .setName("测试发布新版本 v1.1.0")
                .setTagName("v1.1.0")
                .setRef("master")
                .setDescription(CREATE_CHANGE_LOG)
                .setAssets(new AssetsInfo(this.buildLinks()));
    }

    /**
     * 构造需要更新的 release 信息的实例.
     *
     * @return ReleaseInfo 实例
     */
    private ReleaseInfo buildUpdateReleaseInfo() {
        return this.buildBaseReleaseInfo()
                .setTagName("v1.0.0")
                .setName("测试更新版本 v1.0.0")
                .setDescription("修改后的更新日志：\n\n> 这是修改后的更新日志。");
    }

    /**
     * 真实测试获取所有的 release，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realGetAllReleases() {
        Assert.assertNotNull(this.createRealReleaseService().getAllReleases(this.buildBaseReleaseInfo()));
    }

    /**
     * 真实测试获取单个 release 版本信息，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realGetReleaseByTagName() {
        Assert.assertNotNull(this.createRealReleaseService()
                .getReleaseByTagName(this.buildReleaseInfoWithTagName()));
    }

    /**
     * 真实测试发布一个新的 release，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realCreateRelease() {
        this.createRealReleaseService().createRelease(this.buildReleaseInfo());
    }

    /**
     * 真实测试更新 release 版本信息的功能，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realUpdateRelease() {
        this.createRealReleaseService().updateRelease(this.buildUpdateReleaseInfo());
    }

    /**
     * 真实测试删除 release 版本，用于真实测试时使用.
     */
    @Test
    @Ignore
    public void realDeleteRelease() {
        this.createRealReleaseService().deleteRelease(this.buildReleaseInfoWithTagName());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void getAllReleases() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("[{\"name\":\"测试版本 v1.1.3\", \"tag_name\":\"v1.1.3\"}]"));
        releaseService.getAllReleases(this.buildBaseReleaseInfo());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void getReleaseByTagName() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("{\"name\":\"测试版本 v1.0.2\", \"tag_name\":\"v1.0.2\"}"));
        releaseService.getReleaseByTagName(this.buildReleaseInfoWithTagName());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void createRelease() {
        when(restTemplate.postForEntity(anyString(), any(), any()))
                .thenReturn(ResponseEntity.ok("{\"name\":\"测试版本 v1.1.1\", \"tag_name\":\"v1.1.1\"}"));
        releaseService.createRelease(this.buildReleaseInfo());
    }

    /**
     * mock 测试更新已有的 release 版本信息.
     */
    @Test
    public void updateRelease() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("{\"name\":\"测试版本 v1.0.0\", \"tag_name\":\"v1.0.0\"}"));
        releaseService.updateRelease(this.buildUpdateReleaseInfo());
    }

    /**
     * mock 测试发布一个新的 release.
     */
    @Test
    public void deleteRelease() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("{\"name\":\"测试版本 v1.1.2\", \"tag_name\":\"v1.1.2\"}"));
        releaseService.deleteRelease(this.buildReleaseInfoWithTagName());
    }

}
