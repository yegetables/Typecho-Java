# Typecho-Java

## 技术栈

* Spring
* Spring MVC
* Mybatis
* thymeleaf
* [TODO] redis

## 组件选取

* Mysql 5.7.34
* JDK11
* tomcat 9.0.50
* redis 6.2.6
* nginx 1.18.0

## 开发原因

1. `typecho`的一些问题，主要是 因为 `typecho` 在 `2017`年发布`1.1`版本后不再发布正式版，同时因为是`php`开发，没有采用现阶段流行的`前后端分离`模式。（虽然在`typecho`
   的文档页写了不采用MVC的原因，但是本人不是十分认同，猜测开发者可能是开发理念不同，php语言受限 和 未考虑拓展性）
2. 因为本人学习了`spring MVC`之后，想要将原本的`typecho`和`Single`主题前后端分离，但因为`typecho`没有预留接口，故决定准备用java改写`typecho`
   ，提供支持MVC和前后端分离的完善的api文档。
3. 因为 本人博客多为`markdown`撰写并上传到`github`，`typecho`虽然插件支持`markdown`，但是只能打开后台手动编辑修改，修改文章时需要多处修改引来不变，所以我决定开发 支持git管理的`markdown`
   文章的博客系统。
4. 本人多年来喜好折腾博客，所以自己写了一个博客系统，主要是为了满足自定义的需求，同时为了练习新技术。

## 使用注意事项

1. 数据库配置

> 在`src\main\resources\`目录下将文件`db.properties.bak`更名为 `db.properties` 。并在文件中配置 数据库用户名，密码，ip，端口 等一些内容。

2. 邮件发信人配置

> 在`src\main\resources\`目录下将文件`mail.properties.bak`更名为 `mail.properties` 。并在文件中配置 邮箱的账号密码等。

3. 日志位置(可选)

> 在`src\main\resources\`目录下将文件`log4j2.xml`中 `第11行` 关于 `  <property name="basePath">D:\Typecho-Java\src\main\resources\log</property> `更改为 `${web:rootDir}/log`或 参考注释配置本机路径 。

## 参考资料

* [`typecho`](https://www.typecho.org/)
* [`Single`](https://github.com/Dreamer-Paul/Single)
* [`RuleApi`](https://github.com/buxia97/RuleApi)
