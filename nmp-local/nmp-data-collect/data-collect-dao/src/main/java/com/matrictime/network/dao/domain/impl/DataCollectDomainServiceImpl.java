package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataPushRecordMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DataCollectEnum;
import com.matrictime.network.enums.TerminalUserEnum;
import com.matrictime.network.modelVo.DataCollectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.constant.DataConstants.RESERVE_DIGITS;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Service
public class DataCollectDomainServiceImpl implements DataCollectDomainService {

    @Resource
    private NmplDataCollectExtMapper dataCollectExtMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;

    @Resource
    private NmplDataPushRecordMapper dataPushRecordMapper;


    @Override
    public List<DataCollectVo> selectDataCollect() {
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.selectDataCollect();
        if(CollectionUtils.isEmpty(nmplDataCollects)){
            return null;
        }
        NmplDataPushRecordExample pushRecordExample = new NmplDataPushRecordExample();
        pushRecordExample.createCriteria().andTableNameEqualTo(NMPL_DATA_COLLECT);
        pushRecordExample.setOrderByClause("id desc");
        List<NmplDataPushRecord> dataPushRecords = dataPushRecordMapper.selectByExample(pushRecordExample);
        if(!CollectionUtils.isEmpty(dataPushRecords)){
            Long id = nmplDataCollects.get(nmplDataCollects.size() - 1).getId();
            Long dataId = dataPushRecords.get(0).getDataId();
            if(id <= dataId){
                return null;
            }
        }

        //唯一标识分类
        Set<String> stringSet = new HashSet();
        for(NmplDataCollect nmplDataCollect: nmplDataCollects){
            String stationNetworkId = getStationNetworkId(nmplDataCollect);
            String s = NetworkIdUtil.splitNetworkId(stationNetworkId);
            stringSet.add(s);
        }
        //基站数组
        List<NmplDataCollect> stationList = generateList(nmplDataCollects,DeviceTypeEnum.BASE_STATION.getCode());
        //边界基站数组
        List<NmplDataCollect> borderList = generateList(nmplDataCollects,DeviceTypeEnum.BORDER_BASE_STATION.getCode());
        //密钥中心数组
        List<NmplDataCollect> dList = generateList(nmplDataCollects,DeviceTypeEnum.DISPENSER.getCode());
        //生成返回体
        List<DataCollectVo> list = new ArrayList<>();
        for(String networkIdString: stringSet){
            //基站通信上行流量
            if(!CollectionUtils.isEmpty(stationList)){
                DataCollectVo stationCollectVo = setDataCollectVo(stationList, DataCollectEnum.COMM_LOAD_UP_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }
            //边界基站通信上行流量
            if(!CollectionUtils.isEmpty(borderList)){
                DataCollectVo stationCollectVo = setDataCollectVo(borderList, DataCollectEnum.COMM_LOAD_UP_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }
            //密钥中心通信上行流量
            if(!CollectionUtils.isEmpty(dList)){
                DataCollectVo stationCollectVo = setDataCollectVo(dList, DataCollectEnum.COMM_LOAD_UP_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }

            //基站通信下行流量
            if(!CollectionUtils.isEmpty(stationList)){
                DataCollectVo stationCollectVo = setDataCollectVo(stationList, DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }
            //边界基站通信下行流量
            if(!CollectionUtils.isEmpty(borderList)){
                DataCollectVo stationCollectVo = setDataCollectVo(borderList, DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }
            //密钥中心通信下行流量
            if(!CollectionUtils.isEmpty(dList)){
                DataCollectVo stationCollectVo = setDataCollectVo(dList, DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode(), networkIdString);
                list.add(stationCollectVo);
            }

        }
        return list;
    }

    /**
     * 生成数组
     * @return
     */
    private List<NmplDataCollect> generateList(List<NmplDataCollect> nmplDataCollects,String code){
        List<NmplDataCollect> list = new ArrayList<>();
        for(NmplDataCollect nmplDataCollect: nmplDataCollects){
            if(code.equals(nmplDataCollect.getDeviceType())){
                list.add(nmplDataCollect);
            }
        }
        return list;
    }

    /**
     * 生成data返回体
     * @param list 基站数组
     * @param codeEnum 流量编码
     * @param networkIdString 唯一标识
     * @return
     */
    private DataCollectVo setDataCollectVo(List<NmplDataCollect> list,String codeEnum,String networkIdString){
        double stationSum = 0d;
        BigDecimal stationSumBig = new BigDecimal(Double.toString(stationSum));
        DataCollectVo dataCollectVo = new DataCollectVo();
        for(NmplDataCollect nmplDataCollect: list){
            //获取唯一标识
            String networkId = getStationNetworkId(nmplDataCollect);
            String idString = NetworkIdUtil.splitNetworkId(networkId);
            if(codeEnum.equals(nmplDataCollect.getDataItemCode()) &&
                    networkIdString.equals(idString)){
                BigDecimal itemValue = new BigDecimal(nmplDataCollect.getDataItemValue());
                stationSumBig = stationSumBig.add(itemValue);
                dataCollectVo.setUploadTime(nmplDataCollect.getUploadTime());
                dataCollectVo.setDeviceType(nmplDataCollect.getDeviceType());
                dataCollectVo.setDataItemCode(codeEnum);
                //数据转换
                dataCollectVo.setSumNumber(changeSum(String.valueOf(stationSumBig)));
                dataCollectVo.setId(nmplDataCollect.getId());
                dataCollectVo.setCompanyNetworkId(networkIdString);
            }
        }
        return dataCollectVo;
    }


    /**
     * 数据转换
     * @param s
     * @return
     */
    private String  changeSum(String s){
        BigDecimal sum = new BigDecimal(s);
        return String.valueOf(sum.divide(
                new BigDecimal(BASE_NUMBER * BASE_NUMBER * HALF_HOUR_SECONDS / BYTE_TO_BPS),
                RESERVE_DIGITS, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 获取小区唯一标识
     * @param nmplDataCollect
     * @return
     */
    public String getStationNetworkId(NmplDataCollect nmplDataCollect){
        String stationNetworkId = "";
        if(Integer.parseInt(nmplDataCollect.getDeviceType())
                < Integer.parseInt(DeviceTypeEnum.DISPENSER.getCode())){
            NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
            NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
            criteria.andStationIdEqualTo(nmplDataCollect.getDeviceId());
            List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
            if(!CollectionUtils.isEmpty(baseStationInfos)){
                stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
            }
        }else {
            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
            NmplDeviceInfoExample.Criteria criteria = deviceInfoExample.createCriteria();
            criteria.andDeviceIdEqualTo((nmplDataCollect.getDeviceId()));
            List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
            if(!CollectionUtils.isEmpty(nmplDeviceInfoList)){
                stationNetworkId = nmplDeviceInfoList.get(0).getStationNetworkId();
            }
        }

        return stationNetworkId;
    }

}
