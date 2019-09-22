package com.lgq.spring.bean.convert;

/**
 * @author lgq
 * @date 2019-09-22 20:53
 **/
public class IntegerTypeConvert implements TypeConvert {

  public Object convert(String value) {
    return Integer.parseInt(value);
  }

  public boolean handleType(Class<?> type) {
    return type.isAssignableFrom(Integer.class);
  }
}
