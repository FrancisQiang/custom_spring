package com.lgq.spring.bean.factory;

import com.lgq.spring.bean.definition.BeanDefinition;
import com.lgq.spring.bean.reader.XmlBeanDefinitionReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgq
 * @date 2019-09-21 20:15
 **/
public class DefaultListableFactory extends AbstractBeanFactory {

  public DefaultListableFactory(String path) {
    loadBeanDefinitions(path);
  }

  private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

  public Map<String, BeanDefinition> getBeanDefinitionMap() {
    return beanDefinitionMap;
  }

  private void loadBeanDefinitions(String path) {
    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
    // 加载beanDefinition并注册给工厂
    xmlBeanDefinitionReader.loadBeanDefinitions(path, this);
  }



  public void registerBeanDefinition( String beanName, BeanDefinition beanDefinition) {
    beanDefinitionMap.put(beanName, beanDefinition);
  }

}
