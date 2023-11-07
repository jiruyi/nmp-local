package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmpsSecurityServerInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmpsSecurityServerInfoExample() {
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

        public Criteria andServerNameIsNull() {
            addCriterion("server_name is null");
            return (Criteria) this;
        }

        public Criteria andServerNameIsNotNull() {
            addCriterion("server_name is not null");
            return (Criteria) this;
        }

        public Criteria andServerNameEqualTo(String value) {
            addCriterion("server_name =", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotEqualTo(String value) {
            addCriterion("server_name <>", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThan(String value) {
            addCriterion("server_name >", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameGreaterThanOrEqualTo(String value) {
            addCriterion("server_name >=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThan(String value) {
            addCriterion("server_name <", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLessThanOrEqualTo(String value) {
            addCriterion("server_name <=", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameLike(String value) {
            addCriterion("server_name like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotLike(String value) {
            addCriterion("server_name not like", value, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameIn(List<String> values) {
            addCriterion("server_name in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotIn(List<String> values) {
            addCriterion("server_name not in", values, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameBetween(String value1, String value2) {
            addCriterion("server_name between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andServerNameNotBetween(String value1, String value2) {
            addCriterion("server_name not between", value1, value2, "serverName");
            return (Criteria) this;
        }

        public Criteria andComIpIsNull() {
            addCriterion("com_ip is null");
            return (Criteria) this;
        }

        public Criteria andComIpIsNotNull() {
            addCriterion("com_ip is not null");
            return (Criteria) this;
        }

        public Criteria andComIpEqualTo(String value) {
            addCriterion("com_ip =", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpNotEqualTo(String value) {
            addCriterion("com_ip <>", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpGreaterThan(String value) {
            addCriterion("com_ip >", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpGreaterThanOrEqualTo(String value) {
            addCriterion("com_ip >=", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpLessThan(String value) {
            addCriterion("com_ip <", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpLessThanOrEqualTo(String value) {
            addCriterion("com_ip <=", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpLike(String value) {
            addCriterion("com_ip like", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpNotLike(String value) {
            addCriterion("com_ip not like", value, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpIn(List<String> values) {
            addCriterion("com_ip in", values, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpNotIn(List<String> values) {
            addCriterion("com_ip not in", values, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpBetween(String value1, String value2) {
            addCriterion("com_ip between", value1, value2, "comIp");
            return (Criteria) this;
        }

        public Criteria andComIpNotBetween(String value1, String value2) {
            addCriterion("com_ip not between", value1, value2, "comIp");
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

        public Criteria andSignalPortIsNull() {
            addCriterion("signal_port is null");
            return (Criteria) this;
        }

        public Criteria andSignalPortIsNotNull() {
            addCriterion("signal_port is not null");
            return (Criteria) this;
        }

        public Criteria andSignalPortEqualTo(String value) {
            addCriterion("signal_port =", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortNotEqualTo(String value) {
            addCriterion("signal_port <>", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortGreaterThan(String value) {
            addCriterion("signal_port >", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortGreaterThanOrEqualTo(String value) {
            addCriterion("signal_port >=", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortLessThan(String value) {
            addCriterion("signal_port <", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortLessThanOrEqualTo(String value) {
            addCriterion("signal_port <=", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortLike(String value) {
            addCriterion("signal_port like", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortNotLike(String value) {
            addCriterion("signal_port not like", value, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortIn(List<String> values) {
            addCriterion("signal_port in", values, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortNotIn(List<String> values) {
            addCriterion("signal_port not in", values, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortBetween(String value1, String value2) {
            addCriterion("signal_port between", value1, value2, "signalPort");
            return (Criteria) this;
        }

        public Criteria andSignalPortNotBetween(String value1, String value2) {
            addCriterion("signal_port not between", value1, value2, "signalPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortIsNull() {
            addCriterion("key_port is null");
            return (Criteria) this;
        }

        public Criteria andKeyPortIsNotNull() {
            addCriterion("key_port is not null");
            return (Criteria) this;
        }

        public Criteria andKeyPortEqualTo(String value) {
            addCriterion("key_port =", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortNotEqualTo(String value) {
            addCriterion("key_port <>", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortGreaterThan(String value) {
            addCriterion("key_port >", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortGreaterThanOrEqualTo(String value) {
            addCriterion("key_port >=", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortLessThan(String value) {
            addCriterion("key_port <", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortLessThanOrEqualTo(String value) {
            addCriterion("key_port <=", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortLike(String value) {
            addCriterion("key_port like", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortNotLike(String value) {
            addCriterion("key_port not like", value, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortIn(List<String> values) {
            addCriterion("key_port in", values, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortNotIn(List<String> values) {
            addCriterion("key_port not in", values, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortBetween(String value1, String value2) {
            addCriterion("key_port between", value1, value2, "keyPort");
            return (Criteria) this;
        }

        public Criteria andKeyPortNotBetween(String value1, String value2) {
            addCriterion("key_port not between", value1, value2, "keyPort");
            return (Criteria) this;
        }

        public Criteria andConnectTypeIsNull() {
            addCriterion("connect_type is null");
            return (Criteria) this;
        }

        public Criteria andConnectTypeIsNotNull() {
            addCriterion("connect_type is not null");
            return (Criteria) this;
        }

        public Criteria andConnectTypeEqualTo(String value) {
            addCriterion("connect_type =", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotEqualTo(String value) {
            addCriterion("connect_type <>", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeGreaterThan(String value) {
            addCriterion("connect_type >", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("connect_type >=", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLessThan(String value) {
            addCriterion("connect_type <", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLessThanOrEqualTo(String value) {
            addCriterion("connect_type <=", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeLike(String value) {
            addCriterion("connect_type like", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotLike(String value) {
            addCriterion("connect_type not like", value, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeIn(List<String> values) {
            addCriterion("connect_type in", values, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotIn(List<String> values) {
            addCriterion("connect_type not in", values, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeBetween(String value1, String value2) {
            addCriterion("connect_type between", value1, value2, "connectType");
            return (Criteria) this;
        }

        public Criteria andConnectTypeNotBetween(String value1, String value2) {
            addCriterion("connect_type not between", value1, value2, "connectType");
            return (Criteria) this;
        }

        public Criteria andServerStatusIsNull() {
            addCriterion("server_status is null");
            return (Criteria) this;
        }

        public Criteria andServerStatusIsNotNull() {
            addCriterion("server_status is not null");
            return (Criteria) this;
        }

        public Criteria andServerStatusEqualTo(String value) {
            addCriterion("server_status =", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusNotEqualTo(String value) {
            addCriterion("server_status <>", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusGreaterThan(String value) {
            addCriterion("server_status >", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusGreaterThanOrEqualTo(String value) {
            addCriterion("server_status >=", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusLessThan(String value) {
            addCriterion("server_status <", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusLessThanOrEqualTo(String value) {
            addCriterion("server_status <=", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusLike(String value) {
            addCriterion("server_status like", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusNotLike(String value) {
            addCriterion("server_status not like", value, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusIn(List<String> values) {
            addCriterion("server_status in", values, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusNotIn(List<String> values) {
            addCriterion("server_status not in", values, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusBetween(String value1, String value2) {
            addCriterion("server_status between", value1, value2, "serverStatus");
            return (Criteria) this;
        }

        public Criteria andServerStatusNotBetween(String value1, String value2) {
            addCriterion("server_status not between", value1, value2, "serverStatus");
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