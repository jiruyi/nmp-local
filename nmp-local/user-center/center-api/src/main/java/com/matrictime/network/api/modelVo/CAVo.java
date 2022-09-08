package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CAVo implements Serializable {

    private static final long serialVersionUID = 227952989128141211L;

    private String content_type;
    private String contents;
    private PublicDataVo public_data;
    private SignArgsVo sign_args;
    private String txn_id;
}
