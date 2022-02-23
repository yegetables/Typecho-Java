user表的authCode不知道在哪用到 created,activated,logged三个时间是unsigned ing不是datatime -->自己写typeHander解决

relationship的mapper.xml里面是 没有用懒加载 其他的纪念开启了懒加载但是equals的时候老师未加载出来,不得已把equalsAndHash改成了onlyInclude

## JSON

传json两种方式

1. 一种当作`application/x-www-form-urlencoded`类型的普通字符串解析 ---->使用@RequestParam
2. 另一种 使用 `application/json`类型 解析 @RequestBody

前端不指定Content-Type时，jq默认使用 `application/x-www-form-urlencoded`类型， 该编码方式会自动把`JSON数据转换成字符串`,使用 `@RequestParam` 可以接收

[Spring MVC获取不到post提交的multipart/form-data格式的数据](https://blog.csdn.net/qq_30038111/article/details/82885225)

```xml

<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

```xml

<bean id="multipartResolver"
      class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
```

[spring中使用@value注入static静态字符串变量](https://blog.csdn.net/qq_27579471/article/details/113360078)

使用@Accessors(fluent = true)
![img.png](https://tu.yegetables.com/images/2022/02/22/img.png)会导致fastjson的反序列化失效

https://xz.aliyun.com/t/7027

所以在基础上又手动写了get/set方法帮助序列化进行




用户登陆唯一token PropertiesConfig.getApplicationName() + ":user:" + newUser.uid()

![img](https://pic2.zhimg.com/v2-5a762dbe52ff1993221169c12dcb705f_r.jpg?source=1940ef5c)

https://www.zhihu.com/question/19786827