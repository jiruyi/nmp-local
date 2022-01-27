package com.matrictime.network.interceptor;

import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.matrictime.network.base.SystemBaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/10/11 0011 9:18
 * @desc
 */
@Configuration
public class DataSourceConfigure {
//
//    @Bean
//    public DruidDataSource getDruidDataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        try {
//            dataSource.setFilters("stat,sl4j");
//            dataSource.setUseGlobalDataSourceStat(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
//
//    @Bean
//    public StatFilter getStatFilter(){
//        StatFilter statFilter = new StatFilter();
//        statFilter.setSlowSqlMillis(5000);
//        statFilter.setLogSlowSql(true);
//        return statFilter;
//    }

    @Bean("druidStatInterceptor")
    public DruidStatInterceptor getDruidStatInterceptor(){
        DruidStatInterceptor druidStatInterceptor = new DruidStatInterceptor();
        return druidStatInterceptor;
    }
    @Bean
    public BeanTypeAutoProxyCreator getBeanTypeAutoProxyCreator(){
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(SystemBaseService.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanTypeAutoProxyCreator;
    }
}
