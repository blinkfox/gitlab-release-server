package com.blinkfox.release.bean.release;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * GitLab 项目信息.
 *
 * @author blinkfox on 2019-06-16.
 */
@Setter
@Getter
@Accessors(chain = true)
public class ProjectInfo {

    /**
     * GitLab 的主 URL 地址.
     */
    protected String gitlabUrl;

    /**
     * 发送给 GitLab 需要的 token 值.
     */
    protected String token;

    /**
     * GitLab 的项目 ID.
     */
    protected String projectId;

}
