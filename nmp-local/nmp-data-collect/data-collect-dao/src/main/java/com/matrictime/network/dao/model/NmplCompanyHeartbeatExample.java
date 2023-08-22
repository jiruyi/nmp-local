package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplCompanyHeartbeatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplCompanyHeartbeatExample() {
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

        public Criteria andSourceNetworkIdIsNull() {
            addCriterion("source_network_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdIsNotNull() {
            addCriterion("source_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdEqualTo(String value) {
            addCriterion("source_network_id =", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdNotEqualTo(String value) {
            addCriterion("source_network_id <>", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdGreaterThan(String value) {
            addCriterion("source_network_id >", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("source_network_id >=", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdLessThan(String value) {
            addCriterion("source_network_id <", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("source_network_id <=", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdLike(String value) {
            addCriterion("source_network_id like", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdNotLike(String value) {
            addCriterion("source_network_id not like", value, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdIn(List<String> values) {
            addCriterion("source_network_id in", values, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdNotIn(List<String> values) {
            addCriterion("source_network_id not in", values, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdBetween(String value1, String value2) {
            addCriterion("source_network_id between", value1, value2, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andSourceNetworkIdNotBetween(String value1, String value2) {
            addCriterion("source_network_id not between", value1, value2, "sourceNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdIsNull() {
            addCriterion("target_network_id is null");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdIsNotNull() {
            addCriterion("target_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdEqualTo(String value) {
            addCriterion("target_network_id =", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdNotEqualTo(String value) {
            addCriterion("target_network_id <>", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdGreaterThan(String value) {
            addCriterion("target_network_id >", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("target_network_id >=", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdLessThan(String value) {
            addCriterion("target_network_id <", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("target_network_id <=", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdLike(String value) {
            addCriterion("target_network_id like", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdNotLike(String value) {
            addCriterion("target_network_id not like", value, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdIn(List<String> values) {
            addCriterion("target_network_id in", values, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdNotIn(List<String> values) {
            addCriterion("target_network_id not in", values, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdBetween(String value1, String value2) {
            addCriterion("target_network_id between", value1, value2, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andTargetNetworkIdNotBetween(String value1, String value2) {
            addCriterion("target_network_id not between", value1, value2, "targetNetworkId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andUpValueEqualTo(String value) {
            addCriterion("up_value =", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotEqualTo(String value) {
            addCriterion("up_value <>", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueGreaterThan(String value) {
            addCriterion("up_value >", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueGreaterThanOrEqualTo(String value) {
            addCriterion("up_value >=", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueLessThan(String value) {
            addCriterion("up_value <", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueLessThanOrEqualTo(String value) {
            addCriterion("up_value <=", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueLike(String value) {
            addCriterion("up_value like", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotLike(String value) {
            addCriterion("up_value not like", value, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueIn(List<String> values) {
            addCriterion("up_value in", values, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotIn(List<String> values) {
            addCriterion("up_value not in", values, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueBetween(String value1, String value2) {
            addCriterion("up_value between", value1, value2, "upValue");
            return (Criteria) this;
        }

        public Criteria andUpValueNotBetween(String value1, String value2) {
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

        public Criteria andDownValueEqualTo(String value) {
            addCriterion("down_value =", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotEqualTo(String value) {
            addCriterion("down_value <>", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueGreaterThan(String value) {
            addCriterion("down_value >", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueGreaterThanOrEqualTo(String value) {
            addCriterion("down_value >=", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueLessThan(String value) {
            addCriterion("down_value <", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueLessThanOrEqualTo(String value) {
            addCriterion("down_value <=", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueLike(String value) {
            addCriterion("down_value like", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotLike(String value) {
            addCriterion("down_value not like", value, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueIn(List<String> values) {
            addCriterion("down_value in", values, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotIn(List<String> values) {
            addCriterion("down_value not in", values, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueBetween(String value1, String value2) {
            addCriterion("down_value between", value1, value2, "downValue");
            return (Criteria) this;
        }

        public Criteria andDownValueNotBetween(String value1, String value2) {
            addCriterion("down_value not between", value1, value2, "downValue");
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