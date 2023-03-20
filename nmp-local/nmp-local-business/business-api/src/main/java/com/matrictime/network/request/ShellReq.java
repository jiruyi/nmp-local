package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShellReq {
    List<String> commands;
    String path;
}
