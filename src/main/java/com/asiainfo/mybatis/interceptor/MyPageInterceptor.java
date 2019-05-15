package com.asiainfo.mybatis.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**   
 * @Description: 分页拦截实现，这里可以给sql添加 limit 分页参数，可以在service层调用dao前，往Threadlocal里设置pageNum/pageSize参数，如果需要计算总数，需要拦截Executor
 * 
    <plugins>
        <plugin interceptor="com.autumn.interceptor.MyPageInterceptor">
            <property name="pigeSize" value="10" />
            <property name="dbType" value="mysql" />
        </plugin>
    </plugins>
 * 
 * @author chenzq  
 * @date 2019年5月11日 下午7:57:16
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@Intercepts({ 
    @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MyPageInterceptor implements Interceptor {

    // 每页显示的条目数
    private int pageSize;
    // 数据库类型
    private String dbType;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        
        try {
            // 获取StatementHandler，默认是RoutingStatementHandler
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            // 获取statementHandler包装类
            MetaObject metaObjectHandler = SystemMetaObject.forObject(statementHandler);
            
            // 分离代理对象链
            while (metaObjectHandler.hasGetter("h")) {
                metaObjectHandler = SystemMetaObject.forObject(metaObjectHandler.getValue("h"));
            }
            while (metaObjectHandler.hasGetter("target")) {
                metaObjectHandler = SystemMetaObject.forObject(metaObjectHandler.getValue("target"));
            }
            
            // 获取查询接口映射的相关信息
            // MappedStatement mappedStatement = (MappedStatement) metaObjectHandler.getValue("delegate.mappedStatement");
            // statement对应的方法
            // String mapId = mappedStatement.getId();
            // 可以以dao方法名 来决定是否分页 
            // if (mapId.matches(".+ByPage$"))
            
            // 获取进行数据库操作时管理参数的handler
            // ParameterHandler parameterHandler = (ParameterHandler) metaObjectHandler.getValue("delegate.parameterHandler");
            // 获取请求时的参数
            // Map<String, Object> param = (Map<String, Object>) parameterHandler.getParameterObject();
            // currPage = (int) param.get("currPage");
            // pageSize = (int) param.get("pageSize");
            
            // 这里以是否使用了MyPageHelper.startPage来开启分页
            if (MyPageHelper.isInPage()) {
                // 获取真正的sql, 也可以通过statementHandler.getBoundSql().getSql() 直接获取
                String sql = (String) metaObjectHandler.getValue("delegate.boundSql.sql");
                // 构建分页功能的sql语句
                String limitSql = MyPageHelper.getPageSql(sql, this.dbType, this.pageSize);
                // 将构建完成的分页sql语句赋值给'delegate.boundSql.sql'，偷天换日
                metaObjectHandler.setValue("delegate.boundSql.sql", limitSql);
            }

            // 进入责任链的下一级
            return invocation.proceed();
        } finally {
            // 清除分页信息
            MyPageHelper.stopPage();
        }
    }

    @Override
    public Object plugin(Object target) {
        // 生成object对象的动态代理对象
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 如果项目中分页的pageSize是统一的，也可以在这里统一配置和获取，这样就不用每次请求都传递pageSize参数了。
        this.pageSize = Integer.valueOf(properties.getProperty("pigeSize", "10"));
        this.dbType = properties.getProperty("dbType", "mysql");
    }
}
