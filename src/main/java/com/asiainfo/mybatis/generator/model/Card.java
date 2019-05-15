package com.asiainfo.mybatis.generator.model;

import java.io.Serializable;
import java.util.Date;

public class Card implements Serializable {
    private String cardId;

    private String cardAgent;

    private Date authDate;

    private static final long serialVersionUID = 1L;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getCardAgent() {
        return cardAgent;
    }

    public void setCardAgent(String cardAgent) {
        this.cardAgent = cardAgent == null ? null : cardAgent.trim();
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }
}