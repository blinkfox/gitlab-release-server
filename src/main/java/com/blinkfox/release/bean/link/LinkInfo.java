package com.blinkfox.release.bean.link;

import com.blinkfox.release.bean.release.ProjectInfo;
import com.blinkfox.release.kits.StringKit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Release 版本资源链接信息.
 *
 * @author blinkfox on 2019-06-16.
 */
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class LinkInfo extends ProjectInfo {

    /**
     * 标签名称.
     */
    private String tagName;

    /**
     * 资源链接ID（当新增时不存在）.
     */
    private String linkId;

    /**
     * 基础链接信息，主要包括名称和URL.
     */
    private BaseLinkInfo baseLinkInfo;

    /**
     * 构造方法.
     *
     * @param gitlabUrl gitlabUrl
     * @param projectId projectId
     * @param token token
     * @param tagName tagName
     */
    public LinkInfo(String gitlabUrl, String projectId, String token, String tagName) {
        super.gitlabUrl = gitlabUrl;
        super.projectId = projectId;
        super.token = token;
        this.tagName = tagName;
    }

    /**
     * 获取拼接的资源链接 link 的 URL 请求字符串，不含链接ID（linkId）.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases/v0.1/assets/links`.</p>
     *
     * @return URL 字符串
     */
    public String getLinkUrl() {
        return StringKit.format("{}/api/v4/projects/{}/releases/{}/assets/links",
                super.gitlabUrl, super.projectId, this.tagName);
    }

    /**
     * 获取拼接的资源链接 link 的 URL 请求字符串，含链接ID.
     * <p>URL 如：`http://localhost:3000/api/v4/projects/24/releases/v0.1/assets/links/1`.</p>
     *
     * @return URL 字符串
     */
    public String getLinkUrlWithId() {
        return getLinkUrl() + "/" + this.linkId;
    }

}
