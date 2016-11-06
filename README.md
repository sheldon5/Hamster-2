# Hamster-2
>* 仿照Hexo的JavaWeb实现的静态博客网站，除了后台管理，博文编写和少部分的动态页面以外，游客访问博客网站均为静态页面，这个项目设计的初衷就是为低配置的服务器建立一个高并发的博客网站，是Hamster项目的规范化重构而成，使用了SpringMVC+Spring+Mybatis对整个项目进行架构，前端采用bootstrap+jquery进行设计，整个界面简洁美观~

>* 该项目在后台的操作过程中对事务进行了严格的管理，采用c3p0连接池管理数据库连接，而网页静态化则是采用FreeMarker来实现，生成静态的html文件。在后台管理部分，编辑器采用了CKEditor和基于MarkDown实现的editor.md两种开源编辑器，可随意调换，图片上传方面，实现了三种不同的图片上传方式：CKEditor、Editormd和普通方式上传，都实现了图片上传和超链接返回的功能。

>* 本人网站地址：[http://www.coselding.cn](http://www.coselding.cn)

##先定义两个概念：
>* 动态网页：后台管理部分，博文编写，各种后台元素的增删改查的管理员才能访问的部分，以及少部分的游客访问部分，如RSS订阅、浏览、留言、喜爱、文章列表、搜索等操作为该网站的动态网页部分。
>* 静态网页：博客文章内容页面、主页等数据不易改变的部分设计为静态网页，并且，这些静态网页由于后台的编写导致的内容变化，其相关的其他网页也会自动进行相应的改变，这使得后台的维护更加方便，比如新增了一篇新文章，添加完成之后，静态主页也会相应的修改使其保持最新状态。

##技术选型
>* 前端：
jquery-2.2.1
bootstrap-3.3.6（很自然的有自适应~）

>* 后端：
mysql+c3p0+Mybatis+Spring+SpringMVC+JSP

>* 其他
日志系统：slf4j+logback
markdown：markdown4j
网页静态化模板引擎：freemarker
表单校验框架：hibernate-validator
项目构建工具：maven

##关于JavaWeb博客
>[https://github.com/b3log/solo](https://github.com/b3log/solo "进去solo开源项目")这个目前最火的Java开源博客项目也是基于JavaWeb，但是本人对于其自行造了个轮子Latke，采用json对象作为整个系统中的弱数据类型的JavaBean模型只能是望而生畏，还加了个自行实现的轻量级的Spring来作为Ioc容器，只能说是大牛的特立独行尔等渣渣所不能及也，我这个项目呢，整个架构和企业级的设计基本一致，省去了学习solo这个项目所需要额外的学习成本，毕竟大家都忙，哪有那么多空学这个不常用的东西。。。当然，他的想法以及对效率的提高效果也是很好的，说不定以后整个互联网趋势会朝这个方向发展。

##关于前端
>本人是Java后台开发的，前端水平实在是不敢恭维，做的页面也是那第三方的来改改的，要是有前端大神有兴趣可以尝试着改改这个项目的前端部分，后续会提供前端部分的修改开发教程。

##开发环境为
>IntelliJ 15.0.2+Tomcat9+mysql5.5+JDK8

本博客除了前台html+css+js使用了第三方框架以及使用的开源jar包框架，均为本人原创开发，版权归本人所有，开源协议为Apache License 2.0.

##主要功能
>* 博客文章增删改查、分类、静态化
>* 主页静态化，主页动静态两种模式
>* 数据库数据格式化
>* 开发者免登录模式
>* 管理员注册开关
>* 前端主题切换
>* 文章草稿
>* 留言板和留言板审核
>* 三种图片上传方式：CKEditor、editormd、通常方式
>* CKeditor和editormd两种编辑器任意切换
>* 博客订阅和邮件提醒
>* 文章喜爱和搜索
>* ajax刷新
>* 其他很多方便平时博客发布修改等的小功能


##效果浏览
>* 主页：

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/index.png)

>* 文章列表：

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/list.png)

>* 文章页面：

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/view-article.png)

>* 留言板：

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/comment.png)

>* RSS订阅

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/rss.png)

>* 关于我

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/contact.png)

>* 管理页面首页

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/manage.png)

>* 文章管理

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/article-manager.png)

>* 类别管理

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/category-manager.png)

>* 留言管理

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/comment-manager.png)

>* 客户管理

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/guest-manager.png)

>* 文章编辑1

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/article1.png)

>* 文章编辑2

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/article2.png)

>* 图片上传

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/image.png)

>* 模态框

![image](https://github.com/Coselding/Hamster-2/blob/master/screenshot/modal.png)

>* [使用教程](https://github.com/Coselding/Hamster/wiki/%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B "查看使用教程")
>* [前端开发教程](https://github.com/Coselding/Hamster/wiki/%E5%89%8D%E7%AB%AF%E5%BC%80%E5%8F%91%E6%95%99%E7%A8%8B "查看前端开发教程")
>* [更新记录](https://github.com/Coselding/Hamster/wiki/%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B "更新记录")
