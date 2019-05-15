package com.asiainfo.mybatis.springboot.annotation.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.asiainfo.mybatis.interceptor.MyInterceptor;

/**   
 * @Description: mybatis plugin 配置，替代mybatis-config.xml里的配置
 * 
 * @author chenzq  
 * @date 2019年5月10日 上午10:39:00
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@Configuration
public class MybatisInterceptorConfig {

    @Bean
    public MyInterceptor myInterceptor() {
        MyInterceptor myInterceptor = new MyInterceptor();
        Properties properties = new Properties();
        properties.setProperty("my.properties", "hello mybatis");
        myInterceptor.setProperties(properties);
        return myInterceptor;
    }
}
