package com.blinkfox.release.bean;

import com.blinkfox.release.kits.StringKit;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * ReleaseInfo.
 *
 * @author blinkfox on 2019-06-15.
 */
@Setter
@Getter
@Accessors(chain = true)
public class ReleaseInfo {

    /**
     * GitLab 的主 URL 地址.
     */
    private String gitlabUrl;

    /**
     * 发送给 GitLab 需要的 token 值.
     */
    private String token;

    /**
     * GitLab 的项目 ID.
     */
    private String projectId;

    /**
     * 本次发布的 Release 标题名称.
     */
    private String name;

    /**
     * 本次发布的 Release 标签名称.
     */
    private String tagName;

    /**
     * 如果 tagName 不存在，将从本 ref 来创建该版本，它可以是 commit SHA、另一个标签名或者分支名.
     */
    private String ref;

    /**
     * 本次发布的 Release 描述信息.
     */
    private String description;

    /**
     * 本次发布的 release 的资源信息.
     */
    private AssetsInfo assets;

    /**
     * 拼接 release 的 URL 请求字符串，不含标签.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases`.</p>
     *
     * @return URL 字符串
     */
    public String getReleaseUrl() {
        return StringKit.format("{}/api/v4/projects/{}/releases", this.gitlabUrl, this.projectId);
    }

    /**
     * 拼接含 tagName 的 release URL 请求字符串，含标签.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases/v0.1`.</p>
     *
     * @return URL 字符串
     */
    public String getReleaseUrlWithTag() {
        return StringKit.format("{}/api/v4/projects/{}/releases/{}", this.gitlabUrl, this.projectId, this.tagName);
    }

}
