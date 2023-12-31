package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/12/6 0006 15:59
 * @desc
 */
public enum SystemUserEnum {

    NMP_DATACOLLECT("nmp_datacollect","数据采集"),
    NMP_LOCAL("nmp_local","网管"),
    SYSTEM("system","系统");
    private String loginAccount;
    private String nickName;

    SystemUserEnum(String loginAccount, String nickName) {
        this.loginAccount = loginAccount;
        this.nickName = nickName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
