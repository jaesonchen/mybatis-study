package com.asiainfo.mybatis.springboot.xml;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.mybatis.entity.User;
import com.asiainfo.mybatis.xml.dao.UserDao;

/**
 * @Description: springboot 引入xml配置文件，mybatis-config.xml，com/asiainfo/mybatis/xml/mapper/*.xml
 *              - 由于没有引入spring-mybatis.xml，需要配置@MapperScan以扫描动态接口或者在每个接口上标注 @Mapper
 * @author chenzq  
 * @date 2019年5月2日 下午8:39:31
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved.
 */
@SpringBootApplication
@ComponentScan({"com.asiainfo.mybatis.springboot.xml", "com.asiainfo.mybatis.service"})
@MapperScan("com.asiainfo.mybatis.xml.dao")
@RestController
public class MybatisApplication {

    @Autowired
    UserDao dao;
    
    @RequestMapping("/user")
    List<User> listUser() {
        return this.dao.findAll();
    }
    
    @RequestMapping("/user/{id}")
    User getUser(@PathVariable("id") long userId) {
        return this.dao.findById(userId);
    }
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class<?>[] { MybatisApplication.class });
        app.setAdditionalProfiles(new String[] {"db"});
        app.run(args);
    }
}
