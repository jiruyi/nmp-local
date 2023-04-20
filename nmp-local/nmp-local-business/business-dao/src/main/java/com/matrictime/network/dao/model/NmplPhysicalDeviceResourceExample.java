package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplPhysicalDeviceResourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplPhysicalDeviceResourceExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(String value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(String value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(String value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(String value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(String value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLike(String value) {
            addCriterion("resource_type like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotLike(String value) {
            addCriterion("resource_type not like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<String> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<String> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(String value1, String value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(String value1, String value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceValueIsNull() {
            addCriterion("resource_value is null");
            return (Criteria) this;
        }

        public Criteria andResourceValueIsNotNull() {
            addCriterion("resource_value is not null");
            return (Criteria) this;
        }

        public Criteria andResourceValueEqualTo(String value) {
            addCriterion("resource_value =", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueNotEqualTo(String value) {
            addCriterion("resource_value <>", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueGreaterThan(String value) {
            addCriterion("resource_value >", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueGreaterThanOrEqualTo(String value) {
            addCriterion("resource_value >=", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueLessThan(String value) {
            addCriterion("resource_value <", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueLessThanOrEqualTo(String value) {
            addCriterion("resource_value <=", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueLike(String value) {
            addCriterion("resource_value like", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueNotLike(String value) {
            addCriterion("resource_value not like", value, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueIn(List<String> values) {
            addCriterion("resource_value in", values, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueNotIn(List<String> values) {
            addCriterion("resource_value not in", values, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueBetween(String value1, String value2) {
            addCriterion("resource_value between", value1, value2, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceValueNotBetween(String value1, String value2) {
            addCriterion("resource_value not between", value1, value2, "resourceValue");
            return (Criteria) this;
        }

        public Criteria andResourceUnitIsNull() {
            addCriterion("resource_unit is null");
            return (Criteria) this;
        }

        public Criteria andResourceUnitIsNotNull() {
            addCriterion("resource_unit is not null");
            return (Criteria) this;
        }

        public Criteria andResourceUnitEqualTo(String value) {
            addCriterion("resource_unit =", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitNotEqualTo(String value) {
            addCriterion("resource_unit <>", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitGreaterThan(String value) {
            addCriterion("resource_unit >", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitGreaterThanOrEqualTo(String value) {
            addCriterion("resource_unit >=", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitLessThan(String value) {
            addCriterion("resource_unit <", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitLessThanOrEqualTo(String value) {
            addCriterion("resource_unit <=", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitLike(String value) {
            addCriterion("resource_unit like", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitNotLike(String value) {
            addCriterion("resource_unit not like", value, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitIn(List<String> values) {
            addCriterion("resource_unit in", values, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitNotIn(List<String> values) {
            addCriterion("resource_unit not in", values, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitBetween(String value1, String value2) {
            addCriterion("resource_unit between", value1, value2, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourceUnitNotBetween(String value1, String value2) {
            addCriterion("resource_unit not between", value1, value2, "resourceUnit");
            return (Criteria) this;
        }

        public Criteria andResourcePercentIsNull() {
            addCriterion("resource_percent is null");
            return (Criteria) this;
        }

        public Criteria andResourcePercentIsNotNull() {
            addCriterion("resource_percent is not null");
            return (Criteria) this;
        }

        public Criteria andResourcePercentEqualTo(String value) {
            addCriterion("resource_percent =", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentNotEqualTo(String value) {
            addCriterion("resource_percent <>", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentGreaterThan(String value) {
            addCriterion("resource_percent >", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentGreaterThanOrEqualTo(String value) {
            addCriterion("resource_percent >=", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentLessThan(String value) {
            addCriterion("resource_percent <", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentLessThanOrEqualTo(String value) {
            addCriterion("resource_percent <=", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentLike(String value) {
            addCriterion("resource_percent like", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentNotLike(String value) {
            addCriterion("resource_percent not like", value, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentIn(List<String> values) {
            addCriterion("resource_percent in", values, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentNotIn(List<String> values) {
            addCriterion("resource_percent not in", values, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentBetween(String value1, String value2) {
            addCriterion("resource_percent between", value1, value2, "resourcePercent");
            return (Criteria) this;
        }

        public Criteria andResourcePercentNotBetween(String value1, String value2) {
            addCriterion("resource_percent not between", value1, value2, "resourcePercent");
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