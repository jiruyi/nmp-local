package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplBillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplBillExample() {
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

        public Criteria andOwnerIdIsNull() {
            addCriterion("owner_id is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNotNull() {
            addCriterion("owner_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdEqualTo(String value) {
            addCriterion("owner_id =", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotEqualTo(String value) {
            addCriterion("owner_id <>", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThan(String value) {
            addCriterion("owner_id >", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThanOrEqualTo(String value) {
            addCriterion("owner_id >=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThan(String value) {
            addCriterion("owner_id <", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThanOrEqualTo(String value) {
            addCriterion("owner_id <=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLike(String value) {
            addCriterion("owner_id like", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotLike(String value) {
            addCriterion("owner_id not like", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIn(List<String> values) {
            addCriterion("owner_id in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotIn(List<String> values) {
            addCriterion("owner_id not in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdBetween(String value1, String value2) {
            addCriterion("owner_id between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotBetween(String value1, String value2) {
            addCriterion("owner_id not between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andStreamIdIsNull() {
            addCriterion("stream_id is null");
            return (Criteria) this;
        }

        public Criteria andStreamIdIsNotNull() {
            addCriterion("stream_id is not null");
            return (Criteria) this;
        }

        public Criteria andStreamIdEqualTo(String value) {
            addCriterion("stream_id =", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdNotEqualTo(String value) {
            addCriterion("stream_id <>", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdGreaterThan(String value) {
            addCriterion("stream_id >", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdGreaterThanOrEqualTo(String value) {
            addCriterion("stream_id >=", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdLessThan(String value) {
            addCriterion("stream_id <", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdLessThanOrEqualTo(String value) {
            addCriterion("stream_id <=", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdLike(String value) {
            addCriterion("stream_id like", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdNotLike(String value) {
            addCriterion("stream_id not like", value, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdIn(List<String> values) {
            addCriterion("stream_id in", values, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdNotIn(List<String> values) {
            addCriterion("stream_id not in", values, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdBetween(String value1, String value2) {
            addCriterion("stream_id between", value1, value2, "streamId");
            return (Criteria) this;
        }

        public Criteria andStreamIdNotBetween(String value1, String value2) {
            addCriterion("stream_id not between", value1, value2, "streamId");
            return (Criteria) this;
        }

        public Criteria andFlowNumberIsNull() {
            addCriterion("flow_number is null");
            return (Criteria) this;
        }

        public Criteria andFlowNumberIsNotNull() {
            addCriterion("flow_number is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNumberEqualTo(String value) {
            addCriterion("flow_number =", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberNotEqualTo(String value) {
            addCriterion("flow_number <>", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberGreaterThan(String value) {
            addCriterion("flow_number >", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberGreaterThanOrEqualTo(String value) {
            addCriterion("flow_number >=", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberLessThan(String value) {
            addCriterion("flow_number <", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberLessThanOrEqualTo(String value) {
            addCriterion("flow_number <=", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberLike(String value) {
            addCriterion("flow_number like", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberNotLike(String value) {
            addCriterion("flow_number not like", value, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberIn(List<String> values) {
            addCriterion("flow_number in", values, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberNotIn(List<String> values) {
            addCriterion("flow_number not in", values, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberBetween(String value1, String value2) {
            addCriterion("flow_number between", value1, value2, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andFlowNumberNotBetween(String value1, String value2) {
            addCriterion("flow_number not between", value1, value2, "flowNumber");
            return (Criteria) this;
        }

        public Criteria andTimeLengthIsNull() {
            addCriterion("time_length is null");
            return (Criteria) this;
        }

        public Criteria andTimeLengthIsNotNull() {
            addCriterion("time_length is not null");
            return (Criteria) this;
        }

        public Criteria andTimeLengthEqualTo(String value) {
            addCriterion("time_length =", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthNotEqualTo(String value) {
            addCriterion("time_length <>", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthGreaterThan(String value) {
            addCriterion("time_length >", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthGreaterThanOrEqualTo(String value) {
            addCriterion("time_length >=", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthLessThan(String value) {
            addCriterion("time_length <", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthLessThanOrEqualTo(String value) {
            addCriterion("time_length <=", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthLike(String value) {
            addCriterion("time_length like", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthNotLike(String value) {
            addCriterion("time_length not like", value, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthIn(List<String> values) {
            addCriterion("time_length in", values, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthNotIn(List<String> values) {
            addCriterion("time_length not in", values, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthBetween(String value1, String value2) {
            addCriterion("time_length between", value1, value2, "timeLength");
            return (Criteria) this;
        }

        public Criteria andTimeLengthNotBetween(String value1, String value2) {
            addCriterion("time_length not between", value1, value2, "timeLength");
            return (Criteria) this;
        }

        public Criteria andKeyNumIsNull() {
            addCriterion("key_num is null");
            return (Criteria) this;
        }

        public Criteria andKeyNumIsNotNull() {
            addCriterion("key_num is not null");
            return (Criteria) this;
        }

        public Criteria andKeyNumEqualTo(String value) {
            addCriterion("key_num =", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumNotEqualTo(String value) {
            addCriterion("key_num <>", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumGreaterThan(String value) {
            addCriterion("key_num >", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumGreaterThanOrEqualTo(String value) {
            addCriterion("key_num >=", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumLessThan(String value) {
            addCriterion("key_num <", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumLessThanOrEqualTo(String value) {
            addCriterion("key_num <=", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumLike(String value) {
            addCriterion("key_num like", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumNotLike(String value) {
            addCriterion("key_num not like", value, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumIn(List<String> values) {
            addCriterion("key_num in", values, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumNotIn(List<String> values) {
            addCriterion("key_num not in", values, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumBetween(String value1, String value2) {
            addCriterion("key_num between", value1, value2, "keyNum");
            return (Criteria) this;
        }

        public Criteria andKeyNumNotBetween(String value1, String value2) {
            addCriterion("key_num not between", value1, value2, "keyNum");
            return (Criteria) this;
        }

        public Criteria andHybridFactorIsNull() {
            addCriterion("hybrid_factor is null");
            return (Criteria) this;
        }

        public Criteria andHybridFactorIsNotNull() {
            addCriterion("hybrid_factor is not null");
            return (Criteria) this;
        }

        public Criteria andHybridFactorEqualTo(String value) {
            addCriterion("hybrid_factor =", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorNotEqualTo(String value) {
            addCriterion("hybrid_factor <>", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorGreaterThan(String value) {
            addCriterion("hybrid_factor >", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorGreaterThanOrEqualTo(String value) {
            addCriterion("hybrid_factor >=", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorLessThan(String value) {
            addCriterion("hybrid_factor <", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorLessThanOrEqualTo(String value) {
            addCriterion("hybrid_factor <=", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorLike(String value) {
            addCriterion("hybrid_factor like", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorNotLike(String value) {
            addCriterion("hybrid_factor not like", value, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorIn(List<String> values) {
            addCriterion("hybrid_factor in", values, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorNotIn(List<String> values) {
            addCriterion("hybrid_factor not in", values, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorBetween(String value1, String value2) {
            addCriterion("hybrid_factor between", value1, value2, "hybridFactor");
            return (Criteria) this;
        }

        public Criteria andHybridFactorNotBetween(String value1, String value2) {
            addCriterion("hybrid_factor not between", value1, value2, "hybridFactor");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(String value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(String value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(String value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(String value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(String value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(String value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLike(String value) {
            addCriterion("start_time like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotLike(String value) {
            addCriterion("start_time not like", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<String> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<String> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(String value1, String value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(String value1, String value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(String value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(String value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(String value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(String value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(String value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLike(String value) {
            addCriterion("end_time like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotLike(String value) {
            addCriterion("end_time not like", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<String> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<String> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(String value1, String value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(String value1, String value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
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