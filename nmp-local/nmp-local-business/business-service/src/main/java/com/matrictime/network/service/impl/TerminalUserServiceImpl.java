package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.request.TerminalUserResquest;
import com.matrictime.network.response.TerminalUserCountResponse;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.TerminalUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Service
@Slf4j
public class TerminalUserServiceImpl implements TerminalUserService {

    @Resource
    private TerminalUserDomainService terminalUserDomainService;

    /**
     * 更新用户状态
     * @param terminalUserResponse
     * @return
     */
    @Transactional
    @Override
    public Result<Integer> updateTerminalUser(TerminalUserResponse terminalUserResponse) {
        Result<Integer> result = new Result<>();
        int i = 0;
        List<TerminalUserVo> list = terminalUserResponse.getList();
        if(CollectionUtils.isEmpty(list)){
            return new Result<>(false,"上报数据为空");
        }
        for(TerminalUserVo terminalUserVo: list){
            TerminalUserResquest terminalUserResquest = new TerminalUserResquest();
            BeanUtils.copyProperties(terminalUserVo,terminalUserResquest);
//            //查询该用户是否已经在基站下
//            List<TerminalUserVo> terminalUserVoList = terminalUserDomainService.selectTerminalUser(terminalUserResquest);
            i = terminalUserDomainService.insertTerminalUser(terminalUserResquest);

        }
        result.setResultObj(i);
        result.setSuccess(true);
        return result;
    }

    /**
     * 查询各个用户总数
     * @param terminalUserResquest
     * @return
     */
    @Override
    public Result<TerminalUserCountResponse> countTerminalUser(TerminalUserResquest terminalUserResquest) {
        Result<TerminalUserCountResponse> result = new Result<>();
        TerminalUserCountResponse terminalUserCountResponse = new TerminalUserCountResponse();
        try {
            terminalUserCountResponse.setTerminalUserCount(terminalUserDomainService.countTerminalUser(terminalUserResquest));
            result.setResultObj(terminalUserCountResponse);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("countTerminalUser:{}",e.getMessage());
        }
        return result;
    }
}
