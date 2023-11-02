package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmpsCaManageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmpsCaManageExample() {
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

        public Criteria andNetworkIdIsNull() {
            addCriterion("network_id is null");
            return (Criteria) this;
        }

        public Criteria andNetworkIdIsNotNull() {
            addCriterion("network_id is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkIdEqualTo(String value) {
            addCriterion("network_id =", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdNotEqualTo(String value) {
            addCriterion("network_id <>", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdGreaterThan(String value) {
            addCriterion("network_id >", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("network_id >=", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdLessThan(String value) {
            addCriterion("network_id <", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("network_id <=", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdLike(String value) {
            addCriterion("network_id like", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdNotLike(String value) {
            addCriterion("network_id not like", value, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdIn(List<String> values) {
            addCriterion("network_id in", values, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdNotIn(List<String> values) {
            addCriterion("network_id not in", values, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdBetween(String value1, String value2) {
            addCriterion("network_id between", value1, value2, "networkId");
            return (Criteria) this;
        }

        public Criteria andNetworkIdNotBetween(String value1, String value2) {
            addCriterion("network_id not between", value1, value2, "networkId");
            return (Criteria) this;
        }

        public Criteria andAccessMethodIsNull() {
            addCriterion("access_method is null");
            return (Criteria) this;
        }

        public Criteria andAccessMethodIsNotNull() {
            addCriterion("access_method is not null");
            return (Criteria) this;
        }

        public Criteria andAccessMethodEqualTo(String value) {
            addCriterion("access_method =", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodNotEqualTo(String value) {
            addCriterion("access_method <>", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodGreaterThan(String value) {
            addCriterion("access_method >", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodGreaterThanOrEqualTo(String value) {
            addCriterion("access_method >=", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodLessThan(String value) {
            addCriterion("access_method <", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodLessThanOrEqualTo(String value) {
            addCriterion("access_method <=", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodLike(String value) {
            addCriterion("access_method like", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodNotLike(String value) {
            addCriterion("access_method not like", value, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodIn(List<String> values) {
            addCriterion("access_method in", values, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodNotIn(List<String> values) {
            addCriterion("access_method not in", values, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodBetween(String value1, String value2) {
            addCriterion("access_method between", value1, value2, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andAccessMethodNotBetween(String value1, String value2) {
            addCriterion("access_method not between", value1, value2, "accessMethod");
            return (Criteria) this;
        }

        public Criteria andDomainNameIsNull() {
            addCriterion("domain_name is null");
            return (Criteria) this;
        }

        public Criteria andDomainNameIsNotNull() {
            addCriterion("domain_name is not null");
            return (Criteria) this;
        }

        public Criteria andDomainNameEqualTo(String value) {
            addCriterion("domain_name =", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotEqualTo(String value) {
            addCriterion("domain_name <>", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThan(String value) {
            addCriterion("domain_name >", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameGreaterThanOrEqualTo(String value) {
            addCriterion("domain_name >=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThan(String value) {
            addCriterion("domain_name <", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLessThanOrEqualTo(String value) {
            addCriterion("domain_name <=", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameLike(String value) {
            addCriterion("domain_name like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotLike(String value) {
            addCriterion("domain_name not like", value, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameIn(List<String> values) {
            addCriterion("domain_name in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotIn(List<String> values) {
            addCriterion("domain_name not in", values, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameBetween(String value1, String value2) {
            addCriterion("domain_name between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andDomainNameNotBetween(String value1, String value2) {
            addCriterion("domain_name not between", value1, value2, "domainName");
            return (Criteria) this;
        }

        public Criteria andPublicIpIsNull() {
            addCriterion("public_ip is null");
            return (Criteria) this;
        }

        public Criteria andPublicIpIsNotNull() {
            addCriterion("public_ip is not null");
            return (Criteria) this;
        }

        public Criteria andPublicIpEqualTo(String value) {
            addCriterion("public_ip =", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpNotEqualTo(String value) {
            addCriterion("public_ip <>", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpGreaterThan(String value) {
            addCriterion("public_ip >", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpGreaterThanOrEqualTo(String value) {
            addCriterion("public_ip >=", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpLessThan(String value) {
            addCriterion("public_ip <", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpLessThanOrEqualTo(String value) {
            addCriterion("public_ip <=", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpLike(String value) {
            addCriterion("public_ip like", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpNotLike(String value) {
            addCriterion("public_ip not like", value, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpIn(List<String> values) {
            addCriterion("public_ip in", values, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpNotIn(List<String> values) {
            addCriterion("public_ip not in", values, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpBetween(String value1, String value2) {
            addCriterion("public_ip between", value1, value2, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicIpNotBetween(String value1, String value2) {
            addCriterion("public_ip not between", value1, value2, "publicIp");
            return (Criteria) this;
        }

        public Criteria andPublicPortIsNull() {
            addCriterion("public_port is null");
            return (Criteria) this;
        }

        public Criteria andPublicPortIsNotNull() {
            addCriterion("public_port is not null");
            return (Criteria) this;
        }

        public Criteria andPublicPortEqualTo(String value) {
            addCriterion("public_port =", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortNotEqualTo(String value) {
            addCriterion("public_port <>", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortGreaterThan(String value) {
            addCriterion("public_port >", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortGreaterThanOrEqualTo(String value) {
            addCriterion("public_port >=", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortLessThan(String value) {
            addCriterion("public_port <", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortLessThanOrEqualTo(String value) {
            addCriterion("public_port <=", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortLike(String value) {
            addCriterion("public_port like", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortNotLike(String value) {
            addCriterion("public_port not like", value, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortIn(List<String> values) {
            addCriterion("public_port in", values, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortNotIn(List<String> values) {
            addCriterion("public_port not in", values, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortBetween(String value1, String value2) {
            addCriterion("public_port between", value1, value2, "publicPort");
            return (Criteria) this;
        }

        public Criteria andPublicPortNotBetween(String value1, String value2) {
            addCriterion("public_port not between", value1, value2, "publicPort");
            return (Criteria) this;
        }

        public Criteria andLanIpIsNull() {
            addCriterion("lan_ip is null");
            return (Criteria) this;
        }

        public Criteria andLanIpIsNotNull() {
            addCriterion("lan_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLanIpEqualTo(String value) {
            addCriterion("lan_ip =", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpNotEqualTo(String value) {
            addCriterion("lan_ip <>", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpGreaterThan(String value) {
            addCriterion("lan_ip >", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpGreaterThanOrEqualTo(String value) {
            addCriterion("lan_ip >=", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpLessThan(String value) {
            addCriterion("lan_ip <", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpLessThanOrEqualTo(String value) {
            addCriterion("lan_ip <=", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpLike(String value) {
            addCriterion("lan_ip like", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpNotLike(String value) {
            addCriterion("lan_ip not like", value, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpIn(List<String> values) {
            addCriterion("lan_ip in", values, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpNotIn(List<String> values) {
            addCriterion("lan_ip not in", values, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpBetween(String value1, String value2) {
            addCriterion("lan_ip between", value1, value2, "lanIp");
            return (Criteria) this;
        }

        public Criteria andLanIpNotBetween(String value1, String value2) {
            addCriterion("lan_ip not between", value1, value2, "lanIp");
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