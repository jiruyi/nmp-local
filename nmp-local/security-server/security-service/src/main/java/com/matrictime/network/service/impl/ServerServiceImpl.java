package com.matrictime.network.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.EditServerReq;
import com.matrictime.network.req.QueryServerReq;
import com.matrictime.network.req.StartServerReq;
import com.matrictime.network.service.ServerService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.EDIT_TYPE_UPD;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Service
@Slf4j
public class ServerServiceImpl extends SystemBaseService implements ServerService {

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    /**
     * 查询安全服务器列表
     * @return
     */
    @Override
    public Result<PageInfo<SecurityServerInfoVo>> queryServer(QueryServerReq req){
        Result result;
        try {

            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据条件查询密钥中心分配列表
            NmpsSecurityServerInfoExample example = new NmpsSecurityServerInfoExample();
            NmpsSecurityServerInfoExample.Criteria criteria = example.createCriteria();
            criteria.andIsExistEqualTo(IS_EXIST);
            List<NmpsSecurityServerInfo> serverInfos = serverInfoMapper.selectByExample(example);
            List<SecurityServerInfoVo> serverInfoVos = new ArrayList<>();
            for (NmpsSecurityServerInfo info : serverInfos){
                SecurityServerInfoVo vo = new SecurityServerInfoVo();
                BeanUtils.copyProperties(info,vo);
                serverInfoVos.add(vo);
            }

            PageInfo<SecurityServerInfoVo> pageResult = new PageInfo<>();
            pageResult.setList(serverInfoVos);
            pageResult.setCount((int) page.getTotal());
            pageResult.setPages(page.getPages());
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("ServerServiceImpl.queryServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.queryServer Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result editServer(EditServerReq req){
        Result result;
        try {
            // check param is legal
            checkEditServerParam(req);
            switch (req.getEditType()){
                // 批量插入
                case DataConstants.EDIT_TYPE_ADD:
                    for (SecurityServerInfoVo vo : req.getSecurityServerInfoVos()){
                        NmpsSecurityServerInfo serverInfo = new NmpsSecurityServerInfo();
                        BeanUtils.copyProperties(vo,serverInfo);
                        serverInfoMapper.insertSelective(serverInfo);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD://批量修改
                case DataConstants.EDIT_TYPE_DEL:// 逻辑删除
                    for (SecurityServerInfoVo vo : req.getSecurityServerInfoVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("SecurityServerInfoVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmpsSecurityServerInfo serverInfo = serverInfoMapper.selectByPrimaryKey(vo.getId());
                        if (serverInfo == null){
                            throw new Exception("serverInfo"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }
                        NmpsSecurityServerInfo server = new NmpsSecurityServerInfo();
                        BeanUtils.copyProperties(vo,server);
                        serverInfoMapper.updateByPrimaryKeySelective(server);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.editServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.editServer Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result startServer(StartServerReq req){
        Result result;
        try {
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.startServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.startServer Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private void checkEditServerParam(EditServerReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

    }


}
