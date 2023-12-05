package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.extend.NmplCompanyHeartbeatExtMapper;
import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.dao.model.NmplDataPushRecord;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
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
            String sourceId = nmplCompanyHeartbeat.getSourceNetworkId();
            String targetId = nmplCompanyHeartbeat.getTargetNetworkId();
            //获取小区唯一标识
            companyHeartbeatVo.setSourceCompanyNetworkId(NetworkIdUtil.splitNetworkId(sourceId));
            companyHeartbeatVo.setTargetCompanyNetworkId(NetworkIdUtil.splitNetworkId(targetId));
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

    /**
     * 入网id1 16转10 进制
     * @param networkId
     * @return
     */
//    private String changeNetworkId(String networkId){
//        String[] split = networkId.split("-");
//        String networkStr = "";
//        for(int i = 0; i <= 4;i++){
//            Integer change = Integer.parseInt(split[i],16);
//            networkStr = networkStr + change + "-";
//        }
//        return networkStr.substring(0,networkStr.length() - 1);
//    }

}
