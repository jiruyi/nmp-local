package com.matrictime.network.api.request;

import java.io.Serializable;

public class RecallRequest extends BaseReq implements Serializable {
    private String agree;

    private String refuse;

    private String userId;

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
