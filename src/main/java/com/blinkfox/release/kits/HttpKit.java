package com.blinkfox.release.kits;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

/**
 * Http 操作相关的工具类.
 *
 * @author blinkfox on 2019-06-16.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpKit {

    /**
     * 发送给 GitLab 的 token 的关键参数名常量.
     */
    private static final String TOKEN_KEY = "PRIVATE-TOKEN";

    /**
     * 构建发送 http 请求需要的通用 HttpHeaders 对象实例，需要设置 token 信息.
     *
     * @param token Gitlab API 需要的 token 信息
     * @return HttpHeaders 对象实例
     */
    public static HttpHeaders buildTokenHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(TOKEN_KEY, token);
        return headers;
    }

}
