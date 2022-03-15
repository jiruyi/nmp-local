package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplDeviceLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplDeviceLogExample() {
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

        public Criteria andDevcieNameIsNull() {
            addCriterion("devcie_name is null");
            return (Criteria) this;
        }

        public Criteria andDevcieNameIsNotNull() {
            addCriterion("devcie_name is not null");
            return (Criteria) this;
        }

        public Criteria andDevcieNameEqualTo(String value) {
            addCriterion("devcie_name =", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameNotEqualTo(String value) {
            addCriterion("devcie_name <>", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameGreaterThan(String value) {
            addCriterion("devcie_name >", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameGreaterThanOrEqualTo(String value) {
            addCriterion("devcie_name >=", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameLessThan(String value) {
            addCriterion("devcie_name <", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameLessThanOrEqualTo(String value) {
            addCriterion("devcie_name <=", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameLike(String value) {
            addCriterion("devcie_name like", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameNotLike(String value) {
            addCriterion("devcie_name not like", value, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameIn(List<String> values) {
            addCriterion("devcie_name in", values, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameNotIn(List<String> values) {
            addCriterion("devcie_name not in", values, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameBetween(String value1, String value2) {
            addCriterion("devcie_name between", value1, value2, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDevcieNameNotBetween(String value1, String value2) {
            addCriterion("devcie_name not between", value1, value2, "devcieName");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("device_type is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("device_type is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("device_type =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(String value) {
            addCriterion("device_type <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(String value) {
            addCriterion("device_type >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("device_type >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(String value) {
            addCriterion("device_type <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(String value) {
            addCriterion("device_type <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLike(String value) {
            addCriterion("device_type like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotLike(String value) {
            addCriterion("device_type not like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("device_type in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<String> values) {
            addCriterion("device_type not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(String value1, String value2) {
            addCriterion("device_type between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(String value1, String value2) {
            addCriterion("device_type not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIsNull() {
            addCriterion("device_ip is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIsNotNull() {
            addCriterion("device_ip is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIpEqualTo(String value) {
            addCriterion("device_ip =", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotEqualTo(String value) {
            addCriterion("device_ip <>", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpGreaterThan(String value) {
            addCriterion("device_ip >", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpGreaterThanOrEqualTo(String value) {
            addCriterion("device_ip >=", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLessThan(String value) {
            addCriterion("device_ip <", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLessThanOrEqualTo(String value) {
            addCriterion("device_ip <=", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLike(String value) {
            addCriterion("device_ip like", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotLike(String value) {
            addCriterion("device_ip not like", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIn(List<String> values) {
            addCriterion("device_ip in", values, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotIn(List<String> values) {
            addCriterion("device_ip not in", values, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpBetween(String value1, String value2) {
            addCriterion("device_ip between", value1, value2, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotBetween(String value1, String value2) {
            addCriterion("device_ip not between", value1, value2, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNull() {
            addCriterion("oper_type is null");
            return (Criteria) this;
        }

        public Criteria andOperTypeIsNotNull() {
            addCriterion("oper_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperTypeEqualTo(String value) {
            addCriterion("oper_type =", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotEqualTo(String value) {
            addCriterion("oper_type <>", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThan(String value) {
            addCriterion("oper_type >", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeGreaterThanOrEqualTo(String value) {
            addCriterion("oper_type >=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThan(String value) {
            addCriterion("oper_type <", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLessThanOrEqualTo(String value) {
            addCriterion("oper_type <=", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeLike(String value) {
            addCriterion("oper_type like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotLike(String value) {
            addCriterion("oper_type not like", value, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeIn(List<String> values) {
            addCriterion("oper_type in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotIn(List<String> values) {
            addCriterion("oper_type not in", values, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeBetween(String value1, String value2) {
            addCriterion("oper_type between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperTypeNotBetween(String value1, String value2) {
            addCriterion("oper_type not between", value1, value2, "operType");
            return (Criteria) this;
        }

        public Criteria andOperDescIsNull() {
            addCriterion("oper_desc is null");
            return (Criteria) this;
        }

        public Criteria andOperDescIsNotNull() {
            addCriterion("oper_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOperDescEqualTo(String value) {
            addCriterion("oper_desc =", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescNotEqualTo(String value) {
            addCriterion("oper_desc <>", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescGreaterThan(String value) {
            addCriterion("oper_desc >", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescGreaterThanOrEqualTo(String value) {
            addCriterion("oper_desc >=", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescLessThan(String value) {
            addCriterion("oper_desc <", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescLessThanOrEqualTo(String value) {
            addCriterion("oper_desc <=", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescLike(String value) {
            addCriterion("oper_desc like", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescNotLike(String value) {
            addCriterion("oper_desc not like", value, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescIn(List<String> values) {
            addCriterion("oper_desc in", values, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescNotIn(List<String> values) {
            addCriterion("oper_desc not in", values, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescBetween(String value1, String value2) {
            addCriterion("oper_desc between", value1, value2, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperDescNotBetween(String value1, String value2) {
            addCriterion("oper_desc not between", value1, value2, "operDesc");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNull() {
            addCriterion("oper_user is null");
            return (Criteria) this;
        }

        public Criteria andOperUserIsNotNull() {
            addCriterion("oper_user is not null");
            return (Criteria) this;
        }

        public Criteria andOperUserEqualTo(String value) {
            addCriterion("oper_user =", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotEqualTo(String value) {
            addCriterion("oper_user <>", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThan(String value) {
            addCriterion("oper_user >", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserGreaterThanOrEqualTo(String value) {
            addCriterion("oper_user >=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThan(String value) {
            addCriterion("oper_user <", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLessThanOrEqualTo(String value) {
            addCriterion("oper_user <=", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserLike(String value) {
            addCriterion("oper_user like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotLike(String value) {
            addCriterion("oper_user not like", value, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserIn(List<String> values) {
            addCriterion("oper_user in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotIn(List<String> values) {
            addCriterion("oper_user not in", values, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserBetween(String value1, String value2) {
            addCriterion("oper_user between", value1, value2, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperUserNotBetween(String value1, String value2) {
            addCriterion("oper_user not between", value1, value2, "operUser");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNull() {
            addCriterion("oper_time is null");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNotNull() {
            addCriterion("oper_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperTimeEqualTo(Date value) {
            addCriterion("oper_time =", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotEqualTo(Date value) {
            addCriterion("oper_time <>", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThan(Date value) {
            addCriterion("oper_time >", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("oper_time >=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThan(Date value) {
            addCriterion("oper_time <", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThanOrEqualTo(Date value) {
            addCriterion("oper_time <=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeIn(List<Date> values) {
            addCriterion("oper_time in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotIn(List<Date> values) {
            addCriterion("oper_time not in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeBetween(Date value1, Date value2) {
            addCriterion("oper_time between", value1, value2, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotBetween(Date value1, Date value2) {
            addCriterion("oper_time not between", value1, value2, "operTime");
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