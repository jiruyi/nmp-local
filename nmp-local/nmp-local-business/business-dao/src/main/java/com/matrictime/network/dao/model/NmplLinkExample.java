package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplLinkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplLinkExample() {
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

        public Criteria andLinkNameIsNull() {
            addCriterion("link_name is null");
            return (Criteria) this;
        }

        public Criteria andLinkNameIsNotNull() {
            addCriterion("link_name is not null");
            return (Criteria) this;
        }

        public Criteria andLinkNameEqualTo(String value) {
            addCriterion("link_name =", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotEqualTo(String value) {
            addCriterion("link_name <>", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameGreaterThan(String value) {
            addCriterion("link_name >", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameGreaterThanOrEqualTo(String value) {
            addCriterion("link_name >=", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLessThan(String value) {
            addCriterion("link_name <", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLessThanOrEqualTo(String value) {
            addCriterion("link_name <=", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameLike(String value) {
            addCriterion("link_name like", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotLike(String value) {
            addCriterion("link_name not like", value, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameIn(List<String> values) {
            addCriterion("link_name in", values, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotIn(List<String> values) {
            addCriterion("link_name not in", values, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameBetween(String value1, String value2) {
            addCriterion("link_name between", value1, value2, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkNameNotBetween(String value1, String value2) {
            addCriterion("link_name not between", value1, value2, "linkName");
            return (Criteria) this;
        }

        public Criteria andLinkTypeIsNull() {
            addCriterion("link_type is null");
            return (Criteria) this;
        }

        public Criteria andLinkTypeIsNotNull() {
            addCriterion("link_type is not null");
            return (Criteria) this;
        }

        public Criteria andLinkTypeEqualTo(Short value) {
            addCriterion("link_type =", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotEqualTo(Short value) {
            addCriterion("link_type <>", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeGreaterThan(Short value) {
            addCriterion("link_type >", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("link_type >=", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeLessThan(Short value) {
            addCriterion("link_type <", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeLessThanOrEqualTo(Short value) {
            addCriterion("link_type <=", value, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeIn(List<Short> values) {
            addCriterion("link_type in", values, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotIn(List<Short> values) {
            addCriterion("link_type not in", values, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeBetween(Short value1, Short value2) {
            addCriterion("link_type between", value1, value2, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkTypeNotBetween(Short value1, Short value2) {
            addCriterion("link_type not between", value1, value2, "linkType");
            return (Criteria) this;
        }

        public Criteria andLinkRelationIsNull() {
            addCriterion("link_relation is null");
            return (Criteria) this;
        }

        public Criteria andLinkRelationIsNotNull() {
            addCriterion("link_relation is not null");
            return (Criteria) this;
        }

        public Criteria andLinkRelationEqualTo(String value) {
            addCriterion("link_relation =", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationNotEqualTo(String value) {
            addCriterion("link_relation <>", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationGreaterThan(String value) {
            addCriterion("link_relation >", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationGreaterThanOrEqualTo(String value) {
            addCriterion("link_relation >=", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationLessThan(String value) {
            addCriterion("link_relation <", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationLessThanOrEqualTo(String value) {
            addCriterion("link_relation <=", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationLike(String value) {
            addCriterion("link_relation like", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationNotLike(String value) {
            addCriterion("link_relation not like", value, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationIn(List<String> values) {
            addCriterion("link_relation in", values, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationNotIn(List<String> values) {
            addCriterion("link_relation not in", values, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationBetween(String value1, String value2) {
            addCriterion("link_relation between", value1, value2, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andLinkRelationNotBetween(String value1, String value2) {
            addCriterion("link_relation not between", value1, value2, "linkRelation");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdIsNull() {
            addCriterion("main_device_id is null");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdIsNotNull() {
            addCriterion("main_device_id is not null");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdEqualTo(String value) {
            addCriterion("main_device_id =", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdNotEqualTo(String value) {
            addCriterion("main_device_id <>", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdGreaterThan(String value) {
            addCriterion("main_device_id >", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdGreaterThanOrEqualTo(String value) {
            addCriterion("main_device_id >=", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdLessThan(String value) {
            addCriterion("main_device_id <", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdLessThanOrEqualTo(String value) {
            addCriterion("main_device_id <=", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdLike(String value) {
            addCriterion("main_device_id like", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdNotLike(String value) {
            addCriterion("main_device_id not like", value, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdIn(List<String> values) {
            addCriterion("main_device_id in", values, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdNotIn(List<String> values) {
            addCriterion("main_device_id not in", values, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdBetween(String value1, String value2) {
            addCriterion("main_device_id between", value1, value2, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andMainDeviceIdNotBetween(String value1, String value2) {
            addCriterion("main_device_id not between", value1, value2, "mainDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdIsNull() {
            addCriterion("follow_device_id is null");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdIsNotNull() {
            addCriterion("follow_device_id is not null");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdEqualTo(String value) {
            addCriterion("follow_device_id =", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdNotEqualTo(String value) {
            addCriterion("follow_device_id <>", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdGreaterThan(String value) {
            addCriterion("follow_device_id >", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdGreaterThanOrEqualTo(String value) {
            addCriterion("follow_device_id >=", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdLessThan(String value) {
            addCriterion("follow_device_id <", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdLessThanOrEqualTo(String value) {
            addCriterion("follow_device_id <=", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdLike(String value) {
            addCriterion("follow_device_id like", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdNotLike(String value) {
            addCriterion("follow_device_id not like", value, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdIn(List<String> values) {
            addCriterion("follow_device_id in", values, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdNotIn(List<String> values) {
            addCriterion("follow_device_id not in", values, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdBetween(String value1, String value2) {
            addCriterion("follow_device_id between", value1, value2, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceIdNotBetween(String value1, String value2) {
            addCriterion("follow_device_id not between", value1, value2, "followDeviceId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdIsNull() {
            addCriterion("follow_network_id is null");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdIsNotNull() {
            addCriterion("follow_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdEqualTo(String value) {
            addCriterion("follow_network_id =", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdNotEqualTo(String value) {
            addCriterion("follow_network_id <>", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdGreaterThan(String value) {
            addCriterion("follow_network_id >", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("follow_network_id >=", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdLessThan(String value) {
            addCriterion("follow_network_id <", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("follow_network_id <=", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdLike(String value) {
            addCriterion("follow_network_id like", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdNotLike(String value) {
            addCriterion("follow_network_id not like", value, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdIn(List<String> values) {
            addCriterion("follow_network_id in", values, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdNotIn(List<String> values) {
            addCriterion("follow_network_id not in", values, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdBetween(String value1, String value2) {
            addCriterion("follow_network_id between", value1, value2, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowNetworkIdNotBetween(String value1, String value2) {
            addCriterion("follow_network_id not between", value1, value2, "followNetworkId");
            return (Criteria) this;
        }

        public Criteria andFollowIpIsNull() {
            addCriterion("follow_ip is null");
            return (Criteria) this;
        }

        public Criteria andFollowIpIsNotNull() {
            addCriterion("follow_ip is not null");
            return (Criteria) this;
        }

        public Criteria andFollowIpEqualTo(String value) {
            addCriterion("follow_ip =", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpNotEqualTo(String value) {
            addCriterion("follow_ip <>", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpGreaterThan(String value) {
            addCriterion("follow_ip >", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpGreaterThanOrEqualTo(String value) {
            addCriterion("follow_ip >=", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpLessThan(String value) {
            addCriterion("follow_ip <", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpLessThanOrEqualTo(String value) {
            addCriterion("follow_ip <=", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpLike(String value) {
            addCriterion("follow_ip like", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpNotLike(String value) {
            addCriterion("follow_ip not like", value, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpIn(List<String> values) {
            addCriterion("follow_ip in", values, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpNotIn(List<String> values) {
            addCriterion("follow_ip not in", values, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpBetween(String value1, String value2) {
            addCriterion("follow_ip between", value1, value2, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowIpNotBetween(String value1, String value2) {
            addCriterion("follow_ip not between", value1, value2, "followIp");
            return (Criteria) this;
        }

        public Criteria andFollowPortIsNull() {
            addCriterion("follow_port is null");
            return (Criteria) this;
        }

        public Criteria andFollowPortIsNotNull() {
            addCriterion("follow_port is not null");
            return (Criteria) this;
        }

        public Criteria andFollowPortEqualTo(String value) {
            addCriterion("follow_port =", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortNotEqualTo(String value) {
            addCriterion("follow_port <>", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortGreaterThan(String value) {
            addCriterion("follow_port >", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortGreaterThanOrEqualTo(String value) {
            addCriterion("follow_port >=", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortLessThan(String value) {
            addCriterion("follow_port <", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortLessThanOrEqualTo(String value) {
            addCriterion("follow_port <=", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortLike(String value) {
            addCriterion("follow_port like", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortNotLike(String value) {
            addCriterion("follow_port not like", value, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortIn(List<String> values) {
            addCriterion("follow_port in", values, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortNotIn(List<String> values) {
            addCriterion("follow_port not in", values, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortBetween(String value1, String value2) {
            addCriterion("follow_port between", value1, value2, "followPort");
            return (Criteria) this;
        }

        public Criteria andFollowPortNotBetween(String value1, String value2) {
            addCriterion("follow_port not between", value1, value2, "followPort");
            return (Criteria) this;
        }

        public Criteria andActiveAuthIsNull() {
            addCriterion("active_auth is null");
            return (Criteria) this;
        }

        public Criteria andActiveAuthIsNotNull() {
            addCriterion("active_auth is not null");
            return (Criteria) this;
        }

        public Criteria andActiveAuthEqualTo(Boolean value) {
            addCriterion("active_auth =", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthNotEqualTo(Boolean value) {
            addCriterion("active_auth <>", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthGreaterThan(Boolean value) {
            addCriterion("active_auth >", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthGreaterThanOrEqualTo(Boolean value) {
            addCriterion("active_auth >=", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthLessThan(Boolean value) {
            addCriterion("active_auth <", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthLessThanOrEqualTo(Boolean value) {
            addCriterion("active_auth <=", value, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthIn(List<Boolean> values) {
            addCriterion("active_auth in", values, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthNotIn(List<Boolean> values) {
            addCriterion("active_auth not in", values, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthBetween(Boolean value1, Boolean value2) {
            addCriterion("active_auth between", value1, value2, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andActiveAuthNotBetween(Boolean value1, Boolean value2) {
            addCriterion("active_auth not between", value1, value2, "activeAuth");
            return (Criteria) this;
        }

        public Criteria andIsOnIsNull() {
            addCriterion("is_on is null");
            return (Criteria) this;
        }

        public Criteria andIsOnIsNotNull() {
            addCriterion("is_on is not null");
            return (Criteria) this;
        }

        public Criteria andIsOnEqualTo(Boolean value) {
            addCriterion("is_on =", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnNotEqualTo(Boolean value) {
            addCriterion("is_on <>", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnGreaterThan(Boolean value) {
            addCriterion("is_on >", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_on >=", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnLessThan(Boolean value) {
            addCriterion("is_on <", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnLessThanOrEqualTo(Boolean value) {
            addCriterion("is_on <=", value, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnIn(List<Boolean> values) {
            addCriterion("is_on in", values, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnNotIn(List<Boolean> values) {
            addCriterion("is_on not in", values, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on between", value1, value2, "isOn");
            return (Criteria) this;
        }

        public Criteria andIsOnNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_on not between", value1, value2, "isOn");
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

        public Criteria andFollowDeviceNameIsNull() {
            addCriterion("follow_device_name is null");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameIsNotNull() {
            addCriterion("follow_device_name is not null");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameEqualTo(String value) {
            addCriterion("follow_device_name =", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameNotEqualTo(String value) {
            addCriterion("follow_device_name <>", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameGreaterThan(String value) {
            addCriterion("follow_device_name >", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameGreaterThanOrEqualTo(String value) {
            addCriterion("follow_device_name >=", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameLessThan(String value) {
            addCriterion("follow_device_name <", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameLessThanOrEqualTo(String value) {
            addCriterion("follow_device_name <=", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameLike(String value) {
            addCriterion("follow_device_name like", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameNotLike(String value) {
            addCriterion("follow_device_name not like", value, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameIn(List<String> values) {
            addCriterion("follow_device_name in", values, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameNotIn(List<String> values) {
            addCriterion("follow_device_name not in", values, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameBetween(String value1, String value2) {
            addCriterion("follow_device_name between", value1, value2, "followDeviceName");
            return (Criteria) this;
        }

        public Criteria andFollowDeviceNameNotBetween(String value1, String value2) {
            addCriterion("follow_device_name not between", value1, value2, "followDeviceName");
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