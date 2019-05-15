package com.asiainfo.mybatis.springboot.xml;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**   
 * @Description: springboot引入xml配置文件的方式，需要手动配置DatabaseIdProvider以支持多数据库
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午6:55:50
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@Configuration
public class DatabaseIdProviderConfigure {
    
    @Bean
    public VendorDatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider provider = new VendorDatabaseIdProvider();
        Properties prop = new Properties();
        prop.put("Oracle", "oracle");
        prop.put("MySQL", "mysql");
        provider.setProperties(prop);
        return provider;
    }
    
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, VendorDatabaseIdProvider databaseIdProvider) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setDatabaseIdProvider(databaseIdProvider);
        factory.setTypeAliasesPackage("com.asiainfo.mybatis.entity");
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/asiainfo/mybatis/xml/mapper/*.xml"));
        factory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return factory;
    }
}
