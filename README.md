### 作业管理系统 （优化后的Java EE - Spring IOC） 

优化内容：
1. 使用IOC进行实例管理，在注解与xml配置中选择了xml, 在app-context.xml中注册bean,包括所有model下所有实例类(prototype)以及db下的DatabasePool(singleton)
2. 放弃自己写的单例DatabasePool,采用IOC管理,使用bean注册单例的DatabasePool(singleton)
![DatabasePool](screenshots/dbpool.png)
3. 在core下新建一个util包，写了一个实现ApplicationContextAware接口的SpringContextUtil类，并在app-context.xml中注册，方便获取ApplicationContext
![ApplicationContext](screenshots/context.png)
4. 使用：
![usage](screenshots/usage.png)
5. 配置相关(详见web模块中的app-servlet.xml)