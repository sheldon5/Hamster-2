/**
 * Created by 宇强 on 2016/10/2 0002.
 */

/**
 * 模态对话框
 */
//确认模态框
function showComfirmDialog(title, body, ok) {
    $('#myComfirmLabel').html(title);
    $('#myComfirmBody').html(body);
    $('#btn_comfirm_ok').click(ok);
    $('#comfirmDialog').modal('show');
}

function dismissComfirmDialog() {
    $('#comfirmDialog').modal('hide')
}
//消息模态框
function showMsgDialog(body) {
    $('#myMsgLabel').html('消息提示');
    $('#myMsgBody').html(body);
    $('#msgDialog').modal('show');
}

//用户点击喜爱文章
function like(likeURL) {
    $.ajax({
        type: "GET",
        url: likeURL,
        success: function (art) {
            var looked = art['looked'];
            var likes = art['likes'];
            updateLookedAndLikes('looked', looked, 'likes', likes);
        }
    });
}
//更新页面信息
function updateLookedAndLikes(lookedID, looked, likesID, likes) {
    var lookedEle = document.getElementById(lookedID);
    lookedEle.innerHTML = "<span class='glyphicon glyphicon-eye-open'></span> " + looked + " 已阅";
    var likesEle = document.getElementById(likesID);
    likesEle.innerHTML = "<span class='glyphicon glyphicon-heart'></span> " + likes + " 喜爱";
}
//列表的like
function like1(likeURL, artid) {
    $.ajax({
        type: "GET",
        url: likeURL,
        success: function (art) {
            var looked = art['looked'];
            var likes = art['likes'];
            updateLookedAndLikes1('looked-' + artid, looked, 'likes-' + artid, likes);
        }
    });
}
//更新页面信息
function updateLookedAndLikes1(lookedID, looked, likesID, likes) {
    var lookedEle = document.getElementById(lookedID);
    lookedEle.innerHTML = "<span class='glyphicon glyphicon-eye-open'></span> " + looked;
    var likesEle = document.getElementById(likesID);
    likesEle.innerHTML = "<span class='glyphicon glyphicon-heart'></span> " + likes;
}

//留言校验
function submitComment(url) {
    var nickname = $('#nickname').val();
    var gemail = $('#gemail').val();
    var comcontent = $('#comcontent').val();
    if (nickname == null || nickname.length == 0) {
        showMsgDialog('昵称不能为空哦！');
        return false;
    }
    if (gemail == null || gemail.length == 0) {
        showMsgDialog('邮箱不能为空哦！');
        return false;
    }
    if (!/\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(gemail)) {
        showMsgDialog('邮箱格式不正确！');
        return false;
    }
    if (comcontent == null || comcontent.length == 0) {
        showMsgDialog('留言内容不能为空哦！');
        return false;
    }

    //ajax提交表单
    var option = {
        url: url,
        type: 'POST',
        dataType: 'json',
        headers: {"ClientCallMode": "ajax"}, //添加请求头部
        success: function (data) {
            //提取结果消息
            var state = data['state'];
            var message = data['message'];
            //显示消息
            if (state == 1) {
                //输入框重置
                $('#nickname').val('');
                $('#gemail').val('');
                $('#comcontent').val('');

                showMsgDialog(message);
            } else {
                showMsgDialog(message);
            }
        },
        error: function (data) {
            showMsgDialog("提交失败,请刷新后重试，错误信息：" + data);
        }
    };
    $('#form').ajaxSubmit(option);

    return false;
}

/**
 * 文章编辑页面脚本
 */
//添加类别
function addCategory(url) {
    $('#cnameId').val('');
    $('#btn_ok').click(function () {
        this.className = 'btn btn-primary disabled';
        var categoryName = $('#cnameId').val();
        if (categoryName == null || categoryName.trim().length <= 0) {
            showMsgDialog('文章类别不能为空哦！');
            return false;
        }

        //类别已存在
        var select = document.getElementById('cid');
        var options = select.options;
        for (var i = 0; i < options.length; i++) {
            if (options[i].innerHTML.trim() == categoryName.trim()) {
                showMsgDialog('你添加的类别已存在！');
                return false;
            }
        }
        //符合条件，进行请求,ajax
        $.ajax({
            url: url,
            type: "post",
            data: 'cname=' + categoryName,
            success: function (result) {
                var state = result['state'];
                var categories = result['categories'];
                //刷新页面数据
                var html = '';
                for (var i = 0; i < categories.length; i++) {
                    html += '<option value="' + categories[i]['cid'] + '">' + categories[i]['cname'] + '</option>';
                }
                //alert(html);
                $('#cid').html(html);

                //隐藏对话框
                $('#addCategoryDialog').modal('hide');
                if (state == 0) {
                    showMsgDialog("类别添加失败！！！");
                }
            },
            error: function (result) {
                $('#addCategoryDialog').modal('hide');
                showMsgDialog('出现错误，错误信息为：' + result);
            }
        });
    });
    $('#addCategoryDialog').modal('show');
    return false;
}
//文章编辑器切换
function editorChange(url, editor) {
    $('#btn_editor_ok').click(function () {
        var loadType = $('#load').val();
        var form = document.getElementById("form");
        form.action = url;
        document.getElementById("editor").value = editor;
        document.getElementById("loadType").value = loadType;
        form.submit();
        $('#editorDialog').modal('hide');
    });
    $('#editorDialog').modal('show');
    return false;
}
//文章编辑器切换
function changeToCKEditor(url) {
    showComfirmDialog('编辑器切换', '切换编辑器会把编辑器中的内容清空哦，请确保做好内容备份工作！', function () {
        var md = $('#md').val();
        alert(md);
        var srcEditor = 0;
        var destEditor = 1;
        $('#markdown').html();
        $.ajax({
            url: url,
            type: 'post',
            data: 'md=' + md + '&src=' + srcEditor + '&dest=' + destEditor,
            success: function (res) {
                var state = res['state'];
                var content = res['content'];
                if (state == 1) {
                    var html = '<div id="ckeditor"><textarea class="form-control" name="content" id="content">' + content + '</textarea></div>';
                    $('#editor-parent').html(html);
                    initCKEditor('/', 'content');
                    $('#btn-editor').html('MarkDown编辑器');
                    $('#btn-editor').click = function () {
                        changeToMarkdownr(url);
                    };
                } else {
                    showMsgDialog("网络错误，错误信息为：" + res['message']);
                }
            },
            error: function (res) {
                showMsgDialog("网络错误，错误信息为：" + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}
//文章编辑器切换
function changeToMarkdownr(url) {
    showComfirmDialog('编辑器切换', '切换编辑器会把编辑器中的内容清空哦，请确保做好内容备份工作！', function () {
        var content = $('#content').val();
        alert(content);
        var srcEditor = 1;
        var destEditor = 0;
        $('#markdown').html();
        $.ajax({
            url: url,
            type: 'post',
            data: 'md=' + content + '&src=' + srcEditor + '&dest=' + destEditor,
            success: function (res) {
                var state = res['state'];
                var content = res['content'];
                if (state == 1) {
                    var html = '<div id="markdown"> <textarea class="form-control" style="display:none;" name="md" id="md">' + content + '</textarea></div>';
                    $('#editor-parent').html(html);
                    initEditormd('markdown', '/');
                    $('#btn-editor').html('CKEditor编辑器');
                    $('#btn-editor').click = function () {
                        changeToCKEditor(url);
                    };
                } else {
                    showMsgDialog("网络错误，错误信息为：" + res['message']);
                }
            },
            error: function (res) {
                showMsgDialog("网络错误，错误信息为：" + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}
//自定义图片上传
function customerImageUpload() {
    //初始化对话框样式
    $('#customerUploadBody').html($('#uploadBefore').html());
    $('#uploadSuccessImage').attr('src', '');
    $('#uploadSuccessAddr').attr('value', '');
    //显示对话框
    $('#customerUploadDialog').modal('show');
    return false;
}
//ajax后台图片上传
function uploadCustomerImage(url) {
    var option = {
        url: url,
        type: 'POST',
        dataType: 'json',
        headers: {"ClientCallMode": "ajax"}, //添加请求头部
        success: function (data) {
            var state = data['state'];
            var msg = data['msg'];
            if (state == 1) {
                $('#uploadSuccessImage').attr('src', msg);
                $('#uploadSuccessAddr').attr('value', msg);
                $('#customerUploadBody').html($('#uploadSuccess').html());
            } else {
                showMsgDialog(msg);
            }
        },
        error: function (data) {
            showMsgDialog("上传失败,请刷新后重试，错误信息：" + data);
        }
    };
    $('#customerUploadForm').ajaxSubmit(option);
    return false;
}
//从数据库重新加载文章数据
function resetFromDatabase(url, contextPath) {
    showComfirmDialog("编辑器重置", "本操作将会把当前页面中的所有数据重置为当前文章在数据库中的数据，添加文章的将会清空页面数据，确定执行吗", function () {
        var method = $('#method').val();
        if (method == 'update') {
            $.ajax({//请求数据库重置
                url: url,
                type: 'post',
                data: 'artid=' + $('#artid').val() + '&editor=' + $('#editor').val(),
                success: function (article) {
                    $('#title').val(article['title']);
                    $('#cid').val(article['cid']);
                    $('#type').val(article['type']);
                    $('#time').val(article['showtime']);
                    $('#top').val(article['top']);
                    $('#meta').val(article['meta']);
                    if ($('#editor').val() == '1') {
                        CKEDITOR.instances['content'].setData(article['content']);
                    } else {
                        $('#md').val(article['md']);
                        initEditormd(contextPath);
                    }
                    showMsgDialog('重置成功！');
                },
                error: function (res) {
                    showMsgDialog('网络错误，请求失败，错误信息为：' + res);
                }
            });
        } else {//本地重置即可
            $('#artid').val('');
            $('#title').val('');
            $('#cid-default').attr('selected', true);
            $('#type').val('原创');
            $('#time').val('');
            $('#top').val('0');
            $('#meta').val('');
            if ($('#editor').val() == '1') {
                CKEDITOR.instances['content'].setData('');
            } else {
                $('#md').val('');
                initEditormd(contextPath);
            }
        }
        dismissComfirmDialog();
    });
    return false;
}
//Markdown图片上传参数
function getMarkDownOptions(contextPath) {
    return {
        width: "100%",
        height: 840,
        markdown: "",
        path: contextPath + '/md/lib/',
        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为 true
        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为 true
        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为 true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为 0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为 #fff
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: contextPath + "/manage/upload/markdown"
        /*
         上传的后台只需要返回一个 JSON 数据，结构如下：
         {
         success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
         message : "提示的信息，上传成功或上传失败及错误信息等。",
         url     : "图片地址"        // 上传成功时才返回
         }
         */
    };
}
//初始化CKEditor
function initCKEditor(contextPath) {
    CKEDITOR.replace('content', {
        // 设置宽高
        //width:900,
        height: 900,
        // 编辑器样式，有三种：'moono'（默认）、'office2003'
        skin: 'moono',
        // 背景颜色
        //uiColor:'#11AAAA',
        // 工具栏（基础'Basic'、全能'Full'、自定义）plugins/toolbar/plugin.js
        toolbar: 'Full',
        fullPage: false,//允许包含html标签
        //后台图片上传路径
        filebrowserUploadUrl: contextPath + "/manage/upload/ckeditor"
    });
}
//初始化editormd
function initEditormd(contextPath) {
    editormd('markdown', getMarkDownOptions(contextPath));
}

function articleSubmitCheck() {
    var showtime = $('#time').val();
    alert(showtime);
    if(showtime.length()>16){
        showtime = showtime.substring(0,16);
        alert(showtime);
        $('#time').val(showtime);
    }
    return true;
}

/**
 * 文章管理页面脚本
 */
function deleteArticleConfirm(title, url) {
    showComfirmDialog('文章删除确认', '您确认删除 ' + title + ' 这篇博文吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//静态化文章确认
function reloadArticle(title, url) {
    showComfirmDialog('静态化确认', '您确认重新静态化 ' + title + ' 这篇博文吗？', function () {
        $.ajax({
            url: url,
            type: 'get',
            success: function (res) {
                showMsgDialog(res['message']);
            },
            error: function (res) {
                showMsgDialog('网络错误，错误信息：' + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}
//静态化所有文章确认
function reloadAllArticles(url) {
    showComfirmDialog('静态化确认', '您确认重新静态化所有博文吗？', function () {
        $.ajax({
            url: url,
            type: 'get',
            success: function (res) {
                showMsgDialog(res['message']);
            },
            error: function (res) {
                showMsgDialog('网络错误，错误信息：' + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}
//格式化数据库确认
function fomatDatabase(url) {
    showComfirmDialog('数据库格式化确认', '您确认格式化数据库中的各个字段为标准格式吗？', function () {
        $.ajax({
            url: url,
            type: 'get',
            success: function (res) {
                showMsgDialog(res['message']);
            },
            error: function (res) {
                showMsgDialog('网络错误，错误信息：' + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}
//提交文章或草稿表单校验
function submitArticle() {
    var title = $('#title').val();
    var type = $('#type').val();
    var time = $('#time').val();
    if (title == null || title.length == 0) {
        showMsgDialog('文章标题不能为空！');
        return false;
    }
    if (type == null || type.length == 0) {
        showMsgDialog('文章类型不能为空！');
        return false;
    }
    if (!/[0-9]{1,4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}/.test(time)) {
        showMsgDialog('时间格式不正确！');
        return false;
    }
    return true;
}

/**
 * 类别管理界面脚本
 */
function deleteCategoryConfirm(url, cname) {
    showComfirmDialog('类别删除确认', '您确认删除 ' + cname + ' 这个类别吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//添加类别表单校验
function submitCategory() {
    var cname = $('#cname').val();
    if (cname == null || cname.length == 0) {
        showMsgDialog('类别名称不能为空哦！');
        return false;
    }
    return true;
}

/**
 * 留言管理界面脚本
 */
function deleteCommentConfirm(url, id) {
    showComfirmDialog('留言删除确认', '您确认删除 id=' + id + ' 这个留言吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//留言审核通过
function passComment(url, comid) {
    $('#btn_pass_ok').click(function () {
        var pass = $('#passD').val();
        $.ajax({
            url: url + '?comid=' + comid + '&pass=' + pass,
            type: 'post',
            success: function (res) {
                if (res['state'] == 1) {
                    var id = '#comid-' + comid;
                    $(id).html(res['resMsg']);
                    showMsgDialog(res['message']);
                } else {
                    showMsgDialog('留言设置失败！');
                }
            },
            error: function (res) {
                showMsgDialog('网络错误，错误信息为：' + res);
            }
        });
        $('#passDialog').modal('hide');
    });
    $('#passDialog').modal('show');
    return false;
}
//提交管理界面的留言表单验证
function submitManageComment() {
    var comcontent = $('#comcontent').val();
    var comtimeshow = $('#comtimeshow').val();
    if (comcontent == null || comcontent.length == 0) {
        showMsgDialog('留言内容不能为空哦！');
        return false;
    }
    if (!/[0-9]{1,4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}/.test(comtimeshow)) {
        showMsgDialog('时间格式不正确！');
        return false;
    }
    return true;
}
//留言管理自动截取内容长度，过长...显示
function shorten(comcontent, id) {
    if (comcontent.length() > 20) {
        id.html(comcontent.substr(0, 20) + "...");
    } else {
        id.html(comcontent);
    }
}
//留言管理界面获取完整的留言内容
function getFullComcontent(comid,contextPath) {
    $.ajax({
        url: contextPath+'/manage/comment/query?comid='+comid,
        type: 'get',
        success: function (res) {
            var status = res['status'];
            var result = res['result'];
            if(status['code']==0){
                showMsgDialog(result['comcontent']);
            }else{
                showMsgDialog(status['reason']);
            }
        },
        error:function (error) {
            showMsgDialog("网络错误："+error);
        }
    });
}

/**
 * 客户管理界面脚本
 */
function deleteGuestConfirm(url, gname) {
    showComfirmDialog('客户删除确认', '您确认删除 ' + gname + ' 这个客户吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//添加客户表单校验
function submitGuest() {
    var gname = $('#gname').val();
    var gemail = $('#gemail').val();
    if (gname == null || gname.length == 0) {
        showMsgDialog('客户昵称不能为空哦！');
        return false;
    }
    if (gemail == null || gemail.length == 0) {
        showMsgDialog('客户email不能为空哦！');
        return false;
    }
    if (!/\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(gemail)) {
        showMsgDialog('email格式不正确！');
        return false;
    }
    return true;
}

/**
 * 用户管理界面脚本
 */
function deleteUserConfirm(url, uname) {
    showComfirmDialog('用户删除确认', '您确认删除 ' + uname + ' 这个用户吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//添加用户表单校验
function submitUser() {
    var uname = $('#uname').val();
    var password = $('#password').val();
    var password2 = $('#password2').val();
    if (uname == null || uname.length == 0) {
        showMsgDialog('用户名不能为空哦！');
        return false;
    }
    if (!/[a-zA-Z0-9]{6,30}/.test(uname)) {
        showMsgDialog('用户名必须为6~30位的大小写字母数字！');
        return false;
    }
    if (password == null || password.length == 0) {
        showMsgDialog('密码不能为空哦！');
        return false;
    }
    if (!/[a-zA-Z0-9]{8,30}/.test(password)) {
        showMsgDialog('密码必须为8~30位的大小写字母数字！');
        return false;
    }
    if (password != password2) {
        showMsgDialog('两次密码输入不一致！');
        return false;
    }
    return true;
}
/**
 * 草稿页面脚本
 */
//删除草稿
function deleteDraftConfirm(title, url) {
    showComfirmDialog('草稿删除确认', '您确认删除 ' + title + ' 这份草稿吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}
//发布草稿
function deployDraft(title) {
    showComfirmDialog('草稿发布确认', '您确认发布 ' + title + ' 这份草稿吗？', function () {
        var title = $('#title').val();
        var type = $('#type').val();
        var time = $('#time').val();
        if (title == null || title.length == 0) {
            dismissComfirmDialog();
            showMsgDialog('文章标题不能为空！');
            return false;
        }
        if (type == null || type.length == 0) {
            dismissComfirmDialog();
            showMsgDialog('文章类型不能为空！');
            return false;
        }
        if (!/[0-9]{1,4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}/.test(time)) {
            dismissComfirmDialog();
            showMsgDialog('时间格式不正确！');
            return false;
        }

        var form = document.getElementById('form');
        $('#deploy').val('0');
        form.submit();
        dismissComfirmDialog();
    });
    return false;
}
//管理页面发布草稿
function deployDraft2(title, url) {
    showComfirmDialog('草稿发布确认', '您确认发布 ' + title + ' 这份草稿吗？', function () {
        window.location.href = url;
        dismissComfirmDialog();
    });
    return false;
}

/**
 * 管理界面网页头脚本
 */
function staticIndexComfirm(url) {
    showComfirmDialog('静态化确认', '您确认静态化主页吗？', function () {
        $.ajax({
            url: url,
            type: 'get',
            success: function (res) {
                showMsgDialog(res['message']);
            },
            error: function (res) {
                showMsgDialog('网络错误，错误信息：' + res);
            }
        });
        dismissComfirmDialog();
    });
    return false;
}

//主题切换
function theme(url, name) {
    showComfirmDialog("切换主题", '确认切换主题为 ' + name + ' 吗？', function () {
        $.ajax({
            url: url + '?name=' + name,
            type: 'get',
            success: function (res) {
                showMsgDialog(res['message']);
            },
            error: function (error) {
                showMsgDialog("网络错误，错误信息为：" + error);
            }
        });
        dismissComfirmDialog();
    });
}

//保存主题
function saveTheme() {
    $('#btn_ok').click(function () {
        //ajax提交表单
        var option = {
            dataType: 'json',
            headers: {"ClientCallMode": "ajax"}, //添加请求头部
            success: function (data) {
                //提取结果消息
                var state = data['state'];
                var message = data['message'];
                //显示消息
                if (state == 1) {
                    showMsgDialog(message);
                } else {
                    showMsgDialog(message);
                }
            },
            error: function (data) {
                showMsgDialog("提交失败,请刷新后重试，错误信息：" + data);
            }
        };
        $('#addThemeForm').ajaxSubmit(option);
        $('#addThemeDialog').modal('hide');
    });
    $('#addThemeDialog').modal('show');
    return false;
}
