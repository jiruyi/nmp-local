package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.extend.DataCollectExtMapper;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplCompanyInfoExample;
import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplDataCollectExample;
import com.matrictime.network.enums.DataCollectEnum;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.modelVo.PercentageFlowVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/10.
 */
@Service
public class DataCollectDomainServiceImpl implements DataCollectDomainService {

    @Resource
    private NmplDataCollectMapper dataCollectMapper;

    @Resource
    private DataCollectExtMapper dataCollectExtMapper;

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;

    /**
     * 查询负载流浪图
     * @param dataCollectRequest
     * @return
     */
    @Override
    public List<DataTimeVo> selectLoadData(DataCollectRequest dataCollectRequest) {
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.selectLoadData(dataCollectRequest);
        List<DataTimeVo> timeVoList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        List<String> list = getTimeList();
        for(int i = 0;i < list.size();i++){
            double sum = 0d;
            DataTimeVo dataTimeVo = new DataTimeVo();
            for(NmplDataCollect nmplDataCollect: nmplDataCollects){
                String formatTime = formatter.format(nmplDataCollect.getUploadTime());
                if(list.get(i).equals(formatTime)){
                    if(StringUtils.isEmpty(nmplDataCollect.getSumNumber())){
                        nmplDataCollect.setSumNumber("0");
                    }
                    sum = sum + Double.parseDouble(nmplDataCollect.getSumNumber());
                }
            }
            dataTimeVo.setTime(list.get(i));
            dataTimeVo.setDataSum(sum);
            timeVoList.add(dataTimeVo);
        }
        return timeVoList;
    }

    /**
     * 获取一天半小时的时间段
     * @return
     */
    private List<String> getTimeList(){
        List<String> timeList = new ArrayList<>();
        timeList.add("00:00");timeList.add("00:30");
        timeList.add("01:00");timeList.add("01:30");
        timeList.add("02:00");timeList.add("02:30");
        timeList.add("03:00");timeList.add("03:30");
        timeList.add("04:00");timeList.add("04:30");
        timeList.add("05:00");timeList.add("05:30");
        timeList.add("06:00");timeList.add("06:30");
        timeList.add("07:00");timeList.add("07:30");
        timeList.add("08:00");timeList.add("08:30");
        timeList.add("09:00");timeList.add("09:30");
        timeList.add("10:00");timeList.add("10:30");
        timeList.add("11:00");timeList.add("11:30");
        timeList.add("12:00");timeList.add("12:30");
        timeList.add("13:00");timeList.add("13:30");
        timeList.add("14:00");timeList.add("14:30");
        timeList.add("15:00");timeList.add("15:30");
        timeList.add("16:00");timeList.add("16:30");
        timeList.add("17:00");timeList.add("17:30");
        timeList.add("18:00");timeList.add("18:30");
        timeList.add("19:00");timeList.add("19:30");
        timeList.add("20:00");timeList.add("20:30");
        timeList.add("21:00");timeList.add("21:30");
        timeList.add("22:00");timeList.add("22:30");
        timeList.add("23:00");timeList.add("23:30");
        return timeList;
    }


    /**
     * 查询负载总量
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Double sumDataValue(DataCollectRequest dataCollectRequest) {
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.sumData(dataCollectRequest);
        double sum = 0d;
        if(nmplDataCollects.size() >= 1){
            for(NmplDataCollect dataCollect: nmplDataCollects){
                sum = sum + Double.parseDouble(dataCollect.getSumNumber());
            }
            return sum;
        }
        return 0d;
    }

    /**
     * 负载数据插入
     * @param list
     * @return
     */
    @Transactional
    @Override
    public int insertDataCollect(List<DataCollectVo> list) {
        return dataCollectExtMapper.insertData(list);
    }

    /**
     * 查询各个小区各个流量值
     * @param
     * @return
     */
    @Override
    public List<PercentageFlowVo> selectCompanyData() {
        List<PercentageFlowVo> list = new ArrayList<>();
        NmplCompanyInfoExample companyInfoExample = new NmplCompanyInfoExample();
        NmplCompanyInfoExample.Criteria criteria = companyInfoExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        List<NmplCompanyInfo> companyInfos = companyInfoMapper.selectByExample(companyInfoExample);
        if(CollectionUtils.isEmpty(companyInfos)){
            return null;
        }
        for(NmplCompanyInfo nmplCompanyInfo: companyInfos){
            //接入负责
            list.add(setValue(DeviceTypeEnum.STATION_INSIDE.getCode(), nmplCompanyInfo));
            //区间负载
            list.add(setValue(DeviceTypeEnum.STATION_BOUNDARY.getCode(), nmplCompanyInfo));
            //密钥负载
            list.add(setValue(DeviceTypeEnum.DEVICE_DISPENSER.getCode(), nmplCompanyInfo));
        }
        return list;
    }


    /**
     * 求流量总和
     * @param dataCollectRequest
     * @return
     */
    private double getSum(DataCollectRequest dataCollectRequest){
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.sumData(dataCollectRequest);
        double sum = 0d;
        if(nmplDataCollects.size() > 1){
            for(NmplDataCollect dataCollect: nmplDataCollects){
                sum = sum + Double.parseDouble(dataCollect.getSumNumber());
            }
            return sum;
        }

        return 0d;
    }

    /**
     * 保留小数点后两位
     * @param value
     * @return
     */
    public String avgData(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    /**
     * 构建返回值
     * @param code
     * @param nmplCompanyInfo
     * @return
     */
    private PercentageFlowVo setValue(String code,NmplCompanyInfo nmplCompanyInfo){
        DataCollectRequest dataCollectRequest = new DataCollectRequest();
        dataCollectRequest.setDeviceType(code);
        dataCollectRequest.setCompanyNetworkId(nmplCompanyInfo.getCompanyNetworkId());
        PercentageFlowVo percentageFlowVo = new PercentageFlowVo();
        percentageFlowVo.setValue(String.valueOf(getSum(dataCollectRequest)));
        percentageFlowVo.setCompanyName(nmplCompanyInfo.getCompanyName());
        percentageFlowVo.setCode(code);
        return percentageFlowVo;
    }
}
