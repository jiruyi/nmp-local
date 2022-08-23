package com.matrictime.network.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmplDeviceInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NmplDeviceInfoExample() {
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

        public Criteria andDeviceIdIsNull() {
            addCriterion("device_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIsNotNull() {
            addCriterion("device_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIdEqualTo(String value) {
            addCriterion("device_id =", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotEqualTo(String value) {
            addCriterion("device_id <>", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThan(String value) {
            addCriterion("device_id >", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdGreaterThanOrEqualTo(String value) {
            addCriterion("device_id >=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThan(String value) {
            addCriterion("device_id <", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLessThanOrEqualTo(String value) {
            addCriterion("device_id <=", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdLike(String value) {
            addCriterion("device_id like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotLike(String value) {
            addCriterion("device_id not like", value, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdIn(List<String> values) {
            addCriterion("device_id in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotIn(List<String> values) {
            addCriterion("device_id not in", values, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdBetween(String value1, String value2) {
            addCriterion("device_id between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceIdNotBetween(String value1, String value2) {
            addCriterion("device_id not between", value1, value2, "deviceId");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIsNull() {
            addCriterion("device_name is null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIsNotNull() {
            addCriterion("device_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameEqualTo(String value) {
            addCriterion("device_name =", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotEqualTo(String value) {
            addCriterion("device_name <>", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThan(String value) {
            addCriterion("device_name >", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThanOrEqualTo(String value) {
            addCriterion("device_name >=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThan(String value) {
            addCriterion("device_name <", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThanOrEqualTo(String value) {
            addCriterion("device_name <=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLike(String value) {
            addCriterion("device_name like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotLike(String value) {
            addCriterion("device_name not like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIn(List<String> values) {
            addCriterion("device_name in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotIn(List<String> values) {
            addCriterion("device_name not in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameBetween(String value1, String value2) {
            addCriterion("device_name between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotBetween(String value1, String value2) {
            addCriterion("device_name not between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("device_type is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("device_type is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(String value) {
            addCriterion("device_type =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(String value) {
            addCriterion("device_type <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(String value) {
            addCriterion("device_type >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("device_type >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(String value) {
            addCriterion("device_type <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(String value) {
            addCriterion("device_type <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLike(String value) {
            addCriterion("device_type like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotLike(String value) {
            addCriterion("device_type not like", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<String> values) {
            addCriterion("device_type in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<String> values) {
            addCriterion("device_type not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(String value1, String value2) {
            addCriterion("device_type between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(String value1, String value2) {
            addCriterion("device_type not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeIsNull() {
            addCriterion("other_type is null");
            return (Criteria) this;
        }

        public Criteria andOtherTypeIsNotNull() {
            addCriterion("other_type is not null");
            return (Criteria) this;
        }

        public Criteria andOtherTypeEqualTo(String value) {
            addCriterion("other_type =", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeNotEqualTo(String value) {
            addCriterion("other_type <>", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeGreaterThan(String value) {
            addCriterion("other_type >", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeGreaterThanOrEqualTo(String value) {
            addCriterion("other_type >=", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeLessThan(String value) {
            addCriterion("other_type <", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeLessThanOrEqualTo(String value) {
            addCriterion("other_type <=", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeLike(String value) {
            addCriterion("other_type like", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeNotLike(String value) {
            addCriterion("other_type not like", value, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeIn(List<String> values) {
            addCriterion("other_type in", values, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeNotIn(List<String> values) {
            addCriterion("other_type not in", values, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeBetween(String value1, String value2) {
            addCriterion("other_type between", value1, value2, "otherType");
            return (Criteria) this;
        }

        public Criteria andOtherTypeNotBetween(String value1, String value2) {
            addCriterion("other_type not between", value1, value2, "otherType");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeIsNull() {
            addCriterion("enter_network_time is null");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeIsNotNull() {
            addCriterion("enter_network_time is not null");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeEqualTo(Date value) {
            addCriterion("enter_network_time =", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeNotEqualTo(Date value) {
            addCriterion("enter_network_time <>", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeGreaterThan(Date value) {
            addCriterion("enter_network_time >", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("enter_network_time >=", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeLessThan(Date value) {
            addCriterion("enter_network_time <", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeLessThanOrEqualTo(Date value) {
            addCriterion("enter_network_time <=", value, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeIn(List<Date> values) {
            addCriterion("enter_network_time in", values, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeNotIn(List<Date> values) {
            addCriterion("enter_network_time not in", values, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeBetween(Date value1, Date value2) {
            addCriterion("enter_network_time between", value1, value2, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andEnterNetworkTimeNotBetween(Date value1, Date value2) {
            addCriterion("enter_network_time not between", value1, value2, "enterNetworkTime");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainIsNull() {
            addCriterion("device_admain is null");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainIsNotNull() {
            addCriterion("device_admain is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainEqualTo(String value) {
            addCriterion("device_admain =", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainNotEqualTo(String value) {
            addCriterion("device_admain <>", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainGreaterThan(String value) {
            addCriterion("device_admain >", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainGreaterThanOrEqualTo(String value) {
            addCriterion("device_admain >=", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainLessThan(String value) {
            addCriterion("device_admain <", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainLessThanOrEqualTo(String value) {
            addCriterion("device_admain <=", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainLike(String value) {
            addCriterion("device_admain like", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainNotLike(String value) {
            addCriterion("device_admain not like", value, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainIn(List<String> values) {
            addCriterion("device_admain in", values, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainNotIn(List<String> values) {
            addCriterion("device_admain not in", values, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainBetween(String value1, String value2) {
            addCriterion("device_admain between", value1, value2, "deviceAdmain");
            return (Criteria) this;
        }

        public Criteria andDeviceAdmainNotBetween(String value1, String value2) {
            addCriterion("device_admain not between", value1, value2, "deviceAdmain");
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

        public Criteria andPublicNetworkIpIsNull() {
            addCriterion("public_network_ip is null");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpIsNotNull() {
            addCriterion("public_network_ip is not null");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpEqualTo(String value) {
            addCriterion("public_network_ip =", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpNotEqualTo(String value) {
            addCriterion("public_network_ip <>", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpGreaterThan(String value) {
            addCriterion("public_network_ip >", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpGreaterThanOrEqualTo(String value) {
            addCriterion("public_network_ip >=", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpLessThan(String value) {
            addCriterion("public_network_ip <", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpLessThanOrEqualTo(String value) {
            addCriterion("public_network_ip <=", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpLike(String value) {
            addCriterion("public_network_ip like", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpNotLike(String value) {
            addCriterion("public_network_ip not like", value, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpIn(List<String> values) {
            addCriterion("public_network_ip in", values, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpNotIn(List<String> values) {
            addCriterion("public_network_ip not in", values, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpBetween(String value1, String value2) {
            addCriterion("public_network_ip between", value1, value2, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkIpNotBetween(String value1, String value2) {
            addCriterion("public_network_ip not between", value1, value2, "publicNetworkIp");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortIsNull() {
            addCriterion("public_network_port is null");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortIsNotNull() {
            addCriterion("public_network_port is not null");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortEqualTo(String value) {
            addCriterion("public_network_port =", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortNotEqualTo(String value) {
            addCriterion("public_network_port <>", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortGreaterThan(String value) {
            addCriterion("public_network_port >", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortGreaterThanOrEqualTo(String value) {
            addCriterion("public_network_port >=", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortLessThan(String value) {
            addCriterion("public_network_port <", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortLessThanOrEqualTo(String value) {
            addCriterion("public_network_port <=", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortLike(String value) {
            addCriterion("public_network_port like", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortNotLike(String value) {
            addCriterion("public_network_port not like", value, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortIn(List<String> values) {
            addCriterion("public_network_port in", values, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortNotIn(List<String> values) {
            addCriterion("public_network_port not in", values, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortBetween(String value1, String value2) {
            addCriterion("public_network_port between", value1, value2, "publicNetworkPort");
            return (Criteria) this;
        }

        public Criteria andPublicNetworkPortNotBetween(String value1, String value2) {
            addCriterion("public_network_port not between", value1, value2, "publicNetworkPort");
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

        public Criteria andLanPortIsNull() {
            addCriterion("lan_port is null");
            return (Criteria) this;
        }

        public Criteria andLanPortIsNotNull() {
            addCriterion("lan_port is not null");
            return (Criteria) this;
        }

        public Criteria andLanPortEqualTo(String value) {
            addCriterion("lan_port =", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortNotEqualTo(String value) {
            addCriterion("lan_port <>", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortGreaterThan(String value) {
            addCriterion("lan_port >", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortGreaterThanOrEqualTo(String value) {
            addCriterion("lan_port >=", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortLessThan(String value) {
            addCriterion("lan_port <", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortLessThanOrEqualTo(String value) {
            addCriterion("lan_port <=", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortLike(String value) {
            addCriterion("lan_port like", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortNotLike(String value) {
            addCriterion("lan_port not like", value, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortIn(List<String> values) {
            addCriterion("lan_port in", values, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortNotIn(List<String> values) {
            addCriterion("lan_port not in", values, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortBetween(String value1, String value2) {
            addCriterion("lan_port between", value1, value2, "lanPort");
            return (Criteria) this;
        }

        public Criteria andLanPortNotBetween(String value1, String value2) {
            addCriterion("lan_port not between", value1, value2, "lanPort");
            return (Criteria) this;
        }

        public Criteria andStationStatusIsNull() {
            addCriterion("station_status is null");
            return (Criteria) this;
        }

        public Criteria andStationStatusIsNotNull() {
            addCriterion("station_status is not null");
            return (Criteria) this;
        }

        public Criteria andStationStatusEqualTo(String value) {
            addCriterion("station_status =", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusNotEqualTo(String value) {
            addCriterion("station_status <>", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusGreaterThan(String value) {
            addCriterion("station_status >", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusGreaterThanOrEqualTo(String value) {
            addCriterion("station_status >=", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusLessThan(String value) {
            addCriterion("station_status <", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusLessThanOrEqualTo(String value) {
            addCriterion("station_status <=", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusLike(String value) {
            addCriterion("station_status like", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusNotLike(String value) {
            addCriterion("station_status not like", value, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusIn(List<String> values) {
            addCriterion("station_status in", values, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusNotIn(List<String> values) {
            addCriterion("station_status not in", values, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusBetween(String value1, String value2) {
            addCriterion("station_status between", value1, value2, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationStatusNotBetween(String value1, String value2) {
            addCriterion("station_status not between", value1, value2, "stationStatus");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdIsNull() {
            addCriterion("station_network_id is null");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdIsNotNull() {
            addCriterion("station_network_id is not null");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdEqualTo(String value) {
            addCriterion("station_network_id =", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdNotEqualTo(String value) {
            addCriterion("station_network_id <>", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdGreaterThan(String value) {
            addCriterion("station_network_id >", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdGreaterThanOrEqualTo(String value) {
            addCriterion("station_network_id >=", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdLessThan(String value) {
            addCriterion("station_network_id <", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdLessThanOrEqualTo(String value) {
            addCriterion("station_network_id <=", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdLike(String value) {
            addCriterion("station_network_id like", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdNotLike(String value) {
            addCriterion("station_network_id not like", value, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdIn(List<String> values) {
            addCriterion("station_network_id in", values, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdNotIn(List<String> values) {
            addCriterion("station_network_id not in", values, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdBetween(String value1, String value2) {
            addCriterion("station_network_id between", value1, value2, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationNetworkIdNotBetween(String value1, String value2) {
            addCriterion("station_network_id not between", value1, value2, "stationNetworkId");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedIsNull() {
            addCriterion("station_random_seed is null");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedIsNotNull() {
            addCriterion("station_random_seed is not null");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedEqualTo(String value) {
            addCriterion("station_random_seed =", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedNotEqualTo(String value) {
            addCriterion("station_random_seed <>", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedGreaterThan(String value) {
            addCriterion("station_random_seed >", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedGreaterThanOrEqualTo(String value) {
            addCriterion("station_random_seed >=", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedLessThan(String value) {
            addCriterion("station_random_seed <", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedLessThanOrEqualTo(String value) {
            addCriterion("station_random_seed <=", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedLike(String value) {
            addCriterion("station_random_seed like", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedNotLike(String value) {
            addCriterion("station_random_seed not like", value, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedIn(List<String> values) {
            addCriterion("station_random_seed in", values, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedNotIn(List<String> values) {
            addCriterion("station_random_seed not in", values, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedBetween(String value1, String value2) {
            addCriterion("station_random_seed between", value1, value2, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andStationRandomSeedNotBetween(String value1, String value2) {
            addCriterion("station_random_seed not between", value1, value2, "stationRandomSeed");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdIsNull() {
            addCriterion("relation_operator_id is null");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdIsNotNull() {
            addCriterion("relation_operator_id is not null");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdEqualTo(String value) {
            addCriterion("relation_operator_id =", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdNotEqualTo(String value) {
            addCriterion("relation_operator_id <>", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdGreaterThan(String value) {
            addCriterion("relation_operator_id >", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("relation_operator_id >=", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdLessThan(String value) {
            addCriterion("relation_operator_id <", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdLessThanOrEqualTo(String value) {
            addCriterion("relation_operator_id <=", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdLike(String value) {
            addCriterion("relation_operator_id like", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdNotLike(String value) {
            addCriterion("relation_operator_id not like", value, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdIn(List<String> values) {
            addCriterion("relation_operator_id in", values, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdNotIn(List<String> values) {
            addCriterion("relation_operator_id not in", values, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdBetween(String value1, String value2) {
            addCriterion("relation_operator_id between", value1, value2, "relationOperatorId");
            return (Criteria) this;
        }

        public Criteria andRelationOperatorIdNotBetween(String value1, String value2) {
            addCriterion("relation_operator_id not between", value1, value2, "relationOperatorId");
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