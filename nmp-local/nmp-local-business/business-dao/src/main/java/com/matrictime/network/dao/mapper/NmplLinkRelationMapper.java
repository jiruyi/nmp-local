package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.NmplLinkRelation;
import com.matrictime.network.dao.model.NmplLinkRelationExample;
import java.util.List;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import org.apache.ibatis.annotations.Param;

public interface NmplLinkRelationMapper {
    long countByExample(NmplLinkRelationExample example);

    int deleteByExample(NmplLinkRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NmplLinkRelation record);

    int insertSelective(NmplLinkRelation record);

    List<NmplLinkRelation> selectByExample(NmplLinkRelationExample example);

    NmplLinkRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NmplLinkRelation record, @Param("example") NmplLinkRelationExample example);

    int updateByExample(@Param("record") NmplLinkRelation record, @Param("example") NmplLinkRelationExample example);

    int updateByPrimaryKeySelective(NmplLinkRelation record);

    int updateByPrimaryKey(NmplLinkRelation record);

    int insertLinkRelation(LinkRelationRequest linkRelationRequest);

    int deleteLinkRelation(LinkRelationRequest linkRelationRequest);

    int updateLinkRelation(LinkRelationRequest linkRelationRequest);

    List<LinkRelationVo> selectLinkRelation(LinkRelationRequest linkRelationRequest);

    List<BaseStationInfoVo> selectLinkRelationStation(BaseStationInfoRequest baseStationInfoRequest);

    List<DeviceInfoVo> selectLinkRelationDevice(DeviceInfoRequest deviceInfoRequest);

    List<LinkRelationVo> query(LinkRelationRequest linkRelationRequest);

    List<LinkRelationVo> selectById(LinkRelationRequest linkRelationRequest);
}