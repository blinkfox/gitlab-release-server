# GitLab 版本管理服务 (gitlab-release-server)

[![HitCount](http://hits.dwyl.io/blinkfox/gitlab-release-server.svg)](https://github.com/blinkfox/gitlab-release-server) [Build Status](https://secure.travis-ci.org/blinkfox/gitlab-release-server.svg)](https://travis-ci.org/blinkfox/gitlab-release-server) ![Java Version](https://img.shields.io/badge/Java-%3E%3D%208-blue.svg)

> 这是一个基于 GitLab Release API 来发布和管理 GitLab Releases 版本的服务。

## 一、特性

- 简单方便、轻量级，无数据库环境依赖，使用 `MinIO` 来维护版本资源；
- 本服务基于 [GitLab Release API](http://gitlab.com/help/user/project/releases/index) 来实现对 GitLab 版本操作；
- 支持发布、编辑和删除版本的功能；
- 支持上传、更新和删除版本中的资源文件；

> **注意**：你发布版本的项目所在的 GitLab 版本必须是 `11.7` 及以上，低版本的 GitLab 并未提供该发布 Release 版本服务的 API 。你可以在你 GitLab 主地址后面加上 `/help` （如：`http://xxxx.com/help`）来查看你所使用的 GitLab 版本。

## 二、开始使用

进入本服务主页之后，将看到如下页面：

![release-index.png](http://static.blinkfox.com/20190626-release-index.png)

如果你想要发布一个版本，只需要填写页面表单即可，发布版本的表单信息项介绍如下：

### 1. 项目信息

- **GitLab 地址**：你项目所在的 GitLab 地址，表单默认是 `https://gitlab.com`，可以修改为自己项目所在的 GitLab 地址。但是请注意，你发布版本的项目所在的 GitLab 版本必须是 `11.7` 及以上，低版本的 GitLab 并未提供该发布 Release 版本服务的 API 。你可以在你 GitLab 主地址后面加上 `/help` （如：`http://xxxx.com/help`）来查看你所使用的 GitLab 版本。
- **项目ID**：可以在你 GitLab 的项目主页中找到你项目的**项目ID**，应该是一个数字。
- **访问令牌**：访问令牌（即：`token`）是你能操作 GitLab API 的一把“钥匙”，必须正确填写这个访问令牌，才能正常操作 GitLab API。你可以在 GitLab 的 `设置` -> `用户令牌` 页面中去生成令牌，（如：GitLab 中[生成 Token 的地址](http://gitlab.com/profile/personal_access_tokens)），且生成时请务必至少勾选 API 的复选框选项。当然，发布版本是一个很谨慎的操作，你也需要确定你是否真的可以发布版本。

### 2. 版本信息

- **版本标题**：本次发布的版本标题，如：`v1.2.0 重大版本更新`。
- **版本标签**：本次发布版本的标签名称，如：`v1.1.0`。如果 GitLab 项目仓库中已经存在了你填写的版本标签，就可以直接填写这个已经存在了的版本标签，也就不须要填写下面的“引用标记”字段了。
- **引用标记**：表示发布的版本的引用标记，如：`master`。如果 GitLab 项目仓库中不存在了你前面填写的版本标签，就须要填写该“引用标记”字段了，表示从哪里那打版本的标签、标记。所以，这个字段的值可以是 Git commit SHA、标签名称或者当前最新提交的分支名称。关于该字段的更多介绍请[参考这里](https://gitlab.com/help/api/releases/index.md#create-a-release)。
- **版本描述**：关于本次发布版本的描述信息，建议使用 `Markdown` 语法来填写该描述信息。
- **版本资源**：发布版本时，GitLab 会默认将源代码打包成`.zip`等资源文件留在版本记录中。但这些资源往往并不是咱们需要的发布的版本，所以你就可以上传你发布版本需要的资源文件包就行，从而这个资源包会记录在版本信息中。

### 3. 发布后的页面效果

我在 GitLab 上建立了一个的示例项目[ssdemo](https://gitlab.com/blinkfox/ssdemo)，来演示 GitLab 这个发布版本的功能，发布成功之后就可以在项目[版本页](https://gitlab.com/blinkfox/ssdemo/-/releases)中看到版本了，发布版本的效果图如下：

![release-demo.png](http://static.blinkfox.com/20190626-release-demo.png)

### 4. 编辑或删除某个版本

本服务支持再编辑或删除某个存在的版本信息，只需要在首页的表单中填写**GitLab 地址**、**项目ID**、**访问令牌**和版本**版本标签**四个字段，然后点击“前往编辑某个版本”按钮，页面就会跳转并加载该版本的信息，如下图所示：

![release-edit.png](http://static.blinkfox.com/20190626-release-edit.png)

编辑版本只可以编辑如下三项信息：

- **版本标题**
- **版本描述**
- **版本资源**

> **注意**：“版本标题”和“版本描述”两项，当编辑完成后需要点击“更新版本”按钮才能生效。而“版本资源”，在你删除或者上传新资源时，将会即时生效。

如果你想删除这个版本，点击“删除版本”按钮，会弹出确认删除框，确认删除之后就会从 GitLab 中删除这个版本。

## 三、版本变更记录

### v1.0.0 (2019-06-24)

- 新增了发布 GitLab Release 版本的功能；
- 新增了编辑 GitLab Release 版本的功能；
- 新增了 Release 版本资源文件的上传和删除等功能；
