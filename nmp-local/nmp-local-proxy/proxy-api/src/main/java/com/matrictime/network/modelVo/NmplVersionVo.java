package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class NmplVersionVo implements Serializable {

    private static final long serialVersionUID = -307171983040045883L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 系统标识(00:基站 01:分发机 02:生成机)
     */
    private String systemId;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 版本描述
     */
    private String versionDesc;

    /**
     * 描述
     */
    private String remark;

    /**
     * 1存在 0删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    private String createUser;

    /**
     *
     */
    private String updateUser;

    /**
     * 版本文件
     */
    private List<NmplVersionFileVo> nmplVersionFileVos;
}
