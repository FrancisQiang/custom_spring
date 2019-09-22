package com.lgq.spring.bean.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgq
 * @date 2019-09-21 19:59
 **/
public class BeanDefinition {

  private String name;

  private String beanClassName;

  private String initMethod;

  private List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

  public BeanDefinition(String beanClassName) {
    this(null, beanClassName);
  }

  public BeanDefinition(String name, String beanClassName) {
    this.name = name;
    this.beanClassName = beanClassName;
  }

  public List<PropertyValue> getPropertyValueList() {
    return propertyValueList;
  }

  public String getInitMethod() {
    return initMethod;
  }

  public void setInitMethod(String initMethod) {
    this.initMethod = initMethod;
  }

  public void setPropertyValueList(
      List<PropertyValue> propertyValueList) {
    this.propertyValueList = propertyValueList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBeanClassName() {
    return beanClassName;
  }

  public void setBeanClassName(String beanClassName) {
    this.beanClassName = beanClassName;
  }
}
