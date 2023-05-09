package com.matrictime.network.base.enums;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/24 0024 14:07
 * @desc
 */
public enum TimePickerEnum {
    TODAY("0","今天",0),
    THREE("3","近三天",2),
    SEVEN("7","近七天",6),
    THIRTY("30","近一个月",29),
    NINETY("90","近三个月",89),
    HUNDREDEIGHTY("180","近六个月",179),
    ;

    private String code;
    private String desc;
    private  Integer daysBefore;

    TimePickerEnum(String code, String desc, Integer daysBefore) {
        this.code = code;
        this.desc = desc;
        this.daysBefore = daysBefore;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(Integer daysBefore) {
        this.daysBefore = daysBefore;
    }
}
