package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class ChangePasswdReq implements Serializable {

    private static final long serialVersionUID = -2059854301986651992L;

    /**
     * 电话号码
     */
    private String phoneNumber;

    private String newPassword;

    private String repeatPassword;

    private String idNumber;

}
