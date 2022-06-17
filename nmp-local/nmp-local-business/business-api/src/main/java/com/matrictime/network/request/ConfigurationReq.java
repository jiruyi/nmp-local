package com.matrictime.network.request;

import com.matrictime.network.modelVo.ConfigurationVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConfigurationReq  implements Serializable {
   List<ConfigurationVo> configurationVoList = new ArrayList<>();

   /**
    * 设备编号
    */
   private String deviceId;

   /**
    * 内网Ip
    */
   private String realIp;

   /**
    * 内网端口
    */
   private String realPort;

   /**
    * 路径
    */
   private String url;

   /**
    * 路径类型
    */
   private String type;
}
