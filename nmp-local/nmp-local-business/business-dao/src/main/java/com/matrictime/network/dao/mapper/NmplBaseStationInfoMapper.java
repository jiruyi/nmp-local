package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import java.util.List;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import org.apache.ibatis.annotations.Param;

public interface NmplBaseStationInfoMapper {
    long countByExample(NmplBaseStationInfoExample example);

    int deleteByExample(NmplBaseStationInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplBaseStationInfo record);

    int insertSelective(NmplBaseStationInfo record);

    List<NmplBaseStationInfo> selectByExample(NmplBaseStationInfoExample example);

    NmplBaseStationInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplBaseStationInfo record, @Param("example") NmplBaseStationInfoExample example);

    int updateByExample(@Param("record") NmplBaseStationInfo record, @Param("example") NmplBaseStationInfoExample example);

    int updateByPrimaryKeySelective(NmplBaseStationInfo record);

    int updateByPrimaryKey(NmplBaseStationInfo record);

    int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectBaseStationBatch(@Param("stationId") List<String> list );

    StationVo selectDeviceId(BaseStationInfoRequest baseStationInfoRequest);

}