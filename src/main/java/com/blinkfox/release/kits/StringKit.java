package com.blinkfox.release.kits;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.slf4j.helpers.MessageFormatter;

/**
 * 字符串工具类..
 *
 * @author chenjiayin on 2019/6/15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringKit {

    /**
     * 获取最新的UUID.
     *
     * @return 字符串
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @return 格式化后的字符串
     */
    public static String format(String pattern) {
        return pattern == null ? "" : pattern;
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @param args 参数
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

}
