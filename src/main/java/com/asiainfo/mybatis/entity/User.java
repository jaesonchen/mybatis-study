package com.asiainfo.mybatis.entity;

import java.io.Serializable;
import java.util.List;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午2:56:04
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private long userId;
    private String userName;
    private Card card;
    private List<Course> courses;
    
    public User() {}
    public User(String userName, Card card) {
        this.userName = userName;
        this.card = card;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", card=" + card + ", courses=" + courses + "]";
    }
}
