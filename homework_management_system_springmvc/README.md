### 作业管理系统 （优化后的Java EE - Spring MVC） 

优化内容：
1. 添加springmvc依赖，app-context.xml的srpingmvc配置文件，修改web.xml,添加相关配置，并注册app-context.xml文件
2. 删除servlet包，添加controller包 
3. 删除filter包，添加interceptor包,并在app-context.xml中配置

简要介绍：  
1. controller 下存放路由相关处理
2. interceptor 下存放相关拦截器