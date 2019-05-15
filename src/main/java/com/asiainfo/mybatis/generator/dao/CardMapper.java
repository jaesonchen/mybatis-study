package com.asiainfo.mybatis.generator.dao;

import com.asiainfo.mybatis.generator.model.Card;
import java.util.List;

public interface CardMapper {
    int deleteByPrimaryKey(String cardId);

    int insert(Card record);

    Card selectByPrimaryKey(String cardId);

    List<Card> selectAll();

    int updateByPrimaryKey(Card record);
}