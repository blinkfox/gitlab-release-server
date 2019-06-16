package com.blinkfox.release.bean.release;

import com.blinkfox.release.kits.StringKit;

import lombok.Getter;
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
     * 链接名称.
     */
    private String name;

    /**
     * 链接的 URL.
     */
    private String url;

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
