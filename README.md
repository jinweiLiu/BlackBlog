# BlackBlog
基于springboot的个人博客



#### 记录项目中问题解决

1、获取插入文章和标签的ID

@Options(useGeneratedKeys = true,keyProperty = "id")

2、lomok引入后不能调用getter方法

需要在idea安装lombok插件并进行配置

3、生成唯一id

UUID的使用

4、合并多个List

```
List<Integer> list1 = new ArrayList<Integer>();
List<Integer> list2 = new ArrayList<Integer>();
List<Integer> listAll = new ArrayList<Integer>();
listAll.addAll(list1);
listAll.addAll(list2);
listAll = new ArrayList<Integer>(new LinkedHashSet<>(listAll));（去重）
```

5、使用ajax异步请求

controller要加上@ResponseBody注解

使用范例（记录一下）

```javascript
<script th:inline="javascript">    使用了Thymeleaf模板引擎
$(".like").click(function () {
    $.ajax({
        async: false,
        type:"POST",
        url:"/article/like",
        data: {articleId:[[${article.id}]]},
        dataType:"json",
        success:function (data) {
            $(".likeCount").html("赞 "+data);
            $(".testlike").html("<a style=\"color:#337ab7;\"><i class=\"fa fa-thumbs-o-up fa-3x\" aria-hidden=\"true\"></i></a>");
            $.cookie("articleLike",[[${article.id}]]);
        },
        error:function()
        {
            alert("获取数据出错!");
        },
    });
 });
</script>
```

6、刷新后cookie消失

1）设置cookie的路径为“/”

2）跨域问题

关于判断某个cookie是否存在，使用“==”判断会失效，要使用equal方法

7、GitHub授权登录

参考了码匠社区的相关代码，GitHub上有使用的详细说明。

引入了okhttp，是之前未接触到的