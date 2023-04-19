package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplTerminalDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplTerminalDataExample() {
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

        public Criteria andTerminalNetworkIdIsNull() {
            addCriterion("terminal_network_id is null");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdIsNotNull() {
            addCriterion("terminal_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdEqualTo(String value) {
            addCriterion("terminal_network_id =", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdNotEqualTo(String value) {
            addCriterion("terminal_network_id <>", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdGreaterThan(String value) {
            addCriterion("terminal_network_id >", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("terminal_network_id >=", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdLessThan(String value) {
            addCriterion("terminal_network_id <", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("terminal_network_id <=", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdLike(String value) {
            addCriterion("terminal_network_id like", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdNotLike(String value) {
            addCriterion("terminal_network_id not like", value, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdIn(List<String> values) {
            addCriterion("terminal_network_id in", values, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdNotIn(List<String> values) {
            addCriterion("terminal_network_id not in", values, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdBetween(String value1, String value2) {
            addCriterion("terminal_network_id between", value1, value2, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andTerminalNetworkIdNotBetween(String value1, String value2) {
            addCriterion("terminal_network_id not between", value1, value2, "terminalNetworkId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("data_type like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("data_type not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andUpValueIsNull() {
            addCriterion("up_value is null");
            return (Criteria) this;
        }

        public Criteria andUpValueIsNotNull() {
            addCriterion("up_value is not null");
            return (Criteria) this;
        }

        public Criteria andUpValueEqualTo(Integer value) {
            addCriterion("up_value =", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotEqualTo(Integer value) {
            addCriterion("up_value <>", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueGreaterThan(Integer value) {
            addCriterion("up_value >", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("up_value >=", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueLessThan(Integer value) {
            addCriterion("up_value <", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueLessThanOrEqualTo(Integer value) {
            addCriterion("up_value <=", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueIn(List<Integer> values) {
            addCriterion("up_value in", values, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotIn(List<Integer> values) {
            addCriterion("up_value not in", values, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueBetween(Integer value1, Integer value2) {
            addCriterion("up_value between", value1, value2, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotBetween(Integer value1, Integer value2) {
            addCriterion("up_value not between", value1, value2, "upValue");
            return (Criteria) this;
        }

        public Criteria andDownValueIsNull() {
            addCriterion("down_value is null");
            return (Criteria) this;
        }

        public Criteria andDownValueIsNotNull() {
            addCriterion("down_value is not null");
            return (Criteria) this;
        }

        public Criteria andDownValueEqualTo(Integer value) {
            addCriterion("down_value =", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotEqualTo(Integer value) {
            addCriterion("down_value <>", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueGreaterThan(Integer value) {
            addCriterion("down_value >", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("down_value >=", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueLessThan(Integer value) {
            addCriterion("down_value <", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueLessThanOrEqualTo(Integer value) {
            addCriterion("down_value <=", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueIn(List<Integer> values) {
            addCriterion("down_value in", values, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotIn(List<Integer> values) {
            addCriterion("down_value not in", values, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueBetween(Integer value1, Integer value2) {
            addCriterion("down_value between", value1, value2, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotBetween(Integer value1, Integer value2) {
            addCriterion("down_value not between", value1, value2, "downValue");
            return (Criteria) this;
        }

        public Criteria andTerminalIpIsNull() {
            addCriterion("terminal_ip is null");
            return (Criteria) this;
        }

        public Criteria andTerminalIpIsNotNull() {
            addCriterion("terminal_ip is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalIpEqualTo(String value) {
            addCriterion("terminal_ip =", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpNotEqualTo(String value) {
            addCriterion("terminal_ip <>", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpGreaterThan(String value) {
            addCriterion("terminal_ip >", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpGreaterThanOrEqualTo(String value) {
            addCriterion("terminal_ip >=", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpLessThan(String value) {
            addCriterion("terminal_ip <", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpLessThanOrEqualTo(String value) {
            addCriterion("terminal_ip <=", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpLike(String value) {
            addCriterion("terminal_ip like", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpNotLike(String value) {
            addCriterion("terminal_ip not like", value, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpIn(List<String> values) {
            addCriterion("terminal_ip in", values, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpNotIn(List<String> values) {
            addCriterion("terminal_ip not in", values, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpBetween(String value1, String value2) {
            addCriterion("terminal_ip between", value1, value2, "terminalIp");
            return (Criteria) this;
        }

        public Criteria andTerminalIpNotBetween(String value1, String value2) {
            addCriterion("terminal_ip not between", value1, value2, "terminalIp");
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