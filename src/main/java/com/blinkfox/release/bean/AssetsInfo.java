package com.blinkfox.release.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源信息实体.
 *
 * @author blinkfox on 2019-06-15.
 */
@Getter
@AllArgsConstructor
public class AssetsInfo {

    /**
     * 多个资源链接信息的集合.
     */
    private List<LinkInfo> links;

}
