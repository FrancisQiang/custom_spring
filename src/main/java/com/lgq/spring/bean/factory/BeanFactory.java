package com.lgq.spring.bean.factory;

/**
 * @author lgq
 * @date 2019-09-21 20:14
 **/
public interface BeanFactory {

  Object getBean(String beanName);

  Object getBean(Class<?> beanType);

}
