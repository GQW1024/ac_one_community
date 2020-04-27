## 问答社区 <br/>
## 资料 <br/>
[Spring文档](https://spring.io/guides) <br/>
[SpringWEB文档](https://spring.io/guides/gs.serving-wed-content/) <br/>
[对标社区网站](https://elasticsearch.cn/explore) <br/>
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Bootstrap](https://v3.bootcss.com/getting-started/)
[Github OAuth文档](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
[Thymeleaf官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attrubute-value)
[Spring拦截器官方文档](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)
[Markdown 插件](http://editor.md.ipandao.com/)   
## 工具 <br/>
[Git](https://git-scm.com/download) <br/>
[Visual Paradigm](https://www.visual-paradigm.com)<br/>
IntelliJ IDEA <br/>
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
[Lombox](https:www.projectlombox.org)
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
## 脚本

```bash
SQL脚本：执行 mvn flyway:migrate 命令
mvn mybatis-generator:generate   MyBatis Generator生成命令，不会覆盖原有同名文件
ps:在使用覆盖生成命令之前，一定要记得把所有的Mapper删干净，防止出现：...with such name already exists  错误导致服务器启动失败
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate  MyBatis Generator生成命令，覆盖原有同名文件，
```