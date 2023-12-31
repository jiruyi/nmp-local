package com.matrictime.network.interceptor;


import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;


/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/10/8 0008 18:04
 * @desc
 */

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
),@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class CatMybatisPlugin   implements Interceptor {


    //缓存，提高性能
    private static final Map<String, String> sqlURLCache = new ConcurrentHashMap<String, String>(256);

    private static final String EMPTY_CONNECTION = "jdbc:mysql://192.168.72.14:3306/nmp_local";

    private Executor target;

    // druid 数据源的类名称
    private static final String DruidDataSourceClassName = "com.alibaba.druid.pool.DruidDataSource";
    // dbcp 数据源的类名称
    private static final String DBCPBasicDataSourceClassName = "org.apache.commons.dbcp.BasicDataSource";
    // dbcp2 数据源的类名称
    private static final String DBCP2BasicDataSourceClassName = "org.apache.commons.dbcp2.BasicDataSource";
    // c3p0 数据源的类名称
    private static final String C3P0ComboPooledDataSourceClassName = "com.mchange.v2.c3p0.ComboPooledDataSource";
    // HikariCP 数据源的类名称
    private static final String HikariCPDataSourceClassName = "com.zaxxer.hikari.HikariDataSource";
    // BoneCP 数据源的类名称
    private static final String BoneCPDataSourceClassName = "com.jolbox.bonecp.BoneCPDataSource";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //得到类名，方法
        String[] strArr = mappedStatement.getId().split("\\.");
        String methodName = strArr[strArr.length - 2] + "." + strArr[strArr.length - 1];

        com.dianping.cat.message.Transaction t = Cat.newTransaction("SQL", methodName);

        //得到sql语句
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = showSql(configuration, boundSql);

        //获取SQL类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Cat.logEvent("SQL.Method", sqlCommandType.name().toLowerCase(), Message.SUCCESS, sql);

        String s = this.getSQLDatabase();
        Cat.logEvent("SQL.Database", s);

        Object returnObj = null;
        try {
            returnObj = invocation.proceed();
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            t.complete();
        }

        return returnObj;
    }

    private DataSource getDataSource() {
        org.apache.ibatis.transaction.Transaction transaction = this.target.getTransaction();
        if (transaction == null) {
            return null;
        }
        if (transaction instanceof SpringManagedTransaction) {
            String fieldName = "dataSource";
            Field field = ReflectionUtils.findField(transaction.getClass(), fieldName, DataSource.class);

            if (field == null) {

                return null;
            }

            ReflectionUtils.makeAccessible(field);
            DataSource dataSource = (DataSource) ReflectionUtils.getField(field, transaction);
            return dataSource;
        }

        return null;
    }



    /**
     * 重写 getSqlURL 方法
     *
     * @author fanlychie (https://github.com/fanlychie)
     */
    private String getSqlURL() {
        // 客户端使用的数据源
        DataSource dataSource = this.getDataSource();
        if (dataSource != null) {
            // 处理常见的数据源
            switch (dataSource.getClass().getName()) {
                // druid
                case DruidDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, DruidDataSourceClassName, "getUrl");
                // dbcp
                case DBCPBasicDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, DBCPBasicDataSourceClassName, "getUrl");
                // dbcp2
                case DBCP2BasicDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, DBCP2BasicDataSourceClassName, "getUrl");
                // c3p0
                case C3P0ComboPooledDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, C3P0ComboPooledDataSourceClassName, "getJdbcUrl");
                // HikariCP
                case HikariCPDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, HikariCPDataSourceClassName, "getJdbcUrl");
                // BoneCP
                case BoneCPDataSourceClassName:
                    return getDataSourceSqlURL(dataSource, BoneCPDataSourceClassName, "getJdbcUrl");
            }
        }
        return null;
    }

    /**
     * 获取数据源的SQL地址
     *
     * @param dataSource                 数据源
     * @param runtimeDataSourceClassName 运行时真实的数据源的类名称
     * @param sqlURLMethodName           获取SQL地址的方法名称
     * @author fanlychie (https://github.com/fanlychie)
     */
    private String getDataSourceSqlURL(DataSource dataSource, String runtimeDataSourceClassName, String sqlURLMethodName) {
        Class<?> dataSourceClass = null;
        try {
            dataSourceClass = Class.forName(runtimeDataSourceClassName);
        } catch (ClassNotFoundException e) {
        }
        Method sqlURLMethod = ReflectionUtils.findMethod(dataSourceClass, sqlURLMethodName);
        return (String) ReflectionUtils.invokeMethod(sqlURLMethod, dataSource);
    }

    private String getSQLDatabase() {
        //根据设置的多数据源修改此处,获取dbname
        String dbName = null;
        if (dbName == null) {
            dbName = "DEFAULT";
        }
        String url = CatMybatisPlugin.sqlURLCache.get(dbName);
        if (url != null) {
            return url;
        }

        //目前监控只支持mysql ,其余数据库需要各自修改监控服务端
        url = this.getSqlURL();
        if (url == null) {
            url = String.format(EMPTY_CONNECTION, dbName);
        }
        CatMybatisPlugin.sqlURLCache.put(dbName, url);
        return url;
    }

    /**
     * 解析sql语句
     *
     * @param configuration
     * @param boundSql
     * @return
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 参数解析
     *
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            this.target = (Executor) target;
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }


}
