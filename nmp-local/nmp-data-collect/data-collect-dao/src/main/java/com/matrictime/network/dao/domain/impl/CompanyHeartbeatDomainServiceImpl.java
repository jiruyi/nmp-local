package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.extend.NmplCompanyHeartbeatExtMapper;
import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.dao.model.NmplDataPushRecord;
import com.matrictime.network.dao.model.NmplDataPushRecordExample;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.CompanyInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service
public class CompanyHeartbeatDomainServiceImpl implements CompanyHeartbeatDomainService {

    @Resource
    private NmplCompanyHeartbeatExtMapper companyHeartbeatExtMapper;

    @Resource
    private NmplDataPushRecordMapper dataPushRecordMapper;

    @Override
    public List<CompanyHeartbeatVo> selectCompanyHeartbeat() {

        List<NmplCompanyHeartbeat> nmplCompanyHeartbeats = companyHeartbeatExtMapper.selectCompanyHeartbeat();
        if(CollectionUtils.isEmpty(nmplCompanyHeartbeats)){
            return null;
        }
        List<CompanyHeartbeatVo> list = new ArrayList<>();
        for(NmplCompanyHeartbeat nmplCompanyHeartbeat: nmplCompanyHeartbeats){
            CompanyHeartbeatVo companyHeartbeatVo = new CompanyHeartbeatVo();
            //获取小区唯一标识
            companyHeartbeatVo.setSourceCompanyNetworkId(NetworkIdUtil.splitNetworkId(nmplCompanyHeartbeat.getSourceNetworkId()));
            companyHeartbeatVo.setTargetCompanyNetworkId(NetworkIdUtil.splitNetworkId(nmplCompanyHeartbeat.getTargetNetworkId()));
            BeanUtils.copyProperties(nmplCompanyHeartbeat,companyHeartbeatVo);
            list.add(companyHeartbeatVo);
        }
        return list;
    }

    @Override
    public int insertDataPushRecord(Long maxId, String businessDataEnum) {
        NmplDataPushRecord record = new NmplDataPushRecord();
        record.setDataId(maxId);
        record.setTableName(businessDataEnum);
        return  dataPushRecordMapper.insertSelective(record);
    }
}
