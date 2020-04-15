### 作业管理系统 （优化后的Java EE - Spring MVC - IOC） 

优化内容：
1. 使用IOC进行实例管理, 将原来app-context中的bean定义都改为注解
2. 使用@Autowire在构造器中自动装配,包括所有model下所有实例类(prototype)以及db下的DatabasePool(singleton)和所有Db类
2. 放弃自己写的单例DatabasePool,采用IOC管理,使用bean注册单例的DatabasePool(singleton)
![DatabasePool](screenshots/dbpool.png)
3. 在core下新建一个util包，写了一个实现ApplicationContextAware接口的SpringContextUtil类，并在app-context.xml中注册，方便获取ApplicationContext
![ApplicationContext](screenshots/context.png)
4. 使用：
![usage](screenshots/usage.png)
5. 配置相关(详见web模块中的app-servlet.xml)
6. 将之前从req中取参改为@RequestParam