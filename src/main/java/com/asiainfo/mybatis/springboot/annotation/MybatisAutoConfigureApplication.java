package com.asiainfo.mybatis.springboot.annotation;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.asiainfo.mybatis.entity.Card;
import com.asiainfo.mybatis.entity.User;
import com.asiainfo.mybatis.service.UserService;

/**   
 * @Description: springboot mybatis 全注解自动配置
 * 
 * @author chenzq  
 * @date 2019年5月9日 下午3:21:37
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@SpringBootApplication
@ComponentScan({"com.asiainfo.mybatis.springboot.annotation"})
@MapperScan("com.asiainfo.mybatis.springboot.annotation.dao")
public class MybatisAutoConfigureApplication {

    @Autowired
    UserService service;
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class<?>[] { MybatisAutoConfigureApplication.class });
        app.setAdditionalProfiles(new String[] {"db", "mybatis"});
        app.run(args);
    }
    
    @PostConstruct
    public void run() {
        
        System.out.println(service.deleteCard("220322197912233031"));
        System.out.println(service.deleteByName("czq"));
        
        Card card = new Card("220322197912233031", "chaoyang police");
        System.out.println(service.saveCard(card));
        // save
        User user = new User();
        user.setUserName("czq");
        user.setCard(card);
        service.save(user);
        System.out.println(user.getUserId());
        // find
        user = service.findById(user.getUserId());
        System.out.println(user);
        // update
        user.setUserName("czq111");
        System.out.println(service.update(user));
        // findAll
        System.out.println(service.findAll());
    }
}
