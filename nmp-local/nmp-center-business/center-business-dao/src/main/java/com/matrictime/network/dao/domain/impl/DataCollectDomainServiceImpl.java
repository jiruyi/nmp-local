package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.extend.DataCollectExtMapper;
import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplDataCollectExample;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DataTimeVo;
import com.matrictime.network.request.DataCollectRequest;
import com.matrictime.network.response.DataCollectResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
        Double sum = 0d;
        for(NmplDataCollect dataCollect: nmplDataCollects){
            sum = sum + Double.parseDouble(dataCollect.getSumNumber());
        }
        return sum;
    }

    /**
     * 负载数据插入
     * @param dataCollectResponse
     * @return
     */
    @Transactional
    @Override
    public int insertDataCollect(DataCollectResponse dataCollectResponse) {
        List<DataCollectVo> list = dataCollectResponse.getList();
        int i = 0;
        for(DataCollectVo dataCollectVo: list){
            NmplDataCollect dataCollect = new NmplDataCollect();
            BeanUtils.copyProperties(dataCollectVo,dataCollect);
            i = dataCollectMapper.insertSelective(dataCollect);
        }
        return i;
    }

    /**
     * 查询单个小区流量
     * @param dataCollectRequest
     * @return
     */
    @Override
    public Double selectCompanyData(DataCollectRequest dataCollectRequest) {
        NmplDataCollectExample dataCollectExample = new NmplDataCollectExample();
        NmplDataCollectExample.Criteria criteria = dataCollectExample.createCriteria();
        criteria.andCompanyNetworkIdEqualTo(dataCollectRequest.getCompanyNetworkId());
        criteria.andDataItemCodeEqualTo(dataCollectRequest.getDataItemCode());
        dataCollectExample.setOrderByClause("upload_time desc");
        List<NmplDataCollect> nmplDataCollects = dataCollectMapper.selectByExample(dataCollectExample);
        if(CollectionUtils.isEmpty(nmplDataCollects)){
            return 0d;
        }
        return Double.parseDouble(nmplDataCollects.get(0).getSumNumber());
    }
}
