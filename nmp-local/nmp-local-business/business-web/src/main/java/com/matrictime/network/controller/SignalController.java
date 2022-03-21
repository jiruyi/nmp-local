package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplSignalVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.SignalService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/signal")
@RestController
@Slf4j
/**
 * 信令相关请求接口
 */
public class SignalController {

    @Autowired
    private SignalService signalService;

    /**
     * 信令追踪启停
     * @param req
     * @return
     */
    @RequestMapping (value = "/signalIo",method = RequestMethod.POST)
    @SystemLog(opermodul = "信令模块",operDesc = "信令启停",operType = "操作")
    public Result<SignalIoResp> signalIo(@RequestBody SignalIoReq req){
        try {
            return signalService.signalIo(req);
        }catch (Exception e){
            log.error("SignalController.signalIo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 信令上报
     * @param req
     * @return
     */
    @RequestMapping (value = "/addSignal",method = RequestMethod.POST)
    @SystemLog(opermodul = "信令模块",operDesc = "信令上报",operType = "操作")
    public Result addSignal(@RequestBody NmplSignalVo req){
        try {
            EditSignalReq signalReq = new EditSignalReq();
            List<NmplSignalVo> vos = new ArrayList<>(1);
            vos.add(req);
            signalReq.setEditType(DataConstants.EDIT_TYPE_ADD);
            signalReq.setNmplSignalVos(vos);
            return  signalService.editSignal(signalReq);
        }catch (Exception e){
            log.error("SignalController.addSignal exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 信令清空
     * @param req
     * @return
     */
    @RequestMapping (value = "/cleanSignal",method = RequestMethod.POST)
    @SystemLog(opermodul = "信令模块",operDesc = "信令清空",operType = "操作")
    public Result<CleanSignalResp> cleanSignal(@RequestBody CleanSignalReq req){
        try {
            return  signalService.cleanSignal(req);
        }catch (Exception e){
            log.error("SignalController.cleanSignal exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 信令轮询分页查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/querySignalByPage",method = RequestMethod.POST)
    @SystemLog(opermodul = "信令模块",operDesc = "信令轮询分页查询",operType = "查询")
    public Result<QuerySignalByPageResp> querySignalByPage(@RequestBody QuerySignalByPageReq req){
        try {
            return  signalService.querySignalByPage(req);
        }catch (Exception e){
            log.error("SignalController.querySignalByPage exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 信令导出
     * @param req
     * @return
     */
    @RequestMapping (value = "/exportSignal",method = RequestMethod.POST)
    @SystemLog(opermodul = "信令模块",operDesc = "信令导出",operType = "操作")
    public Result<ExportSignalResp> exportSignal(@RequestBody ExportSignalReq req, HttpServletResponse response){
        try {
            String fileName = DateUtils.dateToString(new Date())+DataConstants.FILE_TYPE_CSV;
            Result<ExportSignalResp> result = signalService.exportSignal(req);
            if (result!=null && result.isSuccess()){
                responseSetProperties(fileName,result.getResultObj().getBytes(), response);
            }
            return result;
        }catch (Exception e){
            log.error("SignalController.exportSignal exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 设置下载响应
     * @param fileName
     * @param bytes
     * @param response
     */
    public static void responseSetProperties(String fileName, byte[] bytes, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            response.setContentType("application/csv");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=30");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            log.error("SignalController.iostream IOException:{}", e.getMessage());
        }
    }
}
