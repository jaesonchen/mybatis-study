package com.asiainfo.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.mybatis.entity.Card;
import com.asiainfo.mybatis.entity.User;
import com.asiainfo.mybatis.xml.dao.UserDao;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午3:59:51
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User findById(long userId) {
        return dao.findById(userId);
    }

    @Override
    public int saveCard(Card card) {
        return dao.saveCard(card);
    }

    @Override
    public int save(User user) {
        return dao.save(user);
    }

    @Override
    public int update(User user) {
        return dao.update(user);
    }

    @Override
    public int delete(long userId) {
        return dao.delete(userId);
    }

    @Override
    public int deleteCard(String cardId) {
        return dao.deleteCard(cardId);
    }

    @Override
    public int deleteByName(String userName) {
        return dao.deleteByName(userName);
    }
}
