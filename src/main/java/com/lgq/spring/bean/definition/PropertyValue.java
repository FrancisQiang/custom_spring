package com.lgq.spring.bean.definition;

/**
 * 存储property标签的属性信息
 * @author lgq
 * @date 2019-09-21 22:25
 **/
public class PropertyValue {

  private String name;

  private Object value;

  public String getName() {
    return name;
  }

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
