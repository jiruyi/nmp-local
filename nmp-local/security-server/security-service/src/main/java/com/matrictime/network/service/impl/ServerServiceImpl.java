package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServerServiceImpl extends SystemBaseService implements ServerService {


    @Override
    public Result getStatus() {
        Result result = null;
        try {

        }catch (SystemException e){
            log.error("ServerServiceImpl.getServerStatus SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.getServerStatus Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result start() {
        Result result = null;
        try {

        }catch (SystemException e){
            log.error("ServerServiceImpl.start SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.start Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result getStartStatus() {
        Result result;
        try {
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.getStartStatus SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.getStartStatus Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}
