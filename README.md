# mybatis-generator-tddl

- 基于mybatis generator自动生成ORM映射文件的一款界面工具，增加常用的plugin工具
   ![](https://raw.githubusercontent.com/suyin58/mybatis-generator-tddl/branch/generator-test/src/test/resources/image/mainui.jpg)
- 定制plugin
1. 字段注释工具，将表字段注释添加到属性上
2. Lombok插件，提供lombok的注解插件。
3. 唯一索引插件，提供基于unique key 的select、update、insert操作，便于分库分表情况下，不是使用id子增长主键的sql查询
4. upsert 存在即更新插件 
5. 分页插件
6. 批量插入插件
  ![](https://raw.githubusercontent.com/suyin58/mybatis-generator-tddl/branch/generator-test/src/test/resources/image/mapper.jpg)
- 执行方法
1. 界面启动
 - 执行generator-gui/src/main/java/MainUI.java的main方法
2. 单元测试启动
 - 执行generator-test/src/test/.../generator/MyBatisGeneratorTest.java