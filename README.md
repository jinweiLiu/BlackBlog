# BlackBlog
基于springboot的个人博客



#### 记录项目中问题解决

1、获取插入文章和标签的ID

@Options(useGeneratedKeys = true,keyProperty = "id")

2、lomok引入后不能调用getter方法

需要在idea安装lombok插件并进行配置

3、生成唯一id

UUID的使用