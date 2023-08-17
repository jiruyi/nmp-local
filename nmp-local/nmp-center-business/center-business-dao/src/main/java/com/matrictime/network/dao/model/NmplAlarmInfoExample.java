package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplAlarmInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplAlarmInfoExample() {
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

        public Criteria andAlarmIdIsNull() {
            addCriterion("alarm_id is null");
            return (Criteria) this;
        }

        public Criteria andAlarmIdIsNotNull() {
            addCriterion("alarm_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmIdEqualTo(Long value) {
            addCriterion("alarm_id =", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdNotEqualTo(Long value) {
            addCriterion("alarm_id <>", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdGreaterThan(Long value) {
            addCriterion("alarm_id >", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdGreaterThanOrEqualTo(Long value) {
            addCriterion("alarm_id >=", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdLessThan(Long value) {
            addCriterion("alarm_id <", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdLessThanOrEqualTo(Long value) {
            addCriterion("alarm_id <=", value, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdIn(List<Long> values) {
            addCriterion("alarm_id in", values, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdNotIn(List<Long> values) {
            addCriterion("alarm_id not in", values, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdBetween(Long value1, Long value2) {
            addCriterion("alarm_id between", value1, value2, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmIdNotBetween(Long value1, Long value2) {
            addCriterion("alarm_id not between", value1, value2, "alarmId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdIsNull() {
            addCriterion("alarm_source_id is null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdIsNotNull() {
            addCriterion("alarm_source_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdEqualTo(String value) {
            addCriterion("alarm_source_id =", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdNotEqualTo(String value) {
            addCriterion("alarm_source_id <>", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdGreaterThan(String value) {
            addCriterion("alarm_source_id >", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_source_id >=", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdLessThan(String value) {
            addCriterion("alarm_source_id <", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdLessThanOrEqualTo(String value) {
            addCriterion("alarm_source_id <=", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdLike(String value) {
            addCriterion("alarm_source_id like", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdNotLike(String value) {
            addCriterion("alarm_source_id not like", value, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdIn(List<String> values) {
            addCriterion("alarm_source_id in", values, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdNotIn(List<String> values) {
            addCriterion("alarm_source_id not in", values, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdBetween(String value1, String value2) {
            addCriterion("alarm_source_id between", value1, value2, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIdNotBetween(String value1, String value2) {
            addCriterion("alarm_source_id not between", value1, value2, "alarmSourceId");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpIsNull() {
            addCriterion("alarm_source_ip is null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpIsNotNull() {
            addCriterion("alarm_source_ip is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpEqualTo(String value) {
            addCriterion("alarm_source_ip =", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpNotEqualTo(String value) {
            addCriterion("alarm_source_ip <>", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpGreaterThan(String value) {
            addCriterion("alarm_source_ip >", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_source_ip >=", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpLessThan(String value) {
            addCriterion("alarm_source_ip <", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpLessThanOrEqualTo(String value) {
            addCriterion("alarm_source_ip <=", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpLike(String value) {
            addCriterion("alarm_source_ip like", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpNotLike(String value) {
            addCriterion("alarm_source_ip not like", value, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpIn(List<String> values) {
            addCriterion("alarm_source_ip in", values, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpNotIn(List<String> values) {
            addCriterion("alarm_source_ip not in", values, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpBetween(String value1, String value2) {
            addCriterion("alarm_source_ip between", value1, value2, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceIpNotBetween(String value1, String value2) {
            addCriterion("alarm_source_ip not between", value1, value2, "alarmSourceIp");
            return (Criteria) this;
        }

        public Criteria andAlarmContentIsNull() {
            addCriterion("alarm_content is null");
            return (Criteria) this;
        }

        public Criteria andAlarmContentIsNotNull() {
            addCriterion("alarm_content is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmContentEqualTo(String value) {
            addCriterion("alarm_content =", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentNotEqualTo(String value) {
            addCriterion("alarm_content <>", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentGreaterThan(String value) {
            addCriterion("alarm_content >", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_content >=", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentLessThan(String value) {
            addCriterion("alarm_content <", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentLessThanOrEqualTo(String value) {
            addCriterion("alarm_content <=", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentLike(String value) {
            addCriterion("alarm_content like", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentNotLike(String value) {
            addCriterion("alarm_content not like", value, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentIn(List<String> values) {
            addCriterion("alarm_content in", values, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentNotIn(List<String> values) {
            addCriterion("alarm_content not in", values, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentBetween(String value1, String value2) {
            addCriterion("alarm_content between", value1, value2, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmContentNotBetween(String value1, String value2) {
            addCriterion("alarm_content not between", value1, value2, "alarmContent");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIsNull() {
            addCriterion("alarm_level is null");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIsNotNull() {
            addCriterion("alarm_level is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelEqualTo(String value) {
            addCriterion("alarm_level =", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotEqualTo(String value) {
            addCriterion("alarm_level <>", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelGreaterThan(String value) {
            addCriterion("alarm_level >", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_level >=", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLessThan(String value) {
            addCriterion("alarm_level <", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLessThanOrEqualTo(String value) {
            addCriterion("alarm_level <=", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelLike(String value) {
            addCriterion("alarm_level like", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotLike(String value) {
            addCriterion("alarm_level not like", value, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelIn(List<String> values) {
            addCriterion("alarm_level in", values, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotIn(List<String> values) {
            addCriterion("alarm_level not in", values, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelBetween(String value1, String value2) {
            addCriterion("alarm_level between", value1, value2, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmLevelNotBetween(String value1, String value2) {
            addCriterion("alarm_level not between", value1, value2, "alarmLevel");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeIsNull() {
            addCriterion("alarm_upload_time is null");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeIsNotNull() {
            addCriterion("alarm_upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeEqualTo(Date value) {
            addCriterion("alarm_upload_time =", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeNotEqualTo(Date value) {
            addCriterion("alarm_upload_time <>", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeGreaterThan(Date value) {
            addCriterion("alarm_upload_time >", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("alarm_upload_time >=", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeLessThan(Date value) {
            addCriterion("alarm_upload_time <", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeLessThanOrEqualTo(Date value) {
            addCriterion("alarm_upload_time <=", value, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeIn(List<Date> values) {
            addCriterion("alarm_upload_time in", values, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeNotIn(List<Date> values) {
            addCriterion("alarm_upload_time not in", values, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeBetween(Date value1, Date value2) {
            addCriterion("alarm_upload_time between", value1, value2, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmUploadTimeNotBetween(Date value1, Date value2) {
            addCriterion("alarm_upload_time not between", value1, value2, "alarmUploadTime");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeIsNull() {
            addCriterion("alarm_source_type is null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeIsNotNull() {
            addCriterion("alarm_source_type is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeEqualTo(String value) {
            addCriterion("alarm_source_type =", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeNotEqualTo(String value) {
            addCriterion("alarm_source_type <>", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeGreaterThan(String value) {
            addCriterion("alarm_source_type >", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_source_type >=", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeLessThan(String value) {
            addCriterion("alarm_source_type <", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeLessThanOrEqualTo(String value) {
            addCriterion("alarm_source_type <=", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeLike(String value) {
            addCriterion("alarm_source_type like", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeNotLike(String value) {
            addCriterion("alarm_source_type not like", value, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeIn(List<String> values) {
            addCriterion("alarm_source_type in", values, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeNotIn(List<String> values) {
            addCriterion("alarm_source_type not in", values, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeBetween(String value1, String value2) {
            addCriterion("alarm_source_type between", value1, value2, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmSourceTypeNotBetween(String value1, String value2) {
            addCriterion("alarm_source_type not between", value1, value2, "alarmSourceType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeIsNull() {
            addCriterion("alarm_content_type is null");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeIsNotNull() {
            addCriterion("alarm_content_type is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeEqualTo(String value) {
            addCriterion("alarm_content_type =", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeNotEqualTo(String value) {
            addCriterion("alarm_content_type <>", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeGreaterThan(String value) {
            addCriterion("alarm_content_type >", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_content_type >=", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeLessThan(String value) {
            addCriterion("alarm_content_type <", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeLessThanOrEqualTo(String value) {
            addCriterion("alarm_content_type <=", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeLike(String value) {
            addCriterion("alarm_content_type like", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeNotLike(String value) {
            addCriterion("alarm_content_type not like", value, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeIn(List<String> values) {
            addCriterion("alarm_content_type in", values, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeNotIn(List<String> values) {
            addCriterion("alarm_content_type not in", values, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeBetween(String value1, String value2) {
            addCriterion("alarm_content_type between", value1, value2, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmContentTypeNotBetween(String value1, String value2) {
            addCriterion("alarm_content_type not between", value1, value2, "alarmContentType");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeIsNull() {
            addCriterion("alarm_area_code is null");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeIsNotNull() {
            addCriterion("alarm_area_code is not null");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeEqualTo(String value) {
            addCriterion("alarm_area_code =", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeNotEqualTo(String value) {
            addCriterion("alarm_area_code <>", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeGreaterThan(String value) {
            addCriterion("alarm_area_code >", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("alarm_area_code >=", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeLessThan(String value) {
            addCriterion("alarm_area_code <", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("alarm_area_code <=", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeLike(String value) {
            addCriterion("alarm_area_code like", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeNotLike(String value) {
            addCriterion("alarm_area_code not like", value, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeIn(List<String> values) {
            addCriterion("alarm_area_code in", values, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeNotIn(List<String> values) {
            addCriterion("alarm_area_code not in", values, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeBetween(String value1, String value2) {
            addCriterion("alarm_area_code between", value1, value2, "alarmAreaCode");
            return (Criteria) this;
        }

        public Criteria andAlarmAreaCodeNotBetween(String value1, String value2) {
            addCriterion("alarm_area_code not between", value1, value2, "alarmAreaCode");
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