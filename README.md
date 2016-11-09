#BlackDream 文件生成器构建平台

##机械重复性的工作如同黑色梦魇，同一个问题并不应该被解决两次。

##项目介绍

网上的代码生成器形形色色五花八门，功能也很强大，甚至某些代码生成器能够一键生成项目。但是，这些代码生成器有一个致命的问题，它们都是成型的代码生成器，即已固定了生成规则。

* 比如，代码生成器A只能生成Struts、Spring、Mybatis的代码，限制了用户的技术选型。
* 比如，代码生成器B数据必须通过连接数据库来获取，限制了用户的数据灵活性。
* 比如，代码生成器C生成的代码一定要分Controller层、Service层、Dao层，限制了用户的程序设计。

上面的举例只是生成Java项目，C、C++、C#、Python？HTML、JavaScript？又或者说一定要拘泥于生成代码？
BlackDream是文件生成器构建平台，可快速灵活地构建和共享文件生成器。

####部署的应该是blackdream项目，sample项目是基于程序接口操作数据的示例。

BlackDream前期定位于公司内部服务或者个人使用，数据量不会很多，采用xml存储数据，不采用数据库。

##部署手册

* 服务端：依赖Java8，Tomcat8。
* 配置文件：blackdream.properties。<
* blackdream.username：系统root用户的用户名。
* blackdream.password：系统root用户的密码及新建系统用户时的默认密码。
* blackdream.datapath：系统数据存储的根路径。

##使用手册

* 客户端：支持Chrome、Firefox、Opera浏览器，推荐Chrome。
* 系统角色：root用户、开发者、使用者。
* root用户：拥有所有权限，有且只有一个root用户，开发者账号和使用者账号只能通过root账号新建。
* 开发者：拥有开发和使用权限，开发生成器需掌握JAVA、JS、VTL、EL。
* 使用者：只有使用权限，根据生成器规则输入数据生成目标文件。

###系统部署成功后，其余的文档在系统导航条-帮助-用户指南可翻阅。

##如何打造属于你的生成器

####进行登陆。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/login.png)

####进入首页。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/main.png)

####点击我的生成器，进入生成器管理界面。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generator-manage.png)

####新建生成器。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generator-create.png)

####生成器有三部分组成：数据模型、模板文件、生成策略。详细文档参见帮助-用户指南。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generator-manage2.png)

####新建数据模型，数据模型定义了生成器所需生成数据的数据类型结构，每个生成器可制定多个数据模型。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/dynamicModel-create.png)

####新建模板文件，模板文件定义了生成文件的模板，采用Velocity模板实现。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/template-create.png)

####新建生成策略，生成策略是一组标签集，定义了生成文件和生成文件夹的规则，标签中的属性采用EL表达式访问变量。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/templateStrategy-create.png)

####新建实例，每个生成器可以创建多个实例，一个实例一份数据。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generatorInstance-create.png)

####进入工作台输入生成器所需数据。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/dataModel-manage.png)

####点击工具按钮，选择自定义的生成策略。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/dataModel-manage1.png)

####弹出对话框，点击确定即可生成。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/dataModel-manage2.png)

####生成后下载文件即可获得目标代码。
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generatorInstance-run.png)

####此时再进入首页，就不是空空如也了
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/main1.png)

####另外生成器还可以导出来，满足一些如复制、备份的需求，有导出就有导入！！！
![image](https://github.com/LaineyC/blackdream/blob/master/blackdream/site/image/generator-export.png)