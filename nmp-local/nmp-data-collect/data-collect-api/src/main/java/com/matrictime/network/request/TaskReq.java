package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class TaskReq implements Serializable {

    private static final long serialVersionUID = 1255426007241953597L;

    private List<String> codeList = new ArrayList<>();

    private  long timer;
}
