### 作业管理系统 （优化后的Java EE - 02） 
##### csdn: [博客链接](https://blog.csdn.net/qq_41446852/article/details/104831251)
##### github page: [博客链接](https://kelekle.github.io/2020/03/17/JAVAEE-02%20%E5%81%9A%E4%B8%80%E4%B8%AA%E4%BD%9C%E4%B8%9A%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F%E2%80%94jsp+servlet+layui/)  
优化内容：
1. 改造为 Maven 项目
2. 采用 Project/module 模式
3. 使用 HikariCP 数据库连接池  

简要介绍：  
1. db存放数据库相关类以及数据库连接池单例类
2. core存放相关model实体类
3. web存放servlet, filter, jsp等
