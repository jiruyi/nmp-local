package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplTerminalUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplTerminalUserExample() {
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

        public Criteria andTerminalStatusIsNull() {
            addCriterion("terminal_status is null");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusIsNotNull() {
            addCriterion("terminal_status is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusEqualTo(String value) {
            addCriterion("terminal_status =", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusNotEqualTo(String value) {
            addCriterion("terminal_status <>", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusGreaterThan(String value) {
            addCriterion("terminal_status >", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("terminal_status >=", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusLessThan(String value) {
            addCriterion("terminal_status <", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusLessThanOrEqualTo(String value) {
            addCriterion("terminal_status <=", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusLike(String value) {
            addCriterion("terminal_status like", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusNotLike(String value) {
            addCriterion("terminal_status not like", value, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusIn(List<String> values) {
            addCriterion("terminal_status in", values, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusNotIn(List<String> values) {
            addCriterion("terminal_status not in", values, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusBetween(String value1, String value2) {
            addCriterion("terminal_status between", value1, value2, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andTerminalStatusNotBetween(String value1, String value2) {
            addCriterion("terminal_status not between", value1, value2, "terminalStatus");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdIsNull() {
            addCriterion("company_network_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdIsNotNull() {
            addCriterion("company_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdEqualTo(String value) {
            addCriterion("company_network_id =", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdNotEqualTo(String value) {
            addCriterion("company_network_id <>", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdGreaterThan(String value) {
            addCriterion("company_network_id >", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("company_network_id >=", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdLessThan(String value) {
            addCriterion("company_network_id <", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("company_network_id <=", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdLike(String value) {
            addCriterion("company_network_id like", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdNotLike(String value) {
            addCriterion("company_network_id not like", value, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdIn(List<String> values) {
            addCriterion("company_network_id in", values, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdNotIn(List<String> values) {
            addCriterion("company_network_id not in", values, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdBetween(String value1, String value2) {
            addCriterion("company_network_id between", value1, value2, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andCompanyNetworkIdNotBetween(String value1, String value2) {
            addCriterion("company_network_id not between", value1, value2, "companyNetworkId");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("user_type like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("user_type not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andSumNumberIsNull() {
            addCriterion("sum_number is null");
            return (Criteria) this;
        }

        public Criteria andSumNumberIsNotNull() {
            addCriterion("sum_number is not null");
            return (Criteria) this;
        }

        public Criteria andSumNumberEqualTo(String value) {
            addCriterion("sum_number =", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberNotEqualTo(String value) {
            addCriterion("sum_number <>", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberGreaterThan(String value) {
            addCriterion("sum_number >", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberGreaterThanOrEqualTo(String value) {
            addCriterion("sum_number >=", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberLessThan(String value) {
            addCriterion("sum_number <", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberLessThanOrEqualTo(String value) {
            addCriterion("sum_number <=", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberLike(String value) {
            addCriterion("sum_number like", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberNotLike(String value) {
            addCriterion("sum_number not like", value, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberIn(List<String> values) {
            addCriterion("sum_number in", values, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberNotIn(List<String> values) {
            addCriterion("sum_number not in", values, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberBetween(String value1, String value2) {
            addCriterion("sum_number between", value1, value2, "sumNumber");
            return (Criteria) this;
        }

        public Criteria andSumNumberNotBetween(String value1, String value2) {
            addCriterion("sum_number not between", value1, value2, "sumNumber");
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