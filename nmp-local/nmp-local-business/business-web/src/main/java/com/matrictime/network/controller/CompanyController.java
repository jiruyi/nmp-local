package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域管理接口
 * @author zhangyunjie
 */
@RequestMapping(value = "/company")
@Api(value = "区域管理",tags = "区域管理")
@RestController
@Slf4j
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * 运营商创建接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "运营商创建接口",notes = "运营商创建")
    @RequestMapping(value = "/saveOperator",method = RequestMethod.POST)
    @RequiresPermissions("sys:operator:save")
    @SystemLog(opermodul = "运营商管理模块",operDesc = "创建运营商",operType = "创建")
    public Result saveOperator(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("00");
        return companyService.save(companyInfoRequest);
    }

    /**
     * 运营商编辑接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "运营商编辑接口",notes = "运营商编辑")
    @RequestMapping(value = "/modifyOperator",method = RequestMethod.POST)
    @RequiresPermissions("sys:operator:update")
    @SystemLog(opermodul = "运营商管理模块",operDesc = "修改运营商",operType = "修改")
    public Result modifyOperator(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("00");
        return companyService.modify(companyInfoRequest);
    }


    /**
     * 运营商删除接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "运营商删除接口",notes = "运营商删除")
    @RequestMapping(value = "/deleteOperator",method = RequestMethod.POST)
    @RequiresPermissions("sys:operator:delete")
    @SystemLog(opermodul = "运营商管理模块",operDesc = "删除运营商",operType = "删除")
    public Result deleteOperator(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("00");
        return companyService.delete(companyInfoRequest);
    }

    /**
     * 运营商查询接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "运营商查询接口",notes = "运营商查询")
    @RequestMapping(value = "/queryOperator",method = RequestMethod.POST)
    @RequiresPermissions("sys:operator:query")
    @SystemLog(opermodul = "运营商管理模块",operDesc = "查询运营商",operType = "查询")
    public Result queryOperator(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("00");
        return companyService.queryOperatorByConditon(companyInfoRequest);
    }

    //---------------------------大区------------------------------------------

    /**
     * 大区创建接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "大区创建接口",notes = "大区创建")
    @RequestMapping(value = "/saveRegion",method = RequestMethod.POST)
    @RequiresPermissions("sys:region:save")
    @SystemLog(opermodul = "大区管理模块",operDesc = "创建大区",operType = "创建")
    public Result saveRegion(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("01");
        return companyService.save(companyInfoRequest);
    }

    /**
     * 大区编辑接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "大区编辑接口",notes = "大区编辑")
    @RequestMapping(value = "/modifyRegion",method = RequestMethod.POST)
    @RequiresPermissions("sys:region:update")
    @SystemLog(opermodul = "大区管理模块",operDesc = "修改大区",operType = "修改")
    public Result modifyRegion(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("01");
        return companyService.modify(companyInfoRequest);
    }


    /**
     * 大区删除接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "大区删除接口",notes = "大区删除")
    @RequestMapping(value = "/deleteRegion",method = RequestMethod.POST)
    @RequiresPermissions("sys:region:delete")
    @SystemLog(opermodul = "大区管理模块",operDesc = "删除大区",operType = "删除")
    public Result deleteRegion(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("01");
        return companyService.delete(companyInfoRequest);
    }

    /**
     * 大区查询接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "大区查询接口",notes = "大区查询")
    @RequestMapping(value = "/queryRegion",method = RequestMethod.POST)
    @RequiresPermissions("sys:region:query")
    @SystemLog(opermodul = "大区管理模块",operDesc = "查询大区",operType = "查询")
    public Result queryRegion(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("01");
        return companyService.queryByConditon(companyInfoRequest);
    }

    //-------------------------小区--------------------------------------------

    /**
     * 小区创建接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "小区创建接口",notes = "小区创建")
    @RequestMapping(value = "/saveVillage",method = RequestMethod.POST)
    @RequiresPermissions("sys:village:save")
    @SystemLog(opermodul = "小区管理模块",operDesc = "创建小区",operType = "创建")
    public Result saveVillage(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("02");
        return companyService.save(companyInfoRequest);
    }

    /**
     * 小区编辑接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "小区编辑接口",notes = "小区编辑")
    @RequestMapping(value = "/modifyVillage",method = RequestMethod.POST)
    @RequiresPermissions("sys:village:update")
    @SystemLog(opermodul = "小区管理模块",operDesc = "修改小区",operType = "修改")
    public Result modifyVillage(@RequestBody CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("02");
        return companyService.modify(companyInfoRequest);
    }


    /**
     * 小区删除接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "小区删除接口",notes = "小区删除")
    @RequestMapping(value = "/deleteVillage",method = RequestMethod.POST)
    @RequiresPermissions("sys:village:delete")
    @SystemLog(opermodul = "小区管理模块",operDesc = "删除小区",operType = "删除")
    public Result deleteVillage(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("02");
        return companyService.delete(companyInfoRequest);
    }

    /**
     * 小区查询接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "小区查询接口",notes = "小区查询")
    @RequestMapping(value = "/queryVillage",method = RequestMethod.POST)
    @RequiresPermissions("sys:village:query")
    @SystemLog(opermodul = "小区管理模块",operDesc = "查询小区",operType = "查询")
    public Result queryVillage(@RequestBody  CompanyInfoRequest companyInfoRequest){
        companyInfoRequest.setCompanyType("02");
        return companyService.queryByConditon(companyInfoRequest);
    }



    /**
     * 查询所有运营商大区小区接口
     * @param companyInfoRequest
     * @return
     */
    @ApiOperation(value = "区域查询接口",notes = "小区查询")
    @RequestMapping(value = "/queryCompanyList",method = RequestMethod.POST)
    public Result queryCompanyList(@RequestBody  CompanyInfoRequest companyInfoRequest){
        return companyService.queryCompanyList(companyInfoRequest);
    }


}
