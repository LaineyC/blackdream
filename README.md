# BlackDream 文件生成器构建平台
<div>
    <div>
        <h4><b>项目介绍</b></h4>
        <p>网上的代码生成器形形色色五花八门，功能也很强大，甚至某些代码生成器能够一键生成项目。但是，这些代码生成器有一个致命的问题，它们都是成型的代码生成器，即已固定了生成规则。</p>
        <p><b>1.</b> 比如，代码生成器A只能生成Struts、Spring、Mybatis的代码，限制了用户的技术选型。</p>
        <p><b>2.</b> 比如，代码生成器B数据必须通过连接数据库来获取，限制了用户的数据灵活性。</p>
        <p><b>3.</b> 比如，代码生成器C生成的代码一定要分Controller层、Service层、Dao层，限制了用户的程序设计。</p>
        <p>上面的举例只是生成Java项目，C、C++、C#、Python？HTML、JavaScript？又或者说一定要拘泥于生成代码？</p>
        <p><b>BlackDream</b>是文件生成器构建平台，可快速灵活地构建和共享文件生成器。</p>
        <p><b>机械重复性的工作如同黑色梦魇，同一个问题并不应该被解决两次。</b></p>
    </div>
    <div>
        <h4><b>部署手册</b></h4>
        <p>服务端：依赖Java8，Tomcat8。</p>
        <p>配置文件：blackdream.properties。</p>
        <p>blackdream.username：系统root用户的用户名。</p>
        <p>blackdream.password：系统root用户的密码及新建系统用户时的默认密码。</p>
        <p>blackdream.datapath：系统数据存储的根路径。</p>
        <h4><b>使用手册</b></h4>
        <p>客户端：支持Chrome、Firefox、Opera浏览器，推荐Chrome。</p>
        <p>系统角色：root用户、开发者、使用者。</p>
        <p>root用户：拥有所有权限，有且只有一个root用户，开发者账号和使用者账号只能通过root账号新建。</p>
        <p>开发者：拥有开发和使用权限，开发生成器需掌握JAVA、JS、VTL、EL。</p>
        <p>使用者：只有使用权限，根据生成器规则输入数据生成目标文件。</p>
        <p><b>系统部署成功后，其余的文档在系统导航条-帮助-用户指南可翻阅。</b></p>
    </div>
</div>
