package com.asiainfo.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月7日 下午2:57:03
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
@SuppressWarnings("serial")
public class Card implements Serializable {

    private String cardId;
    private String cardAgent;
    private Date authDate;
    
    public Card() {}
    public Card(String cardId, String cardAgent) {
        this.cardId = cardId;
        this.cardAgent = cardAgent;
        this.authDate = new Date();
    }
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCardAgent() {
        return cardAgent;
    }
    public void setCardAgent(String cardAgent) {
        this.cardAgent = cardAgent;
    }
    public Date getAuthDate() {
        return authDate;
    }
    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }
    @Override
    public String toString() {
        return "Card [cardId=" + cardId + ", cardAgent=" + cardAgent + ", authDate=" + authDate + "]";
    }
}
