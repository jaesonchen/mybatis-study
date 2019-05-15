/**
 * mybatis 分页方式：
 * 1. 全量查询，在内存中分页
 * 2. sql分页，在mapper.xml中传入pageNum, pageSize, 在sql语句里通过 limit start, pageSize
 * 3. Interceptor plugin分页, 拦截StatementHandler的prepare方法，取出sql拼装 limit start, pageSize 实现分页
 *    PageHelper分页插件：
      PageHelper.startPage(pageNum, pageSize);
      List<?> pagelist = queryForList( xxx.class, "queryAll" , param);
 * 4. RowBounds分页, 数据量小时，RowBounds不失为一种好办法。但是数据量大时，实现拦截器就很有必要了。
 *    dao接口加入RowBounds参数:
      public List<UserBean> queryUsersByPage(String userName, RowBounds rowBounds);
      @Service
      public List<RoleBean> queryRolesByPage(String roleName, int start, int pageSize) {
          return roleDao.queryRolesByPage(roleName, new RowBounds(start, pageSize));
      }
 * 
 * 
 * 
 * pagehelper plugin:
 * 
 * 1. 在 pom.xml 中添加如下依赖：
    <dependency>
        <groupId>com.github.pagehelper</groupId>
        <artifactId>pagehelper</artifactId>
        <version>5.1.8</version>
    </dependency>
 * 
 * 2. 配置拦截器插件
 *  a. 在 MyBatis 配置 xml 中配置拦截器插件
    <!-- 
        plugins在配置文件中的位置必须符合要求，否则会报错，顺序如下:
        properties?, settings?, 
        typeAliases?, typeHandlers?, 
        objectFactory?,objectWrapperFactory?, 
        plugins?, 
        environments?, databaseIdProvider?, mappers? -->
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!-- 使用下面的方式配置参数，后面会有所有的参数介绍 -->
            <property name="param1" value="value1"/>
        </plugin>
    </plugins>
    
 *  b. 在 Spring 配置文件中配置拦截器插件
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <!--使用下面的方式配置参数，一行配置一个 -->
                        <value>params=value1</value>
                    </property>
                </bean>
            </array>
        </property>
    </bean>
 *  
 * 3. 分页插件参数介绍
 * 
 *  offsetAsPageNum：默认值为 false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为 true 时，会将 RowBounds 中的 offset 参数当成 pageNum 使用
 *  rowBoundsWithCount：默认值为false，该参数对使用 RowBounds 作为分页参数时有效。 当该参数设置为true时，使用 RowBounds 分页会进行 count 查询。
 *  pageSizeZero：默认值为 false，当该参数设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果（返回结果仍然是 Page 类型）。
 *  reasonable：分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。
 *  params：为了支持startPage(Object params)方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 
 *        - 可以配置 pageNum,pageSize,count,pageSizeZero,reasonable，不配置映射的用默认值， 
 *        - 默认值为pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero。
 *        
 * 4. 如何在代码中使用

    //第一种，RowBounds方式的调用
    List<Country> list = sqlSession.selectList("x.y.selectIf", null, new RowBounds(0, 10));
    
    //第二种，Mapper接口方式的调用，推荐这种使用方式。
    PageHelper.startPage(1, 10);
    List<Country> list = countryMapper.selectIf(1);
    
    //第三种，Mapper接口方式的调用，推荐这种使用方式。
    PageHelper.offsetPage(1, 10);
    List<Country> list = countryMapper.selectIf(1);
    
    //第四种，参数方法调用
    //存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
    public interface CountryMapper {
        List<Country> selectByPageNumSize(
                @Param("user") User user,
                @Param("pageNum") int pageNum, 
                @Param("pageSize") int pageSize);
    }
    //配置supportMethodsArguments=true
    //在代码中直接调用：
    List<Country> list = countryMapper.selectByPageNumSize(user, 1, 10);
    
    //第五种，参数对象
    //如果 pageNum 和 pageSize 存在于 User 对象中，只要参数有值，也会被分页
    //有如下 User 对象
    public class User {
        //其他fields
        //下面两个参数名和 params 配置的名字一致
        private Integer pageNum;
        private Integer pageSize;
    }
    //存在以下 Mapper 接口方法，你不需要在 xml 处理后两个参数
    public interface CountryMapper {
        List<Country> selectByPageNumSize(User user);
    }
    //当 user 中的 pageNum!= null && pageSize!= null 时，会自动分页
    List<Country> list = countryMapper.selectByPageNumSize(user);

    a. 例子一
    //获取第1页，10条内容，默认查询总数count
    PageHelper.startPage(1, 10);
    //紧跟着的第一个select方法会被分页
    List<Country> list = countryMapper.selectIf(1);
    assertEquals(2, list.get(0).getId());
    assertEquals(10, list.size());
    //分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
    assertEquals(182, ((Page) list).getTotal());

    b. 例子二
    //request: url?pageNum=1&pageSize=10
    //支持 ServletRequest,Map,POJO 对象，需要配合 params 参数
    PageHelper.startPage(request);
    //紧跟着的第一个select方法会被分页
    List<Country> list = countryMapper.selectIf(1);
    //后面的不会被分页，除非再次调用PageHelper.startPage
    List<Country> list2 = countryMapper.selectIf(null);
    //list1
    assertEquals(2, list.get(0).getId());
    assertEquals(10, list.size());
    //分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>，
    //或者使用PageInfo类（下面的例子有介绍）
    assertEquals(182, ((Page) list).getTotal());
    //list2
    assertEquals(1, list2.get(0).getId());
    assertEquals(182, list2.size());
    
    c. 例三，使用PageInfo的用法：
    //获取第1页，10条内容，默认查询总数count
    PageHelper.startPage(1, 10);
    List<Country> list = countryMapper.selectAll();
    //用PageInfo对结果进行包装
    PageInfo page = new PageInfo(list);
    //测试PageInfo全部属性
    //PageInfo包含了非常全面的分页属性
    assertEquals(1, page.getPageNum());
    assertEquals(10, page.getPageSize());
    assertEquals(1, page.getStartRow());
    assertEquals(10, page.getEndRow());
    assertEquals(183, page.getTotal());
    assertEquals(19, page.getPages());
    assertEquals(1, page.getFirstPage());
    assertEquals(8, page.getLastPage());
    assertEquals(true, page.isFirstPage());
    assertEquals(false, page.isLastPage());
    assertEquals(false, page.isHasPreviousPage());
    assertEquals(true, page.isHasNextPage());
 *        
 *        
 * @author chenzq  
 * @date 2019年5月10日 下午7:26:49
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
package com.asiainfo.mybatis.pagehelper;