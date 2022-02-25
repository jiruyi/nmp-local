package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplLogintDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplLogintDetailExample() {
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

        public Criteria andLoginAccountIsNull() {
            addCriterion("login_account is null");
            return (Criteria) this;
        }

        public Criteria andLoginAccountIsNotNull() {
            addCriterion("login_account is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAccountEqualTo(String value) {
            addCriterion("login_account =", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountNotEqualTo(String value) {
            addCriterion("login_account <>", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountGreaterThan(String value) {
            addCriterion("login_account >", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountGreaterThanOrEqualTo(String value) {
            addCriterion("login_account >=", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountLessThan(String value) {
            addCriterion("login_account <", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountLessThanOrEqualTo(String value) {
            addCriterion("login_account <=", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountLike(String value) {
            addCriterion("login_account like", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountNotLike(String value) {
            addCriterion("login_account not like", value, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountIn(List<String> values) {
            addCriterion("login_account in", values, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountNotIn(List<String> values) {
            addCriterion("login_account not in", values, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountBetween(String value1, String value2) {
            addCriterion("login_account between", value1, value2, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andLoginAccountNotBetween(String value1, String value2) {
            addCriterion("login_account not between", value1, value2, "loginAccount");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNull() {
            addCriterion("nick_name is null");
            return (Criteria) this;
        }

        public Criteria andNickNameIsNotNull() {
            addCriterion("nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andNickNameEqualTo(String value) {
            addCriterion("nick_name =", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotEqualTo(String value) {
            addCriterion("nick_name <>", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThan(String value) {
            addCriterion("nick_name >", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("nick_name >=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThan(String value) {
            addCriterion("nick_name <", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLessThanOrEqualTo(String value) {
            addCriterion("nick_name <=", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameLike(String value) {
            addCriterion("nick_name like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotLike(String value) {
            addCriterion("nick_name not like", value, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameIn(List<String> values) {
            addCriterion("nick_name in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotIn(List<String> values) {
            addCriterion("nick_name not in", values, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameBetween(String value1, String value2) {
            addCriterion("nick_name between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andNickNameNotBetween(String value1, String value2) {
            addCriterion("nick_name not between", value1, value2, "nickName");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNull() {
            addCriterion("login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNotNull() {
            addCriterion("login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLoginIpEqualTo(String value) {
            addCriterion("login_ip =", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotEqualTo(String value) {
            addCriterion("login_ip <>", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThan(String value) {
            addCriterion("login_ip >", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("login_ip >=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThan(String value) {
            addCriterion("login_ip <", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThanOrEqualTo(String value) {
            addCriterion("login_ip <=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLike(String value) {
            addCriterion("login_ip like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotLike(String value) {
            addCriterion("login_ip not like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpIn(List<String> values) {
            addCriterion("login_ip in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotIn(List<String> values) {
            addCriterion("login_ip not in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpBetween(String value1, String value2) {
            addCriterion("login_ip between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotBetween(String value1, String value2) {
            addCriterion("login_ip not between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIsNull() {
            addCriterion("login_addr is null");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIsNotNull() {
            addCriterion("login_addr is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAddrEqualTo(String value) {
            addCriterion("login_addr =", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotEqualTo(String value) {
            addCriterion("login_addr <>", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrGreaterThan(String value) {
            addCriterion("login_addr >", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrGreaterThanOrEqualTo(String value) {
            addCriterion("login_addr >=", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLessThan(String value) {
            addCriterion("login_addr <", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLessThanOrEqualTo(String value) {
            addCriterion("login_addr <=", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLike(String value) {
            addCriterion("login_addr like", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotLike(String value) {
            addCriterion("login_addr not like", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIn(List<String> values) {
            addCriterion("login_addr in", values, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotIn(List<String> values) {
            addCriterion("login_addr not in", values, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrBetween(String value1, String value2) {
            addCriterion("login_addr between", value1, value2, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotBetween(String value1, String value2) {
            addCriterion("login_addr not between", value1, value2, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginTypeIsNull() {
            addCriterion("login_type is null");
            return (Criteria) this;
        }

        public Criteria andLoginTypeIsNotNull() {
            addCriterion("login_type is not null");
            return (Criteria) this;
        }

        public Criteria andLoginTypeEqualTo(Byte value) {
            addCriterion("login_type =", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeNotEqualTo(Byte value) {
            addCriterion("login_type <>", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeGreaterThan(Byte value) {
            addCriterion("login_type >", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("login_type >=", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeLessThan(Byte value) {
            addCriterion("login_type <", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeLessThanOrEqualTo(Byte value) {
            addCriterion("login_type <=", value, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeIn(List<Byte> values) {
            addCriterion("login_type in", values, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeNotIn(List<Byte> values) {
            addCriterion("login_type not in", values, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeBetween(Byte value1, Byte value2) {
            addCriterion("login_type between", value1, value2, "loginType");
            return (Criteria) this;
        }

        public Criteria andLoginTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("login_type not between", value1, value2, "loginType");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIsNull() {
            addCriterion("is_success is null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIsNotNull() {
            addCriterion("is_success is not null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessEqualTo(Boolean value) {
            addCriterion("is_success =", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotEqualTo(Boolean value) {
            addCriterion("is_success <>", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThan(Boolean value) {
            addCriterion("is_success >", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_success >=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThan(Boolean value) {
            addCriterion("is_success <", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("is_success <=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIn(List<Boolean> values) {
            addCriterion("is_success in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotIn(List<Boolean> values) {
            addCriterion("is_success not in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success not between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andFailCauseIsNull() {
            addCriterion("fail_cause is null");
            return (Criteria) this;
        }

        public Criteria andFailCauseIsNotNull() {
            addCriterion("fail_cause is not null");
            return (Criteria) this;
        }

        public Criteria andFailCauseEqualTo(String value) {
            addCriterion("fail_cause =", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotEqualTo(String value) {
            addCriterion("fail_cause <>", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseGreaterThan(String value) {
            addCriterion("fail_cause >", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseGreaterThanOrEqualTo(String value) {
            addCriterion("fail_cause >=", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLessThan(String value) {
            addCriterion("fail_cause <", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLessThanOrEqualTo(String value) {
            addCriterion("fail_cause <=", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseLike(String value) {
            addCriterion("fail_cause like", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotLike(String value) {
            addCriterion("fail_cause not like", value, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseIn(List<String> values) {
            addCriterion("fail_cause in", values, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotIn(List<String> values) {
            addCriterion("fail_cause not in", values, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseBetween(String value1, String value2) {
            addCriterion("fail_cause between", value1, value2, "failCause");
            return (Criteria) this;
        }

        public Criteria andFailCauseNotBetween(String value1, String value2) {
            addCriterion("fail_cause not between", value1, value2, "failCause");
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