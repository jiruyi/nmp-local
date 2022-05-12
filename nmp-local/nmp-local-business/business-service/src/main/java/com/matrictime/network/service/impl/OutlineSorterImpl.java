package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.OutlineSorterDomainService;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.modelVo.NmplOutlineSorterInfoVo;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.OutlineSorterService;
import com.matrictime.network.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Service
@Slf4j
public class OutlineSorterImpl  extends SystemBaseService implements OutlineSorterService {
    @Resource
    OutlineSorterDomainService outlineSorterDomainService;


    @Override
    public Result save(OutlineSorterReq outlineSorterReq) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = RequestContext.getUser();
            outlineSorterReq.setCreateUser(nmplUser.getNickName());
            result =  buildResult(outlineSorterDomainService.save(outlineSorterReq));
        }catch (Exception e){
            log.info("创建异常",e.getMessage());
            result = failResult(e);
        }
        return result;

    }

    @Override
    public Result modify(OutlineSorterReq outlineSorterReq) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = RequestContext.getUser();
            outlineSorterReq.setUpdateUser(nmplUser.getNickName());
            result =  buildResult(outlineSorterDomainService.modify(outlineSorterReq));
        }catch (Exception e){
            log.info("修改异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result delete(OutlineSorterReq outlineSorterReq) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = RequestContext.getUser();
            outlineSorterReq.setUpdateUser(nmplUser.getNickName());
            result =  buildResult(outlineSorterDomainService.delete(outlineSorterReq));
        }catch (Exception e){
            log.info("删除异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<PageInfo> queryByConditon(OutlineSorterReq outlineSorterReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplOutlineSorterInfoVo> pageResult =  new PageInfo<>();
            pageResult = outlineSorterDomainService.query(outlineSorterReq);
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result upload(MultipartFile file) {
        Result result;
        try {
            File tmp = new File(System.getProperty("user.dir")+File.separator+file.getName()+".csv");
            log.info(System.getProperty("user.dir")+File.separator+file.getName());
            file.transferTo(tmp);
            List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList = CsvUtils.readCsvToSorter(tmp);
            tmp.delete();
            result = buildResult(outlineSorterDomainService.batchInsert(nmplOutlineSorterInfoList));
        } catch (IOException e) {
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result auth(OutlineSorterReq outlineSorterReq) {
        Result<NmplOutlineSorterInfo> result = null;
        try {
            result = buildResult( outlineSorterDomainService.auth(outlineSorterReq));
        }catch (Exception e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    @Override
    public void download(HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            File file = new File("path");
            log.info(file.getPath());
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件后缀名：" + ext);

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
