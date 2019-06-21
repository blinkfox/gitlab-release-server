package com.blinkfox.release.bean.release;

import com.blinkfox.release.bean.link.BaseLinkInfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 资源信息实体.
 *
 * @author blinkfox on 2019-06-15.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetsInfo {

    /**
     * 多个资源链接信息的集合.
     */
    private List<BaseLinkInfo> links;

}
