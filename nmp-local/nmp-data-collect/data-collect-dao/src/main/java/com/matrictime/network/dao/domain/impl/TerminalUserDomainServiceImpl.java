package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplTerminalUserExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.TerminalUserEnum;
import com.matrictime.network.modelVo.TerminalUserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class TerminalUserDomainServiceImpl implements TerminalUserDomainService {

    @Resource
    private NmplTerminalUserExtMapper terminalUserExtMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Override
    public List<TerminalUserVo> selectTerminalUser() {

        List<NmplTerminalUser> list = terminalUserExtMapper.selectTerminalUser();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        //唯一标识分类
        Set<String> stringSet = new HashSet();
        List<NmplTerminalUser> machineList = new ArrayList<>();
        List<NmplTerminalUser> serverList = new ArrayList<>();
        for(NmplTerminalUser nmplTerminalUser: list){
            String stationNetworkId = getStationNetworkId(nmplTerminalUser);
            String s = NetworkIdUtil.splitNetworkId(stationNetworkId);
            stringSet.add(s);
            if(TerminalUserEnum.ONE_MACHINE.getCode().equals(nmplTerminalUser.getUserType())){
                machineList.add(nmplTerminalUser);
            }
            if(TerminalUserEnum.SECURITY_SERVER.getCode().equals(nmplTerminalUser.getUserType())){
                serverList.add(nmplTerminalUser);
            }
        }

        List<TerminalUserVo> terminalUserVoList = new ArrayList<>();
        for(String idString: stringSet){
            for(NmplTerminalUser nmplTerminalUser: machineList){
                //获取唯一标识
                String networkId = getStationNetworkId(nmplTerminalUser);
                String networkIdString = NetworkIdUtil.splitNetworkId(networkId);
                //过滤唯一标识用户
                if(idString.equals(networkIdString)){
                    Map<String, TerminalUserEnum> terminalUserEnumMap = TerminalUserEnum.getTerminalUserEnumMap();
                    Set<String> strings = terminalUserEnumMap.keySet();
                    //获取一体机数量
                    for(String userStatus: strings){
                        //添加一体机数量
                        TerminalUserVo userMachine = getUserVo(TerminalUserEnum.ONE_MACHINE.getCode(), userStatus, machineList, idString);
                        if(userMachine.getUserType() != null){
                            terminalUserVoList.add(userMachine);
                        }
                    }
                }
            }

            for(NmplTerminalUser nmplTerminalUser: serverList){
                //获取唯一标识
                String networkId = getStationNetworkId(nmplTerminalUser);
                String networkIdString = NetworkIdUtil.splitNetworkId(networkId);
                //过滤唯一标识用户
                if(idString.equals(networkIdString)){
                    Map<String, TerminalUserEnum> terminalUserEnumMap = TerminalUserEnum.getTerminalUserEnumMap();
                    Set<String> strings = terminalUserEnumMap.keySet();
                    //获取一体机数量
                    for(String userStatus: strings){
                        //添加安全服务器数量
                        TerminalUserVo userVo = getUserVo(TerminalUserEnum.SECURITY_SERVER.getCode(), userStatus, serverList, idString);
                        if(userVo.getUserType() != null){
                            terminalUserVoList.add(userVo);
                        }
                    }
                }
            }
        }
        return terminalUserVoList;
    }

    /**
     * 构建返回体
     * @param userType 用户类型
     * @param userStatus 用户状态
     * @param list 总用户
     * @param networkIdString 唯一标识
     * @return
     */
    private TerminalUserVo getUserVo(String userType,String userStatus,List<NmplTerminalUser> list,String networkIdString){
        int sum = 0;
        TerminalUserVo terminalUserVo = new TerminalUserVo();
        for(NmplTerminalUser nmplTerminalUser: list){
            //获取唯一标识
            String networkId = getStationNetworkId(nmplTerminalUser);
            String idString = NetworkIdUtil.splitNetworkId(networkId);
            //进行状态 类型 唯一标识过滤
            if(userStatus.equals(nmplTerminalUser.getTerminalStatus()) &&
                    userType.equals(nmplTerminalUser.getUserType()) && networkIdString.equals(idString)){
                sum++;
                terminalUserVo.setUploadTime(nmplTerminalUser.getUploadTime());
                terminalUserVo.setUserType(userType);
                terminalUserVo.setTerminalStatus(userStatus);
                terminalUserVo.setSumNumber(String.valueOf(sum));
                terminalUserVo.setCompanyNetworkId(networkIdString);
            }
        }
        return terminalUserVo;
    }

    /**
     * 获取小区唯一标识
     * @param nmplTerminalUser
     * @return
     */
    public String getStationNetworkId(NmplTerminalUser nmplTerminalUser){
        String stationNetworkId = "";
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andStationIdEqualTo(nmplTerminalUser.getParentDeviceId());
        criteria.andIsExistEqualTo(true);
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(!CollectionUtils.isEmpty(baseStationInfos)){
            stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
        }else {
            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
            NmplDeviceInfoExample.Criteria criteria1 = deviceInfoExample.createCriteria();
            criteria1.andDeviceIdEqualTo((nmplTerminalUser.getParentDeviceId()));
            criteria1.andIsExistEqualTo(true);
            List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
            if(!CollectionUtils.isEmpty(nmplDeviceInfoList)){
                stationNetworkId = nmplDeviceInfoList.get(0).getStationNetworkId();
            }
        }
        return stationNetworkId;
    }
}
