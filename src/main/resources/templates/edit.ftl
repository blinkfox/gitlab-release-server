<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>编辑存在的 Release 版本信息</title>
    <link rel="stylesheet" href="${baseUrl}/lib/zui/css/zui.min.css">
    <link rel="stylesheet" href="${baseUrl}/lib/zui/lib/uploader/zui.uploader.min.css" />
    <link rel="stylesheet" href="${baseUrl}/lib/zui/css/doc.min.css">
    <link rel="stylesheet" href="${baseUrl}/css/index.css" />
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
                <h1>Release 版本服务</h1>
                <p>这是一个用于发布和编辑 GitLab Releases 版本的服务，同时也支持上传和管理各个版本的资源文件。</p>
            </div>
        </div>
    </div>
</header>

<div id="start-release" class="container">
    <h1>编辑存在的版本</h1>
    <div class="alert alert-info with-icon">
        <i class="icon-info-sign"></i>
        <div class="content"><b>注</b>：编辑存在的版本信息，将只能编辑版本标题和版本描述信息，编辑完成后点击“更新版本”按钮生效。而上传的资源文件如果进行了上传或者删除，将会即时生效。</div>
    </div>
    <fieldset>
        <legend>项目信息</legend>
        <form id="project-form" class="form-horizontal">
            <div class="form-group">
                <label for="gitlabUrl" class="required col-sm-2">GitLab 地址</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="gitlabUrl" value="${gitlabUrl}" placeholder="如：https://gitlab.com" readonly>
                </div>
                <label for="projectId" class="required col-sm-2">项目ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="projectId" value="${projectId}" placeholder="请正确填写 GitLab 的项目 ID" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="token" class="required col-sm-2">访问令牌</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="token" value="${token}" placeholder="可通过 API 访问的令牌（token）" readonly>
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
                    <input type="text" class="form-control" id="name" value="${name}" placeholder="如：v1.2.0重大版本更新">
                </div>
                <label for="tagName" class="required col-sm-2">标签名称</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="tagName" value="${tagName}" placeholder="发布版本的标签，如：v1.2.0" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="description" class="required col-sm-2">版本描述</label>
                <div class="col-sm-8">
                    <textarea id="description" rows="8" class="form-control" placeholder="请填写 Markdown 格式的版本描述信息。">${description}</textarea>
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
            <button id="updateBtn" class="btn btn-primary"><i class="icon icon-hand-up"></i> 更新版本</button>
            <button id="deleteReleaseBtn" class="btn btn-danger"><i class="icon icon-edit"></i> 删除版本</button>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <hr>
        <p class="text-muted small">欢迎使用 GitLab Release 版本发布服务。</p>
    </div>
</footer>

<!-- 删除资源文件的对话框HTML. -->
<div id="delAssetsModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">标题</h4>
            </div>
            <div class="modal-body">
                <!-- 删除资源文件id的隐藏域. -->
                <input id="delFileId" type="hidden" />
                <input id="delFileUrl" type="hidden" />
                <p>确定要删除该发布资源吗？删除后将不可恢复！</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="delAssetBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 删除本 Release 版本的对话框. -->
<div id="delReleaseModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">标题</h4>
            </div>
            <div class="modal-body">
                <p>确定要删除本 Release 版本吗？删除后将不可恢复！</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="doDelReleaseBtn" type="button" class="btn btn-primary">确认删除</button>
            </div>
        </div>
    </div>
</div>

<script src="${baseUrl}/lib/jquery/jquery-1.11.0.min.js"></script>
<script src="${baseUrl}/lib/zui/js/zui.min.js"></script>
<script src="${baseUrl}/lib/zui/lib/uploader/zui.uploader.min.js"></script>
<script>
    $(function () {
        // 发布信息的全局变量.
        var gitlabUrl = '${gitlabUrl}', token = '${token}', projectId = '${projectId}',
            name, tagName = '${tagName}', ref, description;
        var removeFile;

        // 上传状态，发布状态.
        var uploading = false,
            releaseing = false;
        // 资源链接的数组集合.
        var uploadLinks = [];

        // 用于存放上传前后文件id的映射关系.
        var fileIdMap = new Map();

        // 初始化上传的页面资源.
        var releaseFiles = [];
        var linkStr = '${links}';
        var links = linkStr !== '' ? JSON.parse(linkStr) : [];
        for (var i = 0, len = links.length; i < len; i++) {
            var link = links[i];
            releaseFiles.push({id: link.id, name: link.name, url: link.url});
        }

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

        // 发布版本的按钮事件.
        $('#updateBtn').on('click', function () {
            // 校验表单
            if (!validForm()) {
                return;
            }
            if (releaseing) {
                new $.zui.Messager('版本正在发布中，请稍候...', {
                    type: 'warning'
                }).show();
                return;
            }

            // 设置发版状态为true.
            releaseing = true;
            var loadingMsger = new $.zui.Messager('版本信息正在更新中，请稍候...', {
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
                description: description
            };
            $.ajax({
                type: 'PUT',
                url: '${baseUrl}/releases/' + projectId + '/' + tagName,
                contentType:'application/json;charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(params),
                success: function(result) {
                    var successMsger = new $.zui.Messager('恭喜你，版本【'+ tagName + '】的信息已更新成功，可前往 GitLab 版本页查看。', {
                        type: 'success'
                    });
                    successMsger.show();
                    releaseing = false;
                    loadingMsger.hide();
                },
                error: function() {
                    var errorMsger = new $.zui.Messager('更新版本失败！请检查你填写的版本信息是否正确！', {
                        type: 'danger'
                    });
                    errorMsger.show();
                    releaseing = false;
                    loadingMsger.hide();
                }
            });
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
            url: '${baseUrl}/releases/${projectId}/${tagName}/assets/file',
            max_retries: 0,
            chunk_size: 0,
            staticFiles: releaseFiles,
            deleteActionOnDone: function(file, doRemoveFile) {
                $('#delFileId').val(fileIdMap.get(file.id) ? fileIdMap.get(file.id) : file.id);
                $('#delFileUrl').val(file.url);
                $('#delAssetsModal').modal({
                    keyboard: false,
                    show: true
                });
                removeFile = doRemoveFile;
            },
            onBeforeUpload: function (file) {
                // 设置上传状态为true.
                uploading = true;

                // 设置上传参数，包括文件名，后端获取的会有些问题.
                var uploader = $uploader.data('zui.uploader');
                var multipart_params = uploader.plupload.settings.multipart_params;
                multipart_params.gitlabUrl = $('#gitlabUrl').val();
                multipart_params.token = $('#token').val();
                multipart_params.fileName = file.name;
                uploader.showMessage('正在上传资源文件并将资源链接信息记录到 GitLab 中，请稍候...', 'info');
            },
            onFileUploaded: function (file, responseObject) {
                if (responseObject.status === 200) {
                    var result = JSON.parse(responseObject.response);
                    fileIdMap.set(file.id, result.id);
                }
                $uploader.data('zui.uploader').hideMessage();
            },
            onUploadComplete: function (files) {
                // 所有文件上传完毕，改变上传的状态.
                uploading = false;
            },
            onError: function () {
                $('#form-tip').html('有文件上传出错，请取消重新上传或联系管理员！').removeClass('hide');
            }
        });

        // 隐藏文件大小属性.
        $('#assetsUploader .file-static .file-size').hide();

        // 确认删除资源的操作.
        $('#delAssetBtn').on('click', function () {
            if (releaseing) {
                return;
            }
            releaseing = true;

            // 操作中提示.
            var loadingMsger = new $.zui.Messager('版本资源文件正在删除中，请稍候...', {
                icon: 'bell',
                time: 0
            });
            loadingMsger.show();

            // 发起删除资源文件的 ajax 请求.
            var url = '${baseUrl}/releases/' + $('#projectId').val() + '/' + $('#tagName').val() + '/links/'
                    + $('#delFileId').val() + '?gitlabUrl=' + $('#gitlabUrl').val() + '&token=' + $('#token').val()
                    + '&linkUrl=' + $('#delFileUrl').val();
            $.ajax({
                type: 'DELETE',
                url: url,
                contentType:'application/json;charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    // 成功就从页面删除文件资源.
                    removeFile();

                    $('#delAssetsModal').modal('hide', 'fit');
                    new $.zui.Messager('删除版本【'+ tagName + '】中的资源成功，可前往 GitLab 版本页查看。', {
                        type: 'success'
                    }).show();
                    releaseing = false;
                    loadingMsger.hide();
                },
                error: function() {
                    new $.zui.Messager('删除资源文件失败', {
                        type: 'danger'
                    }).show();
                    releaseing = false;
                    loadingMsger.hide();
                    $('#delAssetsModal').modal('hide', 'fit');
                }
            });
        });

        // 删除版本的按钮，将弹出确认模态框.
        $('#deleteReleaseBtn').on('click', function () {
            $('#delReleaseModal').modal({
                keyboard: false,
                show: true
            });
        });

        // 真正确认删除的按钮请求.
        $('#doDelReleaseBtn').on('click', function () {
            if (releaseing) {
                return;
            }
            releaseing = true;

            // 操作中提示.
            var loadingMsger = new $.zui.Messager('正在删除【'+ tagName + '】的 Release 版本，请稍候...', {
                icon: 'bell',
                time: 0
            });
            loadingMsger.show();

            var url = '${baseUrl}/releases/' + $('#projectId').val() + '/' + $('#tagName').val()
                    + '?gitlabUrl=' + $('#gitlabUrl').val() + '&token=' + $('#token').val();
            $.ajax({
                type: 'DELETE',
                url: url,
                contentType:'application/json;charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    // 成功跳转回首页.
                    loadingMsger.hide();
                    $('#delReleaseModal').modal('hide', 'fit');

                    new $.zui.Messager('删除【'+ tagName + '】的 Release 版本成功，3 秒钟后将返回首页。', {
                        type: 'success'
                    }).show();
                    setTimeout(function () {
                        location.href = '${baseUrl}';
                    }, 3000);
                },
                error: function() {
                    new $.zui.Messager('删除【'+ tagName + '】的 Release 版本失败，请稍候再试！', {
                        type: 'danger'
                    }).show();
                    releaseing = false;
                    loadingMsger.hide();
                    $('#delReleaseModal').modal('hide', 'fit');
                }
            });
        });

    });
</script>
</body>
</html>