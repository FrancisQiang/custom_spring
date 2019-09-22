package com.lgq.spring.bean.reader;

import com.lgq.spring.bean.definition.BeanDefinition;
import com.lgq.spring.bean.factory.BeanFactory;
import com.lgq.spring.bean.factory.DefaultListableFactory;

/**
 * @author lgq
 * @date 2019-09-21 20:04
 **/
public interface BeanDefinitionReader {

  void loadBeanDefinitions(String path, DefaultListableFactory defaultListableFactory);

}
