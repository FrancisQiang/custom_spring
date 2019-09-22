package com.lgq.spring.bean.parser;

import com.lgq.spring.bean.definition.BeanDefinition;
import com.lgq.spring.bean.definition.PropertyValue;
import com.lgq.spring.bean.definition.RuntimeReference;
import com.lgq.spring.bean.definition.TypeStringValue;
import com.lgq.spring.bean.factory.BeanFactory;
import com.lgq.spring.bean.factory.DefaultListableFactory;
import java.util.List;
import org.dom4j.Element;

/**
 * @author lgq
 * @date 2019-09-21 19:57
 **/
public class BeanDefinitionParser {

  private DefaultListableFactory defaultListableFactory;

  public BeanDefinitionParser(DefaultListableFactory defaultListableFactory) {
    this.defaultListableFactory = defaultListableFactory;
  }

  /**
   * 解析beans根标签
   * @param rootElement 根标签Element对象
   */
  public void parseRootElement(Element rootElement) {
    if (rootElement == null) {
      return;
    }
    // 获取所有的bean标签
    List<Element> elements = rootElement.elements();
    for (Element element : elements) {
      // 解析bean标签
      parseBeanElement(element);
    }
  }

  @SuppressWarnings("unchecked")
  private void parseBeanElement(Element element) {
    if (element == null) {
      return;
    }
    // 获取id属性
    String id = element.attributeValue("id");
    // 获取class属性
    String clazz = element.attributeValue("class");

    String beanName = id;
    BeanDefinition beanDefinition = new BeanDefinition(beanName, clazz);

    // 获取property子标签
    List<Element> propertyElements = element.elements();
    for (Element propertyElement : propertyElements) {
      parsePropertyElement(beanDefinition, propertyElement);
    }

    // 注册BeanDefinition
    registerBeanDefinition(beanName, beanDefinition);
  }

  /**
   * 解析property子标签
   * @param propertyElement property标签Element对象
   */
  private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
    if (propertyElement == null) {
      return;
    }
    // 获取name标签值
    String name = propertyElement.attributeValue("name");
    // 获取value值
    String value = propertyElement.attributeValue("value");
    // 获取ref标签值
    String ref = propertyElement.attributeValue("ref");

    if (value != null && !"".equals(value) && ref != null && !"".equals(ref)) {
      return;
    }

    if (value != null && !"".equals(value)) {
      // 存储类型
      TypeStringValue typeStringValue = new TypeStringValue(value);
      beanDefinition.getPropertyValueList().add(new PropertyValue(name, typeStringValue));
    } else if (ref != null && !"".equals(ref)) {
      RuntimeReference runtimeReference = new RuntimeReference(value);
      beanDefinition.getPropertyValueList().add(new PropertyValue(name, runtimeReference));
    }
  }

  private void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    defaultListableFactory.registerBeanDefinition(beanName, beanDefinition);
  }

}
