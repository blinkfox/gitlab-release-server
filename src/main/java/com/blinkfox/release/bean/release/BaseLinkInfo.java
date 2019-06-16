package com.blinkfox.release.bean.release;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源链接信息实体.
 *
 * @author blinkfox on 2019-06-15.
 */
@Getter
@AllArgsConstructor
public class BaseLinkInfo {

    /**
     * 链接名称.
     */
    private String name;

    /**
     * 链接的 URL.
     */
    private String url;

}
