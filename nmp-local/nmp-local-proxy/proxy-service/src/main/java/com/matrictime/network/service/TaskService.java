package com.matrictime.network.service;

public interface TaskService {

    void heartReport(String url);

    void logPush(String url);

    void pcData(String url);

    void dataCollectPush(String url);

}
