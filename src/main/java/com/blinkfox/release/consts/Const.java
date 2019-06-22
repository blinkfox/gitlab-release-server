package com.blinkfox.release.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 常量类.
 *
 * @author chenjiayin on 2019-06-16.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Const {

    /**
     * 使用'/'来做分隔符，有些时候不用`File.separator`, 因为windows中用'\'时，在 Minio 中作为对象名会报错.
     */
    public static final String SEP = "/";

    /**
     * https 常量.
     */
    public static final String HTTPS = "https://";

    /**
     * http 常量.
     */
    public static final String HTTP = "http://";

}
