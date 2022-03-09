package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplSignalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplSignalExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNull() {
            addCriterion("device_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNotNull() {
            addCriterion("device_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdEqualTo(String value) {
            addCriterion("device_id =", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotEqualTo(String value) {
            addCriterion("device_id <>", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThan(String value) {
            addCriterion("device_id >", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThanOrEqualTo(String value) {
            addCriterion("device_id >=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThan(String value) {
            addCriterion("device_id <", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThanOrEqualTo(String value) {
            addCriterion("device_id <=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLike(String value) {
            addCriterion("device_id like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotLike(String value) {
            addCriterion("device_id not like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIn(List<String> values) {
            addCriterion("device_id in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotIn(List<String> values) {
            addCriterion("device_id not in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdBetween(String value1, String value2) {
            addCriterion("device_id between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotBetween(String value1, String value2) {
            addCriterion("device_id not between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andSignalNameIsNull() {
            addCriterion("signal_name is null");
            return (Criteria) this;
        }

        public Criteria andSignalNameIsNotNull() {
            addCriterion("signal_name is not null");
            return (Criteria) this;
        }

        public Criteria andSignalNameEqualTo(String value) {
            addCriterion("signal_name =", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameNotEqualTo(String value) {
            addCriterion("signal_name <>", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameGreaterThan(String value) {
            addCriterion("signal_name >", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameGreaterThanOrEqualTo(String value) {
            addCriterion("signal_name >=", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameLessThan(String value) {
            addCriterion("signal_name <", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameLessThanOrEqualTo(String value) {
            addCriterion("signal_name <=", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameLike(String value) {
            addCriterion("signal_name like", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameNotLike(String value) {
            addCriterion("signal_name not like", value, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameIn(List<String> values) {
            addCriterion("signal_name in", values, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameNotIn(List<String> values) {
            addCriterion("signal_name not in", values, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameBetween(String value1, String value2) {
            addCriterion("signal_name between", value1, value2, "signalName");
            return (Criteria) this;
        }

        public Criteria andSignalNameNotBetween(String value1, String value2) {
            addCriterion("signal_name not between", value1, value2, "signalName");
            return (Criteria) this;
        }

        public Criteria andSendIpIsNull() {
            addCriterion("send_ip is null");
            return (Criteria) this;
        }

        public Criteria andSendIpIsNotNull() {
            addCriterion("send_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSendIpEqualTo(String value) {
            addCriterion("send_ip =", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpNotEqualTo(String value) {
            addCriterion("send_ip <>", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpGreaterThan(String value) {
            addCriterion("send_ip >", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpGreaterThanOrEqualTo(String value) {
            addCriterion("send_ip >=", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpLessThan(String value) {
            addCriterion("send_ip <", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpLessThanOrEqualTo(String value) {
            addCriterion("send_ip <=", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpLike(String value) {
            addCriterion("send_ip like", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpNotLike(String value) {
            addCriterion("send_ip not like", value, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpIn(List<String> values) {
            addCriterion("send_ip in", values, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpNotIn(List<String> values) {
            addCriterion("send_ip not in", values, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpBetween(String value1, String value2) {
            addCriterion("send_ip between", value1, value2, "sendIp");
            return (Criteria) this;
        }

        public Criteria andSendIpNotBetween(String value1, String value2) {
            addCriterion("send_ip not between", value1, value2, "sendIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpIsNull() {
            addCriterion("receive_ip is null");
            return (Criteria) this;
        }

        public Criteria andReceiveIpIsNotNull() {
            addCriterion("receive_ip is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveIpEqualTo(String value) {
            addCriterion("receive_ip =", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpNotEqualTo(String value) {
            addCriterion("receive_ip <>", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpGreaterThan(String value) {
            addCriterion("receive_ip >", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpGreaterThanOrEqualTo(String value) {
            addCriterion("receive_ip >=", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpLessThan(String value) {
            addCriterion("receive_ip <", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpLessThanOrEqualTo(String value) {
            addCriterion("receive_ip <=", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpLike(String value) {
            addCriterion("receive_ip like", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpNotLike(String value) {
            addCriterion("receive_ip not like", value, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpIn(List<String> values) {
            addCriterion("receive_ip in", values, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpNotIn(List<String> values) {
            addCriterion("receive_ip not in", values, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpBetween(String value1, String value2) {
            addCriterion("receive_ip between", value1, value2, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andReceiveIpNotBetween(String value1, String value2) {
            addCriterion("receive_ip not between", value1, value2, "receiveIp");
            return (Criteria) this;
        }

        public Criteria andSignalContentIsNull() {
            addCriterion("signal_content is null");
            return (Criteria) this;
        }

        public Criteria andSignalContentIsNotNull() {
            addCriterion("signal_content is not null");
            return (Criteria) this;
        }

        public Criteria andSignalContentEqualTo(String value) {
            addCriterion("signal_content =", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentNotEqualTo(String value) {
            addCriterion("signal_content <>", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentGreaterThan(String value) {
            addCriterion("signal_content >", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentGreaterThanOrEqualTo(String value) {
            addCriterion("signal_content >=", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentLessThan(String value) {
            addCriterion("signal_content <", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentLessThanOrEqualTo(String value) {
            addCriterion("signal_content <=", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentLike(String value) {
            addCriterion("signal_content like", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentNotLike(String value) {
            addCriterion("signal_content not like", value, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentIn(List<String> values) {
            addCriterion("signal_content in", values, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentNotIn(List<String> values) {
            addCriterion("signal_content not in", values, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentBetween(String value1, String value2) {
            addCriterion("signal_content between", value1, value2, "signalContent");
            return (Criteria) this;
        }

        public Criteria andSignalContentNotBetween(String value1, String value2) {
            addCriterion("signal_content not between", value1, value2, "signalContent");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleIsNull() {
            addCriterion("business_module is null");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleIsNotNull() {
            addCriterion("business_module is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleEqualTo(String value) {
            addCriterion("business_module =", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleNotEqualTo(String value) {
            addCriterion("business_module <>", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleGreaterThan(String value) {
            addCriterion("business_module >", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleGreaterThanOrEqualTo(String value) {
            addCriterion("business_module >=", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleLessThan(String value) {
            addCriterion("business_module <", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleLessThanOrEqualTo(String value) {
            addCriterion("business_module <=", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleLike(String value) {
            addCriterion("business_module like", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleNotLike(String value) {
            addCriterion("business_module not like", value, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleIn(List<String> values) {
            addCriterion("business_module in", values, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleNotIn(List<String> values) {
            addCriterion("business_module not in", values, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleBetween(String value1, String value2) {
            addCriterion("business_module between", value1, value2, "businessModule");
            return (Criteria) this;
        }

        public Criteria andBusinessModuleNotBetween(String value1, String value2) {
            addCriterion("business_module not between", value1, value2, "businessModule");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Date value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Date value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Date value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Date value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Date> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Date> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Date value1, Date value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andIsExistIsNull() {
            addCriterion("is_exist is null");
            return (Criteria) this;
        }

        public Criteria andIsExistIsNotNull() {
            addCriterion("is_exist is not null");
            return (Criteria) this;
        }

        public Criteria andIsExistEqualTo(Boolean value) {
            addCriterion("is_exist =", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistNotEqualTo(Boolean value) {
            addCriterion("is_exist <>", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistGreaterThan(Boolean value) {
            addCriterion("is_exist >", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_exist >=", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistLessThan(Boolean value) {
            addCriterion("is_exist <", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistLessThanOrEqualTo(Boolean value) {
            addCriterion("is_exist <=", value, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistIn(List<Boolean> values) {
            addCriterion("is_exist in", values, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistNotIn(List<Boolean> values) {
            addCriterion("is_exist not in", values, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistBetween(Boolean value1, Boolean value2) {
            addCriterion("is_exist between", value1, value2, "isExist");
            return (Criteria) this;
        }

        public Criteria andIsExistNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_exist not between", value1, value2, "isExist");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}