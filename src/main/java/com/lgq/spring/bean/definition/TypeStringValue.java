package com.lgq.spring.bean.definition;

/**
 * @author lgq
 * @date 2019-09-21 22:39
 **/
public class TypeStringValue {

  private String value;

  private Object targetType;

  public TypeStringValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Object getTargetType() {
    return targetType;
  }

  public void setTargetType(Object targetType) {
    this.targetType = targetType;
  }
}
