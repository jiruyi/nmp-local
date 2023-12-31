package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.FileVersionDomainService;
import com.matrictime.network.dao.mapper.NmplVersionMapper;
import com.matrictime.network.dao.model.NmplVersion;
import com.matrictime.network.dao.model.NmplVersionExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.modelVo.NmplVersionFileVo;
import com.matrictime.network.request.UploadVersionFileReq;
import com.matrictime.network.response.VersionFileResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/3/15.
 */
@Service
public class FileVersionDomainServiceImpl implements FileVersionDomainService {

    @Resource
    private NmplVersionMapper nmplVersionMapper;

    @Override
    public int insertFileVersion(UploadVersionFileReq uploadVersionFileReq) {
        NmplVersionExample nmplVersionExample = new NmplVersionExample();
        NmplVersionExample.Criteria criteria = nmplVersionExample.createCriteria();
        criteria.andSystemTypeEqualTo(uploadVersionFileReq.getSystemType());
        criteria.andVersionNoEqualTo(uploadVersionFileReq.getVersionNo());
        criteria.andIsDeleteEqualTo(true);
        List<NmplVersion> nmplVersions = nmplVersionMapper.selectByExample(nmplVersionExample);
        if(nmplVersions.size() > NumberUtils.INTEGER_ZERO){
            throw new SystemException("此版本已经存在");
        }
        NmplVersion nmplVersion = new NmplVersion();
        BeanUtils.copyProperties(uploadVersionFileReq,nmplVersion);
        return nmplVersionMapper.insertSelective(nmplVersion);
    }

    @Transactional
    @Override
    public int updateFileVersion(UploadVersionFileReq uploadVersionFileReq) {
        NmplVersionExample nmplVersionExample = new NmplVersionExample();
        NmplVersionExample.Criteria criteria = nmplVersionExample.createCriteria();
        //原纪录逻辑删除
        NmplVersion updateNmplVersion = new NmplVersion();
        criteria.andSystemTypeEqualTo(uploadVersionFileReq.getSystemType());
        criteria.andVersionNoEqualTo(uploadVersionFileReq.getVersionNo());
        updateNmplVersion.setIsDelete(false);
        int updateFlag = nmplVersionMapper.updateByExampleSelective(updateNmplVersion, nmplVersionExample);
        //增加一条新纪录
        NmplVersion insertNmplVersion = new NmplVersion();
        BeanUtils.copyProperties(uploadVersionFileReq,insertNmplVersion);
        insertNmplVersion.setId(null);
        nmplVersionMapper.insertSelective(insertNmplVersion);
        if(updateFlag == NumberUtils.INTEGER_ONE){
            return DataConstants.FILE_UPDATE_SUCCESS;
        }
        return updateFlag;
    }

    @Override
    public int deleteFileVersion(UploadVersionFileReq uploadVersionFileReq) {
        NmplVersionExample nmplVersionExample = new NmplVersionExample();
        NmplVersionExample.Criteria criteria = nmplVersionExample.createCriteria();
        criteria.andSystemTypeEqualTo(uploadVersionFileReq.getSystemType());
        criteria.andVersionNoEqualTo(uploadVersionFileReq.getVersionNo());
        NmplVersion nmplVersion = new NmplVersion();
        nmplVersion.setIsDelete(false);
        int i = nmplVersionMapper.updateByExampleSelective(nmplVersion, nmplVersionExample);
        return i;
    }

    @Override
    public VersionFileResponse selectVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        List list = new ArrayList<>();
        VersionFileResponse versionFileResponse = new VersionFileResponse();
        NmplVersionExample nmplVersionExample = new NmplVersionExample();
        NmplVersionExample.Criteria criteria = nmplVersionExample.createCriteria();
        nmplVersionExample.setOrderByClause("update_time desc");
        if(!StringUtils.isEmpty(uploadVersionFileReq.getSystemType())){
            criteria.andSystemTypeEqualTo(uploadVersionFileReq.getSystemType());
        }
        criteria.andIsDeleteEqualTo(true);
        List<NmplVersion> nmplVersions = nmplVersionMapper.selectByExample(nmplVersionExample);
        for(NmplVersion nmplVersion : nmplVersions){
            NmplVersionFileVo nmplVersionFileVo = new NmplVersionFileVo();
            BeanUtils.copyProperties(nmplVersion,nmplVersionFileVo);
            list.add(nmplVersionFileVo);
        }
        versionFileResponse.setList(list);
        return versionFileResponse;
    }






















}
