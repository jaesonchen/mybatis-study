/**   
 * MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。
 * MyBatis 允许使用插件来拦截的方法调用包括：
 *  Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 *  ParameterHandler (getParameterObject, setParameters)
 *  ResultSetHandler (handleResultSets, handleOutputParameters)
 *  StatementHandler (prepare, parameterize, batch, update, query)
 *  
 * 
 * @author chenzq  
 * @date 2019年5月11日 下午7:11:19
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
package com.asiainfo.mybatis.interceptor;