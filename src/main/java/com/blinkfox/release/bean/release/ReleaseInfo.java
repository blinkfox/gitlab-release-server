package com.blinkfox.release.bean.release;

import com.blinkfox.release.kits.StringKit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Release 版本信息.
 *
 * @author blinkfox on 2019-06-15.
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
public class ReleaseInfo extends ProjectInfo {

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
     * 构造方法.
     *
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @param token token
     * @param tagName tagName
     */
    public ReleaseInfo(String gitlabUrl, String projectId, String token, String tagName) {
        super.gitlabUrl = gitlabUrl;
        super.projectId = projectId;
        super.token = token;
        this.tagName = tagName;
    }

    /**
     * 拼接 release 的 URL 请求字符串，不含标签.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases`.</p>
     *
     * @return URL 字符串
     */
    public String getReleaseUrl() {
        return StringKit.format("{}/api/v4/projects/{}/releases", super.gitlabUrl, super.projectId);
    }

    /**
     * 拼接含 tagName 的 release URL 请求字符串，含标签.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases/v0.1`.</p>
     *
     * @return URL 字符串
     */
    public String getReleaseUrlWithTag() {
        return StringKit.format("{}/api/v4/projects/{}/releases/{}", super.gitlabUrl, super.projectId, this.tagName);
    }

}
