package com.asiainfo.mybatis.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @Description: 非spring集成环境下构建SqlSessionFactory 工具，用于获取SqlSession
 * 
 * @author chenzq  
 * @date 2019年5月3日 下午5:41:07
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved.
 */
public class SqlSessionFactoryUtil {
    
	private static SqlSessionFactory factory;
	static {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml")) {
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    /**
     * @Description: 获取SqlSession
     * @author chenzq
     * @date 2019年5月3日 下午5:43:14
     * @return
     */
    public static SqlSession getSession() {
        return factory.openSession();
    }
    
    /**
     * @Description: 获取SqlSession
     * @author chenzq
     * @date 2019年5月3日 下午5:43:27
     * @param autoCommit true 表示创建的SqlSession对象在执行完SQL之后会自动提交事务
     *                   false 表示创建的SqlSession对象在执行完SQL之后不会自动提交事务，这时就需要我们手动调用sqlSession.commit()提交事务
     * @return
     */
	public static SqlSession getSqlSession(boolean autoCommit) {
		return factory.openSession(autoCommit);
	}
}
