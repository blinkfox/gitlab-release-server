<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>GitLab Release 版本服务</title>
    <link rel="stylesheet" href="lib/zui/css/zui.min.css">
    <link rel="stylesheet" href="lib/zui/lib/uploader/zui.uploader.min.css" />
    <link rel="stylesheet" href="lib/zui/css/doc.min.css">
    <link rel="stylesheet" href="css/index.css" />
</head>

<body>

<header id="header" class="bg-primary">
    <div class="navbar navbar-inverse" id="navbar" role="banner">
        <div class="container">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".zui-navbar-collapse">
                    <span class="sr-only">切换导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="/" class="navbar-brand"><span><i class="icon icon-tags icon-logo"></i></span> <span class="brand-title">Release</span></a>
            </div>
            <nav class="collapse navbar-collapse zui-navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a title="在 GitLab 上 Star 项目" href="https://github.com/blinkfox/gitlab-release-server" target="_blank">
                            <i class="icon icon-github"></i><span> Star</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
    <div id="headContainer">
        <div class="container">
            <div id="heading">
                <h1>Release 服务</h1>
                <p>这是一个用于发布和编辑 GitLab Releases 版本的服务，同时也支持上传和管理各个版本的资源文件。</p>
            </div>
        </div>
    </div>
</header>

<div id="start-release" class="container">
    <h1>发布新版本</h1>
    <div class="alert alert-info with-icon">
        <i class="icon-ok-sign"></i>
        <div class="content">请在如下表单中输入对应 GitLib 项目仓库你要发布的的版本信息，然后点击"发布版本"即可。</div>
    </div>
    <fieldset>
        <legend>项目信息</legend>
        <form id="project-form" class="form-horizontal">
            <div class="form-group">
                <label for="gitlabUrl" class="required col-sm-2">GitLab 地址</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="gitlabUrl" value="https://gitlab.com" placeholder="如：https://gitlab.com">
                </div>
                <label for="projectId" class="required col-sm-2">项目ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="projectId" placeholder="请正确填写 GitLab 的项目 ID">
                </div>
            </div>
            <div class="form-group">
                <label for="token" class="required col-sm-2">访问令牌</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="token" placeholder="可通过 API 访问的令牌（token）">
                </div>
                <div class="ref-help help-block">访问令牌（即：token）可以在 GitLab 的 <code>设置 -> 用户令牌</code> 页面中生成，
                    生成时请务必勾选 <code>API</code> 选项。</div>
            </div>
        </form>
    </fieldset>
    <fieldset>
        <legend>版本信息</legend>
        <form id="release-form" class="form-horizontal">
            <div class="form-group">
                <label for="name" class="required col-sm-2">版本标题</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="name" placeholder="如：v1.2.0重大版本更新">
                </div>
                <label for="tagName" class="required col-sm-2">标签名称</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="tagName" placeholder="发布版本的标签，如：v1.2.0">
                </div>
            </div>
            <div class="form-group">
                <label for="ref" class="col-sm-2">引用标记</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="ref" placeholder="可填写 commit SHA、标签名称或分支名称">
                </div>
                <div class="ref-help help-block">如果标签名称在 GitLab 仓库中不存在，则必须填写该值。
                    更多介绍请<a href="https://gitlab.com/help/api/releases/index.md#create-a-release" target="_blank">参考这里</a>。</div>
            </div>
            <div class="form-group">
                <label for="description" class="required col-sm-2">版本描述</label>
                <div class="col-sm-8">
                    <textarea id="description" rows="8" class="form-control" placeholder="请填写 Markdown 格式的版本描述信息。"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="assetsUploader" class="col-sm-2">上传资源</label>
                <div class="col-sm-8">
                    <div id='assetsUploader' class="uploader">
                        <div class="uploader-message text-center">
                            <div class="content"></div>
                            <button type="button" class="close">×</button>
                        </div>
                        <div class="uploader-files file-list file-list-lg" data-drag-placeholder="请拖拽文件到此处"></div>
                        <div class="uploader-actions">
                            <div class="uploader-status pull-right text-muted"></div>
                            <button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button>
                            <button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </fieldset>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
            <div id="form-tip" class="alert alert-warning hide">...</div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
            <button id="releaseBtn" class="btn btn-primary"><i class="icon icon-hand-up"></i> 发布新版本</button>
            <button id="queryEditBtn" class="btn btn-info"><i class="icon icon-edit"></i> 前往编辑某个版本</button>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <hr>
        <p class="text-muted small">欢迎使用 GitLab Release 版本发布服务。</p>
    </div>
</footer>

<script src="lib/jquery/jquery-1.11.0.min.js"></script>
<script src="lib/zui/js/zui.min.js"></script>
<script src="lib/zui/lib/uploader/zui.uploader.min.js"></script>
<script>
    $(function () {
        // 发布信息的全局变量.
        var gitlabUrl, token, projectId,
            name, tagName, ref, description;
        // 上传状态，发布状态.
        var uploading = false,
            isReleaseing = false;
        // 资源链接的数组集合.
        var uploadLinks = [];

        // 上传文件对象.
        var $uploader = $('#assetsUploader');

        function isBlank(str) {
            return !str || $.trim(str).length === 0;
        }

        /**
         * 校验表单填写是否完善.
         *
         * @returns 是否校验通过
         */
        function validForm() {
            // 获取最新的数据.
            gitlabUrl = $('#gitlabUrl').val();
            token = $('#token').val();
            projectId = $('#projectId').val();
            name = $('#name').val();
            tagName = $('#tagName').val();
            ref = $('#ref').val();
            description = $('#description').val();

            // 对表单的重要数据做校验检查.
            var $formTip = $('#form-tip');
            if (isBlank(gitlabUrl)) {
                $formTip.html('请填写有效的 GitLab 仓库地址，如：https://gitlab.com').removeClass('hide');
                return false;
            }
            if (isBlank(token)) {
                $formTip.html('请填写正确的 GitLab API 访问令牌（token），如：gDybLx3yrUK_HLp3qPjS').removeClass('hide');
                return false;
            }
            if (isBlank(projectId)) {
                $formTip.html('请填写 GitLab 项目仓库的 ID，如：253').removeClass('hide');
                return false;
            }
            if (isBlank(name)) {
                $formTip.html('请填写本次发布的版本标题，如："v1.2.0重大版本更新"').removeClass('hide');
                return false;
            }
            if (isBlank(tagName)) {
                $formTip.html('请填写本次发布的标签名称，如：v1.2.0').removeClass('hide');
                return false;
            }
            if (isBlank(description)) {
                $formTip.html('请使用 Markdown 语法格式来填写版本描述信息。').removeClass('hide');
                return false;
            }

            // 如果文件正在上传，也不能提交.
            if (uploading) {
                $formTip.html('有文件正在上传中，请稍候在提交发布！').removeClass('hide');
                return false;
            }

            $formTip.html('').addClass('hide');
            return true;
        }

        /**
         * 校验需要查询编辑表单填写的信息是否完善.
         *
         * @returns 是否校验通过
         */
        function validQueryEditForm() {
            // 获取最新的数据.
            gitlabUrl = $('#gitlabUrl').val();
            token = $('#token').val();
            projectId = $('#projectId').val();
            tagName = $('#tagName').val();

            // 对表单的重要数据做校验检查.
            var $formTip = $('#form-tip');
            if (!gitlabUrl) {
                $formTip.html('请填写有效的 GitLab 仓库地址，如：https://gitlab.com').removeClass('hide');
                return false;
            }
            if (!token || $.trim(token).length === 0) {
                $formTip.html('请填写正确的 GitLab API 访问令牌（token），如：gDybL237rUK_HLp3qPjS').removeClass('hide');
                return false;
            }
            if (!projectId) {
                $formTip.html('请填写 GitLab 项目仓库的 ID，如：253').removeClass('hide');
                return false;
            }
            if (!tagName) {
                $formTip.html('请填写本次发布的标签名称，如：v1.2.0').removeClass('hide');
                return false;
            }

            // 如果文件正在上传，也不能提交.
            if (uploading) {
                $formTip.html('有文件正在上传中，请稍候在提交发布！').removeClass('hide');
                return false;
            }

            $formTip.html('').addClass('hide');
            return true;
        }

        /**
         * 清空表单数据.
         */
        function clearFormData(formId) {
            $(':input', '#' + formId)
                .not(':button, :submit, :reset, :hidden')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
        }

        /**
         * 清空版本信息和状态信息.
         */
        function clearForm() {
            clearFormData('release-form');
            uploadLinks = [];
        }

        // 发布版本的按钮事件.
        $('#releaseBtn').on('click', function () {
            // 校验表单
            if (!validForm()) {
                return;
            }
            if (isReleaseing) {
                new $.zui.Messager('版本正在发布中，请稍候...', {
                    type: 'warning'
                }).show();
                return;
            }

            // 设置发版状态为true.
            isReleaseing = true;
            var loadingMsger = new $.zui.Messager('版本正在发布中，请稍候...', {
                icon: 'bell',
                time: 0
            });
            loadingMsger.show();

            // 发起 ajax 请求.
            var params = {
                gitlabUrl: gitlabUrl,
                token: token,
                projectId: projectId,
                name: name,
                tagName: tagName,
                ref: ref,
                description: description,
                assets: {links: uploadLinks}
            };
            $.ajax({
                type: 'POST',
                url: '/releases/',
                contentType:'application/json;charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(params),
                success: function(result) {
                    var successMsger = new $.zui.Messager('恭喜你，新版本【'+ tagName + '】已发布成功，请前往 GitLab 版本页查看新版本!', {
                        type: 'success'
                    });
                    successMsger.show();
                    clearForm();
                    isReleaseing = false;
                    loadingMsger.hide();
                },
                error: function() {
                    var errorMsger = new $.zui.Messager('发布失败！请检查你填写的版本发布的信息是否正确！', {
                        type: 'danger'
                    });
                    errorMsger.show();
                    isReleaseing = false;
                    loadingMsger.hide();
                }
            });
        });

        // 查询加载并编辑某个版本的数据.
        $('#queryEditBtn').on('click', function () {
            if (!validQueryEditForm()) {
                return;
            }
            location.href = '/releases/' + projectId + '/' + tagName + '?gitlabUrl=' + gitlabUrl + '&token=' + token;
        });

        /**
         * 移除文件.
         *
         * @param files 多个文件对象.
         */
        function removeFiles(files) {
            var uploader = $uploader.data('zui.uploader');
            for (var i = 0, len = files.length; i < len; i++) {
                uploader.removeFile(files[i]);
            }
        }

        // 上传资源文件的相关代码.
        $uploader.uploader({
            url: '/releases/assets/file',
            max_retries: 0,
            chunk_size: '5mb',
            onFilesAdded: function(files) {
                var $formTip = $('#form-tip');
                // 判断 gitlabUrl 是否为空.
                gitlabUrl = $('#gitlabUrl').val();
                if (isBlank(gitlabUrl)) {
                    $formTip.html('请填写有效的 GitLab 仓库地址，如：https://gitlab.com。').removeClass('hide');
                    removeFiles(files);
                    return false;
                }

                // 判断 projectId 是否为空.
                projectId = $('#projectId').val();
                if (isBlank(projectId)) {
                    $formTip.html('请填写 GitLab 项目仓库的 ID，如：253。').removeClass('hide');
                    removeFiles(files);
                    return false;
                }

                tagName = $('#tagName').val();
                if (isBlank(tagName)) {
                    $formTip.html('请填写本次发布的标签名称，如：v1.2.0').removeClass('hide');
                    removeFiles(files);
                    return false;
                }

                $formTip.html('').addClass('hide');
            },
            onBeforeUpload: function (file) {
                // 设置上传状态为true.
                uploading = true;

                // 设置上传参数.
                var multipart_params = $uploader.data('zui.uploader').plupload.settings.multipart_params;
                multipart_params.gitlabUrl = $('#gitlabUrl').val();
                multipart_params.projectId = $('#projectId').val();
                multipart_params.tagName = $('#tagName').val();
                multipart_params.fileName = file.name;
            },
            onFileUploaded: function (file, responseObject) {
                if (responseObject.status === 200) {
                    var result = JSON.parse(responseObject.response);
                    uploadLinks.push({name: result.name, url: result.url});
                }
            },
            onUploadComplete: function (files) {
                // 所有文件上传完毕，改变上传的状态.
                uploading = false;
            },
            onError: function () {
                $('#form-tip').html('有文件上传出错，请取消重新上传或联系管理员！').removeClass('hide');
            }
        });
    });
</script>
</body>
</html>