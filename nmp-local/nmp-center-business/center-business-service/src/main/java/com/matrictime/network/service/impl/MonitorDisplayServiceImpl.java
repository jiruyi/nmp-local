package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.util.NumberUtils;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.StationSummaryExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.modelVo.LoanVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.QueryCompanyUserResp;
import com.matrictime.network.response.QueryDeviceResp;
import com.matrictime.network.response.QueryMapInfoResp;
import com.matrictime.network.response.queryUserResp;
import com.matrictime.network.service.MonitorDisplayService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.ZERO;
import static com.matrictime.network.base.constant.DataConstants.ZERO_STRING;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Slf4j
@Service
public class MonitorDisplayServiceImpl extends SystemBaseService implements MonitorDisplayService {

    @Resource
    private NmplStationSummaryMapper stationSummaryMapper;

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;

    @Resource
    private NmplTerminalUserMapper terminalUserMapper;

    @Resource
    private StationSummaryExtMapper summaryExtMapper;

    @Resource
    private NmplDataCollectMapper dataCollectMapper;
    
    @Resource
    private NmplAlarmInfoMapper alarmInfoMapper;

    @Resource
    private NmplCompanyHeartbeatMapper companyHeartbeatMapper;


    /**
     * 查询小区用户数
     * @return
     */
    @Override
    public Result<QueryCompanyUserResp> queryCompanyUser() {
        Result result;

        try {
            QueryCompanyUserResp resp = new QueryCompanyUserResp();
            NmplCompanyInfoExample example = new NmplCompanyInfoExample();
            example.createCriteria().andCompanyTypeEqualTo(CompanyEnum.SMALL_AREA.getCode()).andIsExistEqualTo(IS_EXIST);
            List<NmplCompanyInfo> nmplCompanyInfos = companyInfoMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(nmplCompanyInfos)){
                // 小区列表
                List<CompanyInfoVo> companyInfos = new ArrayList<>();
                // 在线用户数列表
                List<String> onlineUser = new ArrayList<>();
                // 接入用户数列表
                List<String> accessUser = new ArrayList<>();
                for (NmplCompanyInfo companyInfo:nmplCompanyInfos){
                    // 获取小区列表
                    CompanyInfoVo vo = new CompanyInfoVo();
                    BeanUtils.copyProperties(companyInfo,vo);
                    companyInfos.add(vo);

                    // 获取在线用户列表
                    NmplTerminalUserExample onlineExample = new NmplTerminalUserExample();
                    onlineExample.createCriteria().andCompanyNetworkIdEqualTo(companyInfo.getCompanyNetworkId()).andTerminalStatusEqualTo(TerminalUserEnum.ON_LINE.getCode());
                    List<NmplTerminalUser> onlineUsers = terminalUserMapper.selectByExample(onlineExample);
                    int online = ZERO;
                    if (!CollectionUtils.isEmpty(onlineUsers)){
                        for (int i=0;i<onlineUsers.size();i++){
                            online = online + Integer.valueOf(onlineUsers.get(i).getSumNumber());
                        }
                    }
                    onlineUser.add(String.valueOf(online));

                    // 获取接入用户列表
                    NmplTerminalUserExample accessExample = new NmplTerminalUserExample();
                    List<String> terminalStatus = new ArrayList<>();
                    terminalStatus.add(TerminalUserEnum.REGISTER.getCode());
                    terminalStatus.add(TerminalUserEnum.ON_LINE.getCode());
                    terminalStatus.add(TerminalUserEnum.OFF_LINE.getCode());
                    accessExample.createCriteria().andCompanyNetworkIdEqualTo(companyInfo.getCompanyNetworkId()).andTerminalStatusIn(terminalStatus);
                    List<NmplTerminalUser> accessUsers = terminalUserMapper.selectByExample(accessExample);
                    int access = ZERO;
                    if (!CollectionUtils.isEmpty(accessUsers)){
                        for (int i=0; i<accessUsers.size();i++){
                            access = access + Integer.valueOf(accessUsers.get(i).getSumNumber());
                        }

                    }
                    accessUser.add(String.valueOf(access));
                }
                resp.setCompanyInfo(companyInfos);
                resp.setAccessUser(accessUser);
                resp.setOnlineUser(onlineUser);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryCompanyUser Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 查询用户数
     * @param req
     * @return
     */
    @Override
    public Result<queryUserResp> queryUser(QueryUserReq req) {
        Result result;

        try {
            queryUserResp resp = new queryUserResp();
            //总用户数
            int totalUser = ZERO;

            //在线用户数
            int onlineUser = ZERO;

            //接入用户数
            int accessUser = ZERO;

            //僵尸用户数(非上线用户)
            int zombieUser = ZERO;

            List<NmplTerminalUser> terminalUsers = new ArrayList<>();
            if (ParamCheckUtil.checkVoStrBlank(req.getCompanyNetworkId())){
                // 没有小区入网码标识查询总数据
                List<String>companyNetworkIds = new ArrayList<>();
                NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
                companyInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
                List<NmplCompanyInfo> companyInfos = companyInfoMapper.selectByExample(companyInfoExample);

                if (!CollectionUtils.isEmpty(companyInfos)){
                    for (int i=0;i<companyInfos.size();i++){
                        companyNetworkIds.add(companyInfos.get(i).getCompanyNetworkId());
                    }
                }
                if (!CollectionUtils.isEmpty(companyNetworkIds)){
                    NmplTerminalUserExample example = new NmplTerminalUserExample();
                    example.createCriteria().andCompanyNetworkIdIn(companyNetworkIds);
                    terminalUsers = terminalUserMapper.selectByExample(example);
                }
            }else {
                // 有小区入网码标识查询小区数据
                NmplTerminalUserExample example = new NmplTerminalUserExample();
                example.createCriteria().andCompanyNetworkIdEqualTo(req.getCompanyNetworkId());
                terminalUsers = terminalUserMapper.selectByExample(example);
            }

            if (!CollectionUtils.isEmpty(terminalUsers)){
                for (NmplTerminalUser terminalUser:terminalUsers){
                    int tempNumber = ZERO;
                    String sumNumber = terminalUser.getSumNumber();
                    if (!ParamCheckUtil.checkVoStrBlank(sumNumber)){
                        tempNumber = Integer.valueOf(sumNumber);
                    }
                    String terminalStatus = terminalUser.getTerminalStatus();
                    if (TerminalUserEnum.KEY_MATCH.getCode().equals(terminalStatus)){
//                            zombieUser = zombieUser + tempNumber;
                    }else if (TerminalUserEnum.REGISTER.getCode().equals(terminalStatus)){
                        accessUser = accessUser + tempNumber;
//                            zombieUser = zombieUser + tempNumber;
                    }else if (TerminalUserEnum.ON_LINE.getCode().equals(terminalStatus)){
                        onlineUser = onlineUser + tempNumber;
                        accessUser = accessUser + tempNumber;
                    }else if (TerminalUserEnum.OFF_LINE.getCode().equals(terminalStatus)){
                        accessUser = accessUser + tempNumber;
//                            zombieUser = zombieUser + tempNumber;
                    }else if (TerminalUserEnum.LOG_OUT.getCode().equals(terminalStatus)){
//                            zombieUser = zombieUser + tempNumber;
                    }
                    totalUser = totalUser + tempNumber;
                    zombieUser = totalUser - onlineUser;
                }
            }

            resp.setTotalUser(String.valueOf(totalUser));
            resp.setOnlineUser(String.valueOf(onlineUser));
            resp.setAccessUser(String.valueOf(accessUser));
            resp.setZombieUser(String.valueOf(zombieUser));
            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryUser Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 查询设备数
     * @param req
     * @return
     */
    @Override
    public Result<QueryDeviceResp> queryDevice(QueryDeviceReq req){
        Result result;

        try {
            QueryDeviceResp resp = new QueryDeviceResp();

            //总网络数
            long totalNet = ZERO;

            //总接入基站数
            long totalInsideStation = ZERO;

            //总密钥中心数
            long totalKeyCenter = ZERO;

            //总边界基站数
            long totalBoundaryStation = ZERO;

            //总一体机数
            long totalOutlinePc = ZERO;

            //总安全服务器数
            long totalSafeServer = ZERO;

            List<String> companyNetworkIds = new ArrayList<>();

            if (ParamCheckUtil.checkVoStrBlank(req.getCompanyNetworkId())){// 如果小区id列表为空，默认查所有已存在小区总量
                NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
                companyInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
                List<NmplCompanyInfo> companyInfos = companyInfoMapper.selectByExample(companyInfoExample);
                if (!CollectionUtils.isEmpty(companyInfos)){
                    for (int i=0;i<companyInfos.size();i++){
                        companyNetworkIds.add(companyInfos.get(i).getCompanyNetworkId());
                    }
                }
            }else {
                companyNetworkIds.add(req.getCompanyNetworkId());
            }

            if (!CollectionUtils.isEmpty(companyNetworkIds)){// 如果小区id列表为空则此时没有有效小区信息
                //总网络数
                totalNet = NumberUtils.getLong(summaryExtMapper.getSumByIn(StationSummaryEnum.TOTAL_NET_WORKS.getCode(), companyNetworkIds));

                //总接入基站数
                totalInsideStation = NumberUtils.getLong(summaryExtMapper.getSumByIn(StationSummaryEnum.BASE_STATION.getCode(), companyNetworkIds));

                //总密钥中心数
                totalKeyCenter = NumberUtils.getLong(summaryExtMapper.getSumByIn(StationSummaryEnum.KET_CENTER.getCode(), companyNetworkIds));

                //总边界基站数
                totalBoundaryStation = NumberUtils.getLong(summaryExtMapper.getSumByIn(StationSummaryEnum.BORDER_BASE_STATION.getCode(), companyNetworkIds));

                NmplTerminalUserExample pcUserExample = new NmplTerminalUserExample();
                pcUserExample.createCriteria().andUserTypeEqualTo(TerminalUserEnum.ONE_MACHINE.getCode()).andCompanyNetworkIdIn(companyNetworkIds);
                List<NmplTerminalUser> pcTerminalUsers = terminalUserMapper.selectByExample(pcUserExample);
                if (!CollectionUtils.isEmpty(pcTerminalUsers)){
                    for (NmplTerminalUser terminalUser:pcTerminalUsers){
                        totalOutlinePc = totalOutlinePc + Long.valueOf(terminalUser.getSumNumber());
                    }
                }

                NmplTerminalUserExample ssUserExample = new NmplTerminalUserExample();
                ssUserExample.createCriteria().andUserTypeEqualTo(TerminalUserEnum.SECURITY_SERVER.getCode()).andCompanyNetworkIdIn(companyNetworkIds);
                List<NmplTerminalUser> ssTerminalUsers = terminalUserMapper.selectByExample(ssUserExample);
                if (!CollectionUtils.isEmpty(ssTerminalUsers)){
                    for (NmplTerminalUser terminalUser:ssTerminalUsers){
                        totalSafeServer = totalSafeServer + Long.valueOf(terminalUser.getSumNumber());
                    }
                }
            }

            resp.setTotalNet(String.valueOf(totalNet));
            resp.setTotalInsideStation(String.valueOf(totalInsideStation));
            resp.setTotalBoundaryStation(String.valueOf(totalBoundaryStation));
            resp.setTotalKeyCenter(String.valueOf(totalKeyCenter));
            resp.setTotalOutlinePc(String.valueOf(totalOutlinePc));
            resp.setTotalSafeServer(String.valueOf(totalSafeServer));
            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryDevice Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 查询地图信息
     * @return
     */
    @Override
    public Result<QueryMapInfoResp> queryMapInfo() {
        Result result;

        try {
            QueryMapInfoResp resp = new QueryMapInfoResp();
            NmplCompanyInfoExample example = new NmplCompanyInfoExample();
            example.createCriteria().andCompanyTypeEqualTo(CompanyEnum.SMALL_AREA.getCode()).andIsExistEqualTo(IS_EXIST);
            List<NmplCompanyInfo> nmplCompanyInfos = companyInfoMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(nmplCompanyInfos)){
                // 小区列表
                List<CompanyInfoVo> companyInfos = new ArrayList<>();
                List<String> companyNetIds = new ArrayList<>();

                // 小区心跳关联信息
                List<CompanyHeartbeatVo> companyHeartbeatVos = new ArrayList<>();
                for (NmplCompanyInfo companyInfo:nmplCompanyInfos){
                    String companyNetworkId = companyInfo.getCompanyNetworkId();
                    companyNetIds.add(companyNetworkId);
                    // 获取小区列表
                    CompanyInfoVo vo = new CompanyInfoVo();
                    BeanUtils.copyProperties(companyInfo,vo);

                    // 获取告警信息
                    NmplAlarmInfoExample seriousExample = new NmplAlarmInfoExample();
                    seriousExample.createCriteria().andAlarmLevelEqualTo(AlarmSysLevelEnum.LevelEnum.SERIOUS.getLevel()).andAlarmAreaCodeEqualTo(companyNetworkId);
                    long seriousCount = alarmInfoMapper.countByExample(seriousExample);
                    vo.setSeriousCount(String.valueOf(seriousCount));

                    NmplAlarmInfoExample emergentExample = new NmplAlarmInfoExample();
                    emergentExample.createCriteria().andAlarmLevelEqualTo(AlarmSysLevelEnum.LevelEnum.EMERG.getLevel()).andAlarmAreaCodeEqualTo(companyNetworkId);
                    long emergentCount = alarmInfoMapper.countByExample(emergentExample);
                    vo.setEmergentCount(String.valueOf(emergentCount));

                    NmplAlarmInfoExample sameAsExample = new NmplAlarmInfoExample();
                    sameAsExample.createCriteria().andAlarmLevelEqualTo(AlarmSysLevelEnum.LevelEnum.SAMEAS.getLevel()).andAlarmAreaCodeEqualTo(companyNetworkId);
                    long sameAsCount = alarmInfoMapper.countByExample(sameAsExample);
                    vo.setSameAsCount(String.valueOf(sameAsCount));

                    // 获取带宽信息
//                    NmplDataCollectExample accessExample = new NmplDataCollectExample();
//                    accessExample.createCriteria().andDataItemCodeEqualTo(DataCollectEnum.ACCESS_BANDWITH.getCode()).andCompanyNetworkIdEqualTo(companyNetworkId);
//                    List<NmplDataCollect> accessCollects = dataCollectMapper.selectByExample(accessExample);
//                    if (!CollectionUtils.isEmpty(accessCollects)){
//                        String sumNumber = accessCollects.get(0).getSumNumber();
//                        vo.setAccessBandwith(sumNumber);
//                        vo.setAccessBandwithUnit(accessCollects.get(0).getUnit());
//                    }
//
//                    NmplDataCollectExample intervalExample = new NmplDataCollectExample();
//                    intervalExample.createCriteria().andDataItemCodeEqualTo(DataCollectEnum.INTERVAL_BANDWITH.getCode()).andCompanyNetworkIdEqualTo(companyNetworkId);
//                    List<NmplDataCollect> intervalCollects = dataCollectMapper.selectByExample(intervalExample);
//                    if (!CollectionUtils.isEmpty(intervalCollects)){
//                        String sumNumber = intervalCollects.get(0).getSumNumber();
//                        vo.setIntervalBandwith(sumNumber);
//                        vo.setIntervalBandwithUnit(intervalCollects.get(0).getUnit());
//                    }

                    // 获取设备信息
                    long base = NumberUtils.getLong(summaryExtMapper.getSum(StationSummaryEnum.BASE_STATION.getCode(), companyNetworkId));
                    long border = NumberUtils.getLong(summaryExtMapper.getSum(StationSummaryEnum.BORDER_BASE_STATION.getCode(), companyNetworkId));
                    long keycenter = NumberUtils.getLong(summaryExtMapper.getSum(StationSummaryEnum.KET_CENTER.getCode(), companyNetworkId));
                    vo.setNetDevices(String.valueOf(base+border+keycenter));

                    NmplTerminalUserExample pcUserExample = new NmplTerminalUserExample();
                    pcUserExample.createCriteria().andUserTypeEqualTo(TerminalUserEnum.ONE_MACHINE.getCode()).andCompanyNetworkIdEqualTo(companyNetworkId);
                    List<NmplTerminalUser> pcTerminalUsers = terminalUserMapper.selectByExample(pcUserExample);
                    if (!CollectionUtils.isEmpty(pcTerminalUsers)){
                        vo.setTerminalDevices(pcTerminalUsers.get(0).getSumNumber());
                    }else {
                        vo.setTerminalDevices(String.valueOf(ZERO));
                    }

                    // 获取带宽信息(暂时按设备数*10M计算)
                    vo.setAccessBandwith(String.valueOf(base * 10));
                    vo.setAccessBandwithUnit("M");
                    vo.setIntervalBandwith(String.valueOf(border * 10));
                    vo.setIntervalBandwithUnit("M");

                    companyInfos.add(vo);
                }

                // 小区关联信息
                List<NmplCompanyHeartbeat> companyHeartbeats = summaryExtMapper.queryHeart();
                if (!CollectionUtils.isEmpty(companyHeartbeats)){
                    for (NmplCompanyHeartbeat heartbeat:companyHeartbeats){
                        if (companyNetIds.contains(heartbeat.getSourceCompanyNetworkId()) && companyNetIds.contains(heartbeat.getTargetCompanyNetworkId())){
                            CompanyHeartbeatVo vo = new CompanyHeartbeatVo();
                            BeanUtils.copyProperties(heartbeat,vo);
                            companyHeartbeatVos.add(vo);
                        }
                    }
                }
                resp.setCompanyInfoVos(companyInfos);
                resp.setCompanyHeartbeatVos(companyHeartbeatVos);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryMapInfo Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

}
