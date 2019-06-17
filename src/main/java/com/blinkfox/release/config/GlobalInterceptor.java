package com.blinkfox.release.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局拦截器.
 *
 * @author blinkfox on 2019-05-29.
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor {

    @Value("${system.baseUrl}")
    private String baseUrl;

    /**
     * 每个请求处理完毕之后，渲染之前执行的方法，都返回 baseUrl 信息.
     *
     * @param request 请求request
     * @param response 响应response
     * @param handler handler
     * @param modelView modelAndView实例
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelView) {
        if (modelView != null) {
            modelView.addObject("baseUrl", baseUrl);
        }
    }

}
