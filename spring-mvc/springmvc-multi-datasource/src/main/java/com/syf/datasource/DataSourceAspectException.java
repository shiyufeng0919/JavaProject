package com.syf.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* 自定义异常类
* */
public class DataSourceAspectException extends Exception{

    private static final Logger LOGGER= LoggerFactory.getLogger(DataSourceAspect.class);

    public DataSourceAspectException(){}

    public DataSourceAspectException(String msg, Throwable e){
        super(msg,e);
        LOGGER.error(msg);
    }
}
