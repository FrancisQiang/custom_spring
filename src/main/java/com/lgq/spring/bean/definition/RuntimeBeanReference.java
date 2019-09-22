package com.lgq.spring.bean.definition;

/**
 * @author lgq
 * @date 2019-09-21 22:42
 **/
public class RuntimeBeanReference {

  private String refName;

  public RuntimeBeanReference(String refName) {
    this.refName = refName;
  }

  public String getRefName() {
    return refName;
  }

  public void setRefName(String refName) {
    this.refName = refName;
  }
}
