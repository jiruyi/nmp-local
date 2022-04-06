package com.matrictime.network.service.impl;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.domain.GroupDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GroupServiceImpl extends SystemBaseService implements GroupService {
    @Autowired
    GroupDomainService groupDomainService;


    @Override
    public Result<Integer> createGroup(GroupReq groupReq) {
        Result<Integer> result;
        try {
            result = buildResult(groupDomainService.createGroup(groupReq));
        }catch (Exception e){
            log.info("组创建异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    @Override
    public Result<Integer> modifyGroup(GroupReq groupReq) {
        Result<Integer> result;
        try {
            result = buildResult(groupDomainService.modifyGroup(groupReq));
        }catch (Exception e){
            log.info("组修改异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<Integer> deleteGroup(GroupReq groupReq) {
        Result<Integer> result;
        try {
            result = buildResult(groupDomainService.deleteGroup(groupReq));
        }catch (Exception e){
            log.info("组删除异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<GroupResp> queryGroup(GroupReq groupReq) {
        Result<GroupResp> result;
        try {
            result = buildResult(groupDomainService.queryGroup(groupReq));
        }catch (Exception e){
            log.info("组查询异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

}
