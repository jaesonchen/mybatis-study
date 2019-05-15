package com.asiainfo.mybatis.xml.dao;

import java.util.List;

import com.asiainfo.mybatis.entity.Card;
import com.asiainfo.mybatis.entity.User;

/**   
 * @Description: dao接口
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午3:08:53
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
public interface UserDao {

    List<User> findAll();
    
    User findById(long userId);
    
    int saveCard(Card card);
    
    int save(User user);
    
    int update(User user);

    int delete(long userId);
    
    int deleteByName(String userName);
    
    int deleteCard(String cardId);
}
