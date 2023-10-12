package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DataCollectEnum;
import com.matrictime.network.modelVo.DataCollectVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    @Override
    public List<DataCollectVo> selectDataCollect() {
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.selectDataCollect();
        if(CollectionUtils.isEmpty(nmplDataCollects)){
            return null;
        }
        //基站数组
        List<NmplDataCollect> stationList = new ArrayList<>();
        for(NmplDataCollect nmplDataCollect: nmplDataCollects){
            if(DeviceTypeEnum.BASE_STATION.getCode().equals(nmplDataCollect.getDeviceType())){
                stationList.add(nmplDataCollect);
            }

        }
        //边界基站数组
        List<NmplDataCollect> borderList = new ArrayList<>();
        for(NmplDataCollect nmplDataCollect: nmplDataCollects){
            if(DeviceTypeEnum.BORDER_BASE_STATION.getCode().equals(nmplDataCollect.getDeviceType())){
                borderList.add(nmplDataCollect);
            }

        }
        //密钥中心数组
        List<NmplDataCollect> dList = new ArrayList<>();
        for(NmplDataCollect nmplDataCollect: nmplDataCollects){
            if(DeviceTypeEnum.DISPENSER.getCode().equals(nmplDataCollect.getDeviceType())){
                dList.add(nmplDataCollect);
            }

        }
        //查询小区Id唯一标识符
        String stationNetworkId = getStationNetworkId(nmplDataCollects.get(0));
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        List<DataCollectVo> list = new ArrayList<>();
        Map<String, DataCollectEnum> map = DataCollectEnum.getMap();
        Set<String> strings = map.keySet();
        //按照流量类型求流量总和
        for(String code: strings){
            //基站求和
            double stationSum = 0d;
            BigDecimal stationSumBig = new BigDecimal(Double.toString(stationSum));
            DataCollectVo stationCollectVo = new DataCollectVo();
            for(NmplDataCollect nmplDataCollect: stationList){
                if(code.equals(nmplDataCollect.getDataItemCode())){
                    BigDecimal itemValue = new BigDecimal(nmplDataCollect.getDataItemValue());
                    stationSumBig = stationSumBig.add(itemValue);
                    stationCollectVo.setUploadTime(nmplDataCollect.getUploadTime());

                }

            }
            stationCollectVo.setDeviceType(DeviceTypeEnum.BASE_STATION.getCode());
            stationCollectVo.setDataItemCode(code);
            //数据转换
            stationCollectVo.setSumNumber(changeSum(String.valueOf(stationSumBig)));
            stationCollectVo.setCompanyNetworkId(networkIdString);
            list.add(stationCollectVo);

            //边界基站求和
            double borderSum = 0d;
            BigDecimal borderSumBig = new BigDecimal(Double.toString(borderSum));
            DataCollectVo borderCollectVo = new DataCollectVo();
            for(NmplDataCollect nmplDataCollect: borderList){
                if(code.equals(nmplDataCollect.getDataItemCode())){
                    BigDecimal itemValue = new BigDecimal(nmplDataCollect.getDataItemValue());
                    borderSumBig = borderSumBig.add(itemValue);
                    borderCollectVo.setUploadTime(nmplDataCollect.getUploadTime());
                }

            }
            borderCollectVo.setDeviceType(DeviceTypeEnum.BORDER_BASE_STATION.getCode());
            borderCollectVo.setDataItemCode(code);
            //数据转换
            borderCollectVo.setSumNumber(changeSum(String.valueOf(borderSumBig)));
            borderCollectVo.setCompanyNetworkId(networkIdString);
            list.add(borderCollectVo);

            //密钥中心求和
            double sum = 0d;
            BigDecimal sumBig = new BigDecimal(Double.toString(sum));
            DataCollectVo dataCollectVo = new DataCollectVo();
            for(NmplDataCollect nmplDataCollect: dList){
                if(code.equals(nmplDataCollect.getDataItemCode())){
                    BigDecimal itemValue = new BigDecimal(nmplDataCollect.getDataItemValue());
                    borderSumBig = borderSumBig.add(itemValue);
                    dataCollectVo.setUploadTime(nmplDataCollect.getUploadTime());

                }

            }
            dataCollectVo.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
            dataCollectVo.setDataItemCode(code);
            //数据转换
            dataCollectVo.setSumNumber(changeSum(String.valueOf(sumBig)));
            dataCollectVo.setCompanyNetworkId(networkIdString);
            list.add(dataCollectVo);

        }
        return list;
    }


    private DataCollectVo setDataCollectVo(){
        return null;
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
