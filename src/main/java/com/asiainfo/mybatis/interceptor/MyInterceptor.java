package com.asiainfo.mybatis.interceptor;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

/**
 * @Description: 拦截sql执行，统计时间
 * 
 * @author       zq
 * @date         2017年9月3日  下午12:33:28
 * Copyright: 	  北京亚信智慧数据科技有限公司
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }),
		@Signature(type = StatementHandler.class, method = "update", args = { Statement.class }) })
public class MyInterceptor implements Interceptor {

	Properties properties;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
	    StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
	    BoundSql boundSql = statementHandler.getBoundSql();
		System.out.println("执行SQL : " + boundSql.getSql());

		long beginTime = System.currentTimeMillis();
		Object result = invocation.proceed();
		System.out.println("执行时间 : " + (System.currentTimeMillis() - beginTime) + " ms");
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println("------> " + properties.getProperty("my.properties"));
		this.properties = properties;
	}

}
