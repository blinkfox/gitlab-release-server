package com.blinkfox.release.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blinkfox.release.bean.release.ReleaseInfo;
import com.blinkfox.release.service.ReleaseService;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页根控制器.
 *
 * @author blinkfox on 2019-06-14.
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping
public class IndexController {

    @Resource
    private ReleaseService releaseService;

    /**
     * index.html 首页请求.
     *
     * @return 字符串
     */
    @GetMapping
    public ModelAndView index(ModelAndView modelView) {
        modelView.setViewName("index");
        return modelView;
    }

    /**
     * 编辑 Release 页的请求.
     *
     * @return 字符串
     */
    @GetMapping("/releases/{projectId}/{tagName}")
    public ModelAndView queryEdit(ModelAndView modelView,
            @RequestParam("gitlabUrl") String gitlabUrl,
            @PathVariable("projectId") String projectId,
            @RequestParam("token") String token,
            @PathVariable("tagName") String tagName) {
        ReleaseInfo releaseInfo = new ReleaseInfo(gitlabUrl, projectId, token, tagName);
        log.info("开始查找一个版本，查询的版本信息为：{}", releaseInfo);
        String jsonStr = releaseService.getReleaseByTagName(releaseInfo);
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        // 添加对应的返回填充的数据.
        modelView.addObject("gitlabUrl", gitlabUrl);
        modelView.addObject("projectId", projectId);
        modelView.addObject("token", token);
        modelView.addObject("tagName", tagName);
        modelView.addObject("name", jsonObject.getString("name"));
        modelView.addObject("description", jsonObject.getString("description"));
        JSONArray jsonArray = jsonObject.getJSONObject("assets").getJSONArray("links");
        modelView.addObject("links", jsonArray != null ? JSON.toJSONString(jsonArray) : "");

        modelView.setViewName("edit");
        return modelView;
    }

}
