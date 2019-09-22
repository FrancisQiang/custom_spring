package com.lgq.spring.bean.convert;

/**
 * @author lgq
 * @date 2019-09-22 20:52
 **/
public interface TypeConvert {

  Object convert(String value);

  boolean handleType(Class<?> type);
}
