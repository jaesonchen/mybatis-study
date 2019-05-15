package com.asiainfo.mybatis.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.mybatis.entity.Card;
import com.asiainfo.mybatis.entity.User;
import com.asiainfo.mybatis.service.UserService;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午4:02:40
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
public class MybatisXmlApplication {

    public static void main(String[] args) {
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-datasource.xml", "spring-mybatis.xml"});
        UserService service = context.getBean(UserService.class);
        
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
        
        context.close();
    }
}
