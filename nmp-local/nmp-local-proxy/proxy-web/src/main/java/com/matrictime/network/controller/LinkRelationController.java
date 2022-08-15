package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.service.LinkRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 链路模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/linkRelation",method = RequestMethod.POST)
@Slf4j
public class LinkRelationController extends SystemBaseService {

    @Autowired
    private LinkRelationService linkRelationService;


    /**
     * 链路插入
     * @param req
     * @return
     */

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result addLinkRelation(@RequestBody LinkRelationVo req){
        Result result;
        try {
            List<LinkRelationVo> voList = new ArrayList<>();
            voList.add(req);
            result = linkRelationService.addLinkRelation(voList);
        }catch (Exception e){
            log.info("LinkRelationController.addLinkRelation{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 链路更新
     * @param req
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateLinkRelation(@RequestBody LinkRelationVo req){
        Result result;
        try {
            List<LinkRelationVo> voList = new ArrayList<>();
            voList.add(req);
            result = linkRelationService.updateLinkRelation(voList);
        }catch (Exception e){
            log.info("LinkRelationController.updateLinkRelation{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


}