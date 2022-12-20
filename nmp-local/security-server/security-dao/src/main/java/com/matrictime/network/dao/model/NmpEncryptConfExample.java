package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmpEncryptConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmpEncryptConfExample() {
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

        public Criteria andUpEncryptRatioIsNull() {
            addCriterion("up_encrypt_ratio is null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioIsNotNull() {
            addCriterion("up_encrypt_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioEqualTo(String value) {
            addCriterion("up_encrypt_ratio =", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioNotEqualTo(String value) {
            addCriterion("up_encrypt_ratio <>", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioGreaterThan(String value) {
            addCriterion("up_encrypt_ratio >", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioGreaterThanOrEqualTo(String value) {
            addCriterion("up_encrypt_ratio >=", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioLessThan(String value) {
            addCriterion("up_encrypt_ratio <", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioLessThanOrEqualTo(String value) {
            addCriterion("up_encrypt_ratio <=", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioLike(String value) {
            addCriterion("up_encrypt_ratio like", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioNotLike(String value) {
            addCriterion("up_encrypt_ratio not like", value, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioIn(List<String> values) {
            addCriterion("up_encrypt_ratio in", values, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioNotIn(List<String> values) {
            addCriterion("up_encrypt_ratio not in", values, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioBetween(String value1, String value2) {
            addCriterion("up_encrypt_ratio between", value1, value2, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpEncryptRatioNotBetween(String value1, String value2) {
            addCriterion("up_encrypt_ratio not between", value1, value2, "upEncryptRatio");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmIsNull() {
            addCriterion("up_extend_algorithm is null");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmIsNotNull() {
            addCriterion("up_extend_algorithm is not null");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmEqualTo(String value) {
            addCriterion("up_extend_algorithm =", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmNotEqualTo(String value) {
            addCriterion("up_extend_algorithm <>", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmGreaterThan(String value) {
            addCriterion("up_extend_algorithm >", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmGreaterThanOrEqualTo(String value) {
            addCriterion("up_extend_algorithm >=", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmLessThan(String value) {
            addCriterion("up_extend_algorithm <", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmLessThanOrEqualTo(String value) {
            addCriterion("up_extend_algorithm <=", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmLike(String value) {
            addCriterion("up_extend_algorithm like", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmNotLike(String value) {
            addCriterion("up_extend_algorithm not like", value, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmIn(List<String> values) {
            addCriterion("up_extend_algorithm in", values, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmNotIn(List<String> values) {
            addCriterion("up_extend_algorithm not in", values, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmBetween(String value1, String value2) {
            addCriterion("up_extend_algorithm between", value1, value2, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpExtendAlgorithmNotBetween(String value1, String value2) {
            addCriterion("up_extend_algorithm not between", value1, value2, "upExtendAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeIsNull() {
            addCriterion("up_encrypt_type is null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeIsNotNull() {
            addCriterion("up_encrypt_type is not null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeEqualTo(String value) {
            addCriterion("up_encrypt_type =", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeNotEqualTo(String value) {
            addCriterion("up_encrypt_type <>", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeGreaterThan(String value) {
            addCriterion("up_encrypt_type >", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeGreaterThanOrEqualTo(String value) {
            addCriterion("up_encrypt_type >=", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeLessThan(String value) {
            addCriterion("up_encrypt_type <", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeLessThanOrEqualTo(String value) {
            addCriterion("up_encrypt_type <=", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeLike(String value) {
            addCriterion("up_encrypt_type like", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeNotLike(String value) {
            addCriterion("up_encrypt_type not like", value, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeIn(List<String> values) {
            addCriterion("up_encrypt_type in", values, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeNotIn(List<String> values) {
            addCriterion("up_encrypt_type not in", values, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeBetween(String value1, String value2) {
            addCriterion("up_encrypt_type between", value1, value2, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptTypeNotBetween(String value1, String value2) {
            addCriterion("up_encrypt_type not between", value1, value2, "upEncryptType");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmIsNull() {
            addCriterion("up_encrypt_algorithm is null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmIsNotNull() {
            addCriterion("up_encrypt_algorithm is not null");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmEqualTo(String value) {
            addCriterion("up_encrypt_algorithm =", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmNotEqualTo(String value) {
            addCriterion("up_encrypt_algorithm <>", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmGreaterThan(String value) {
            addCriterion("up_encrypt_algorithm >", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmGreaterThanOrEqualTo(String value) {
            addCriterion("up_encrypt_algorithm >=", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmLessThan(String value) {
            addCriterion("up_encrypt_algorithm <", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmLessThanOrEqualTo(String value) {
            addCriterion("up_encrypt_algorithm <=", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmLike(String value) {
            addCriterion("up_encrypt_algorithm like", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmNotLike(String value) {
            addCriterion("up_encrypt_algorithm not like", value, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmIn(List<String> values) {
            addCriterion("up_encrypt_algorithm in", values, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmNotIn(List<String> values) {
            addCriterion("up_encrypt_algorithm not in", values, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmBetween(String value1, String value2) {
            addCriterion("up_encrypt_algorithm between", value1, value2, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpEncryptAlgorithmNotBetween(String value1, String value2) {
            addCriterion("up_encrypt_algorithm not between", value1, value2, "upEncryptAlgorithm");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueIsNull() {
            addCriterion("up_max_value is null");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueIsNotNull() {
            addCriterion("up_max_value is not null");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueEqualTo(Long value) {
            addCriterion("up_max_value =", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueNotEqualTo(Long value) {
            addCriterion("up_max_value <>", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueGreaterThan(Long value) {
            addCriterion("up_max_value >", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueGreaterThanOrEqualTo(Long value) {
            addCriterion("up_max_value >=", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueLessThan(Long value) {
            addCriterion("up_max_value <", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueLessThanOrEqualTo(Long value) {
            addCriterion("up_max_value <=", value, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueIn(List<Long> values) {
            addCriterion("up_max_value in", values, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueNotIn(List<Long> values) {
            addCriterion("up_max_value not in", values, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueBetween(Long value1, Long value2) {
            addCriterion("up_max_value between", value1, value2, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpMaxValueNotBetween(Long value1, Long value2) {
            addCriterion("up_max_value not between", value1, value2, "upMaxValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueIsNull() {
            addCriterion("up_warn_value is null");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueIsNotNull() {
            addCriterion("up_warn_value is not null");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueEqualTo(Long value) {
            addCriterion("up_warn_value =", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueNotEqualTo(Long value) {
            addCriterion("up_warn_value <>", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueGreaterThan(Long value) {
            addCriterion("up_warn_value >", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueGreaterThanOrEqualTo(Long value) {
            addCriterion("up_warn_value >=", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueLessThan(Long value) {
            addCriterion("up_warn_value <", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueLessThanOrEqualTo(Long value) {
            addCriterion("up_warn_value <=", value, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueIn(List<Long> values) {
            addCriterion("up_warn_value in", values, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueNotIn(List<Long> values) {
            addCriterion("up_warn_value not in", values, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueBetween(Long value1, Long value2) {
            addCriterion("up_warn_value between", value1, value2, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpWarnValueNotBetween(Long value1, Long value2) {
            addCriterion("up_warn_value not between", value1, value2, "upWarnValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueIsNull() {
            addCriterion("up_min_value is null");
            return (Criteria) this;
        }

        public Criteria andUpMinValueIsNotNull() {
            addCriterion("up_min_value is not null");
            return (Criteria) this;
        }

        public Criteria andUpMinValueEqualTo(Long value) {
            addCriterion("up_min_value =", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueNotEqualTo(Long value) {
            addCriterion("up_min_value <>", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueGreaterThan(Long value) {
            addCriterion("up_min_value >", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueGreaterThanOrEqualTo(Long value) {
            addCriterion("up_min_value >=", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueLessThan(Long value) {
            addCriterion("up_min_value <", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueLessThanOrEqualTo(Long value) {
            addCriterion("up_min_value <=", value, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueIn(List<Long> values) {
            addCriterion("up_min_value in", values, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueNotIn(List<Long> values) {
            addCriterion("up_min_value not in", values, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueBetween(Long value1, Long value2) {
            addCriterion("up_min_value between", value1, value2, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andUpMinValueNotBetween(Long value1, Long value2) {
            addCriterion("up_min_value not between", value1, value2, "upMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueIsNull() {
            addCriterion("down_max_value is null");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueIsNotNull() {
            addCriterion("down_max_value is not null");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueEqualTo(Long value) {
            addCriterion("down_max_value =", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueNotEqualTo(Long value) {
            addCriterion("down_max_value <>", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueGreaterThan(Long value) {
            addCriterion("down_max_value >", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueGreaterThanOrEqualTo(Long value) {
            addCriterion("down_max_value >=", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueLessThan(Long value) {
            addCriterion("down_max_value <", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueLessThanOrEqualTo(Long value) {
            addCriterion("down_max_value <=", value, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueIn(List<Long> values) {
            addCriterion("down_max_value in", values, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueNotIn(List<Long> values) {
            addCriterion("down_max_value not in", values, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueBetween(Long value1, Long value2) {
            addCriterion("down_max_value between", value1, value2, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownMaxValueNotBetween(Long value1, Long value2) {
            addCriterion("down_max_value not between", value1, value2, "downMaxValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueIsNull() {
            addCriterion("down_warn_value is null");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueIsNotNull() {
            addCriterion("down_warn_value is not null");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueEqualTo(Long value) {
            addCriterion("down_warn_value =", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueNotEqualTo(Long value) {
            addCriterion("down_warn_value <>", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueGreaterThan(Long value) {
            addCriterion("down_warn_value >", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueGreaterThanOrEqualTo(Long value) {
            addCriterion("down_warn_value >=", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueLessThan(Long value) {
            addCriterion("down_warn_value <", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueLessThanOrEqualTo(Long value) {
            addCriterion("down_warn_value <=", value, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueIn(List<Long> values) {
            addCriterion("down_warn_value in", values, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueNotIn(List<Long> values) {
            addCriterion("down_warn_value not in", values, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueBetween(Long value1, Long value2) {
            addCriterion("down_warn_value between", value1, value2, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownWarnValueNotBetween(Long value1, Long value2) {
            addCriterion("down_warn_value not between", value1, value2, "downWarnValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueIsNull() {
            addCriterion("down_min_value is null");
            return (Criteria) this;
        }

        public Criteria andDownMinValueIsNotNull() {
            addCriterion("down_min_value is not null");
            return (Criteria) this;
        }

        public Criteria andDownMinValueEqualTo(Long value) {
            addCriterion("down_min_value =", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueNotEqualTo(Long value) {
            addCriterion("down_min_value <>", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueGreaterThan(Long value) {
            addCriterion("down_min_value >", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueGreaterThanOrEqualTo(Long value) {
            addCriterion("down_min_value >=", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueLessThan(Long value) {
            addCriterion("down_min_value <", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueLessThanOrEqualTo(Long value) {
            addCriterion("down_min_value <=", value, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueIn(List<Long> values) {
            addCriterion("down_min_value in", values, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueNotIn(List<Long> values) {
            addCriterion("down_min_value not in", values, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueBetween(Long value1, Long value2) {
            addCriterion("down_min_value between", value1, value2, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andDownMinValueNotBetween(Long value1, Long value2) {
            addCriterion("down_min_value not between", value1, value2, "downMinValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueIsNull() {
            addCriterion("random_max_value is null");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueIsNotNull() {
            addCriterion("random_max_value is not null");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueEqualTo(Long value) {
            addCriterion("random_max_value =", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueNotEqualTo(Long value) {
            addCriterion("random_max_value <>", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueGreaterThan(Long value) {
            addCriterion("random_max_value >", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueGreaterThanOrEqualTo(Long value) {
            addCriterion("random_max_value >=", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueLessThan(Long value) {
            addCriterion("random_max_value <", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueLessThanOrEqualTo(Long value) {
            addCriterion("random_max_value <=", value, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueIn(List<Long> values) {
            addCriterion("random_max_value in", values, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueNotIn(List<Long> values) {
            addCriterion("random_max_value not in", values, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueBetween(Long value1, Long value2) {
            addCriterion("random_max_value between", value1, value2, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomMaxValueNotBetween(Long value1, Long value2) {
            addCriterion("random_max_value not between", value1, value2, "randomMaxValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueIsNull() {
            addCriterion("random_warn_value is null");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueIsNotNull() {
            addCriterion("random_warn_value is not null");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueEqualTo(Long value) {
            addCriterion("random_warn_value =", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueNotEqualTo(Long value) {
            addCriterion("random_warn_value <>", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueGreaterThan(Long value) {
            addCriterion("random_warn_value >", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueGreaterThanOrEqualTo(Long value) {
            addCriterion("random_warn_value >=", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueLessThan(Long value) {
            addCriterion("random_warn_value <", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueLessThanOrEqualTo(Long value) {
            addCriterion("random_warn_value <=", value, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueIn(List<Long> values) {
            addCriterion("random_warn_value in", values, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueNotIn(List<Long> values) {
            addCriterion("random_warn_value not in", values, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueBetween(Long value1, Long value2) {
            addCriterion("random_warn_value between", value1, value2, "randomWarnValue");
            return (Criteria) this;
        }

        public Criteria andRandomWarnValueNotBetween(Long value1, Long value2) {
            addCriterion("random_warn_value not between", value1, value2, "randomWarnValue");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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