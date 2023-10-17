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
import com.matrictime.network.request.SelectRequest;
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

        //起止id
        Long startId = 0l;
        Long endId = startId+DataConstants.ALARM_INFO_EVERY_COUNT;
        //1.0 查询上次推送到的位置
        NmplDataPushRecordExample pushRecordExample = new NmplDataPushRecordExample();
        pushRecordExample.createCriteria().andTableNameEqualTo(DataConstants.NMPL_COMPANY_HEARTBEAT);
        pushRecordExample.setOrderByClause("id desc");
        List<NmplDataPushRecord> dataPushRecords = dataPushRecordMapper.selectByExample(pushRecordExample);
        //2.0 配置最新的起止id
        if(!CollectionUtils.isEmpty(dataPushRecords)){
            Long lastId = dataPushRecords.get(0).getDataId();
            startId= lastId;
            endId = endId +startId;
        }
        SelectRequest selectRequest = new SelectRequest();
        selectRequest.setStartId(startId);
        selectRequest.setEndId(endId);
        List<NmplCompanyHeartbeat> nmplCompanyHeartbeats = companyHeartbeatExtMapper.selectCompanyHeartbeat(selectRequest);

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
