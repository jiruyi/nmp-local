package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmpStationConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmpStationConfExample() {
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

        public Criteria andMainAccessTypeIsNull() {
            addCriterion("main_access_type is null");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeIsNotNull() {
            addCriterion("main_access_type is not null");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeEqualTo(String value) {
            addCriterion("main_access_type =", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeNotEqualTo(String value) {
            addCriterion("main_access_type <>", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeGreaterThan(String value) {
            addCriterion("main_access_type >", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("main_access_type >=", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeLessThan(String value) {
            addCriterion("main_access_type <", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeLessThanOrEqualTo(String value) {
            addCriterion("main_access_type <=", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeLike(String value) {
            addCriterion("main_access_type like", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeNotLike(String value) {
            addCriterion("main_access_type not like", value, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeIn(List<String> values) {
            addCriterion("main_access_type in", values, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeNotIn(List<String> values) {
            addCriterion("main_access_type not in", values, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeBetween(String value1, String value2) {
            addCriterion("main_access_type between", value1, value2, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainAccessTypeNotBetween(String value1, String value2) {
            addCriterion("main_access_type not between", value1, value2, "mainAccessType");
            return (Criteria) this;
        }

        public Criteria andMainCommIpIsNull() {
            addCriterion("main_comm_ip is null");
            return (Criteria) this;
        }

        public Criteria andMainCommIpIsNotNull() {
            addCriterion("main_comm_ip is not null");
            return (Criteria) this;
        }

        public Criteria andMainCommIpEqualTo(String value) {
            addCriterion("main_comm_ip =", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpNotEqualTo(String value) {
            addCriterion("main_comm_ip <>", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpGreaterThan(String value) {
            addCriterion("main_comm_ip >", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpGreaterThanOrEqualTo(String value) {
            addCriterion("main_comm_ip >=", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpLessThan(String value) {
            addCriterion("main_comm_ip <", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpLessThanOrEqualTo(String value) {
            addCriterion("main_comm_ip <=", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpLike(String value) {
            addCriterion("main_comm_ip like", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpNotLike(String value) {
            addCriterion("main_comm_ip not like", value, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpIn(List<String> values) {
            addCriterion("main_comm_ip in", values, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpNotIn(List<String> values) {
            addCriterion("main_comm_ip not in", values, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpBetween(String value1, String value2) {
            addCriterion("main_comm_ip between", value1, value2, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainCommIpNotBetween(String value1, String value2) {
            addCriterion("main_comm_ip not between", value1, value2, "mainCommIp");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameIsNull() {
            addCriterion("main_domain_name is null");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameIsNotNull() {
            addCriterion("main_domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameEqualTo(String value) {
            addCriterion("main_domain_name =", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameNotEqualTo(String value) {
            addCriterion("main_domain_name <>", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameGreaterThan(String value) {
            addCriterion("main_domain_name >", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("main_domain_name >=", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameLessThan(String value) {
            addCriterion("main_domain_name <", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameLessThanOrEqualTo(String value) {
            addCriterion("main_domain_name <=", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameLike(String value) {
            addCriterion("main_domain_name like", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameNotLike(String value) {
            addCriterion("main_domain_name not like", value, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameIn(List<String> values) {
            addCriterion("main_domain_name in", values, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameNotIn(List<String> values) {
            addCriterion("main_domain_name not in", values, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameBetween(String value1, String value2) {
            addCriterion("main_domain_name between", value1, value2, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainDomainNameNotBetween(String value1, String value2) {
            addCriterion("main_domain_name not between", value1, value2, "mainDomainName");
            return (Criteria) this;
        }

        public Criteria andMainPortIsNull() {
            addCriterion("main_port is null");
            return (Criteria) this;
        }

        public Criteria andMainPortIsNotNull() {
            addCriterion("main_port is not null");
            return (Criteria) this;
        }

        public Criteria andMainPortEqualTo(String value) {
            addCriterion("main_port =", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortNotEqualTo(String value) {
            addCriterion("main_port <>", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortGreaterThan(String value) {
            addCriterion("main_port >", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortGreaterThanOrEqualTo(String value) {
            addCriterion("main_port >=", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortLessThan(String value) {
            addCriterion("main_port <", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortLessThanOrEqualTo(String value) {
            addCriterion("main_port <=", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortLike(String value) {
            addCriterion("main_port like", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortNotLike(String value) {
            addCriterion("main_port not like", value, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortIn(List<String> values) {
            addCriterion("main_port in", values, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortNotIn(List<String> values) {
            addCriterion("main_port not in", values, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortBetween(String value1, String value2) {
            addCriterion("main_port between", value1, value2, "mainPort");
            return (Criteria) this;
        }

        public Criteria andMainPortNotBetween(String value1, String value2) {
            addCriterion("main_port not between", value1, value2, "mainPort");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeIsNull() {
            addCriterion("spare_access_type is null");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeIsNotNull() {
            addCriterion("spare_access_type is not null");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeEqualTo(String value) {
            addCriterion("spare_access_type =", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeNotEqualTo(String value) {
            addCriterion("spare_access_type <>", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeGreaterThan(String value) {
            addCriterion("spare_access_type >", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("spare_access_type >=", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeLessThan(String value) {
            addCriterion("spare_access_type <", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeLessThanOrEqualTo(String value) {
            addCriterion("spare_access_type <=", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeLike(String value) {
            addCriterion("spare_access_type like", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeNotLike(String value) {
            addCriterion("spare_access_type not like", value, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeIn(List<String> values) {
            addCriterion("spare_access_type in", values, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeNotIn(List<String> values) {
            addCriterion("spare_access_type not in", values, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeBetween(String value1, String value2) {
            addCriterion("spare_access_type between", value1, value2, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareAccessTypeNotBetween(String value1, String value2) {
            addCriterion("spare_access_type not between", value1, value2, "spareAccessType");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpIsNull() {
            addCriterion("spare_comm_ip is null");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpIsNotNull() {
            addCriterion("spare_comm_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpEqualTo(String value) {
            addCriterion("spare_comm_ip =", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpNotEqualTo(String value) {
            addCriterion("spare_comm_ip <>", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpGreaterThan(String value) {
            addCriterion("spare_comm_ip >", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpGreaterThanOrEqualTo(String value) {
            addCriterion("spare_comm_ip >=", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpLessThan(String value) {
            addCriterion("spare_comm_ip <", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpLessThanOrEqualTo(String value) {
            addCriterion("spare_comm_ip <=", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpLike(String value) {
            addCriterion("spare_comm_ip like", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpNotLike(String value) {
            addCriterion("spare_comm_ip not like", value, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpIn(List<String> values) {
            addCriterion("spare_comm_ip in", values, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpNotIn(List<String> values) {
            addCriterion("spare_comm_ip not in", values, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpBetween(String value1, String value2) {
            addCriterion("spare_comm_ip between", value1, value2, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareCommIpNotBetween(String value1, String value2) {
            addCriterion("spare_comm_ip not between", value1, value2, "spareCommIp");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameIsNull() {
            addCriterion("spare_domain_name is null");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameIsNotNull() {
            addCriterion("spare_domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameEqualTo(String value) {
            addCriterion("spare_domain_name =", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameNotEqualTo(String value) {
            addCriterion("spare_domain_name <>", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameGreaterThan(String value) {
            addCriterion("spare_domain_name >", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("spare_domain_name >=", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameLessThan(String value) {
            addCriterion("spare_domain_name <", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameLessThanOrEqualTo(String value) {
            addCriterion("spare_domain_name <=", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameLike(String value) {
            addCriterion("spare_domain_name like", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameNotLike(String value) {
            addCriterion("spare_domain_name not like", value, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameIn(List<String> values) {
            addCriterion("spare_domain_name in", values, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameNotIn(List<String> values) {
            addCriterion("spare_domain_name not in", values, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameBetween(String value1, String value2) {
            addCriterion("spare_domain_name between", value1, value2, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSpareDomainNameNotBetween(String value1, String value2) {
            addCriterion("spare_domain_name not between", value1, value2, "spareDomainName");
            return (Criteria) this;
        }

        public Criteria andSparePortIsNull() {
            addCriterion("spare_port is null");
            return (Criteria) this;
        }

        public Criteria andSparePortIsNotNull() {
            addCriterion("spare_port is not null");
            return (Criteria) this;
        }

        public Criteria andSparePortEqualTo(String value) {
            addCriterion("spare_port =", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortNotEqualTo(String value) {
            addCriterion("spare_port <>", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortGreaterThan(String value) {
            addCriterion("spare_port >", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortGreaterThanOrEqualTo(String value) {
            addCriterion("spare_port >=", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortLessThan(String value) {
            addCriterion("spare_port <", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortLessThanOrEqualTo(String value) {
            addCriterion("spare_port <=", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortLike(String value) {
            addCriterion("spare_port like", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortNotLike(String value) {
            addCriterion("spare_port not like", value, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortIn(List<String> values) {
            addCriterion("spare_port in", values, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortNotIn(List<String> values) {
            addCriterion("spare_port not in", values, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortBetween(String value1, String value2) {
            addCriterion("spare_port between", value1, value2, "sparePort");
            return (Criteria) this;
        }

        public Criteria andSparePortNotBetween(String value1, String value2) {
            addCriterion("spare_port not between", value1, value2, "sparePort");
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