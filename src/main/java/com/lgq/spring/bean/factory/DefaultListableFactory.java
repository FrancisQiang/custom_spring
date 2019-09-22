package com.lgq.spring.bean.factory;

import com.lgq.spring.bean.convert.IntegerTypeConvert;
import com.lgq.spring.bean.convert.TypeConvert;
import com.lgq.spring.bean.definition.BeanDefinition;
import com.lgq.spring.bean.definition.PropertyValue;
import com.lgq.spring.bean.definition.RuntimeBeanReference;
import com.lgq.spring.bean.definition.TypeStringValue;
import com.lgq.spring.bean.reader.XmlBeanDefinitionReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lgq
 * @date 2019-09-21 20:15
 **/
public class DefaultListableFactory extends AbstractBeanFactory {

  public DefaultListableFactory(String path) {
    loadBeanDefinitions(path);
    initConverters();
  }

  private void initConverters() {
    typeConvertList.add(new IntegerTypeConvert());
  }

  private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

  private Map<String, Object> singletonBeanMap = new HashMap<String, Object>();

  private List<TypeConvert> typeConvertList = new ArrayList<TypeConvert>();

  public Map<String, BeanDefinition> getBeanDefinitionMap() {
    return beanDefinitionMap;
  }

  @Override
  public Object getBean(String beanName) {
    // 根据beanName从缓存中取
    Object instance = singletonBeanMap.get(beanName);
    if (instance != null) {
      return instance;
    }
    // 根据beanName去beanDefinition集合中获取beanDefinition对象
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    // 根据beanDefinition创建Bean
    instance = createBean(beanDefinition);
    // 填充属性，从BeanDefinition中获取PropertyValue集合 解析该集合中不同类型的属性值完成属性填充
    setPropertyValue(beanDefinition, instance);
    // 对象初始化 从BeanDefinition中获取initMethod信息 根据反射调用指定的method
    initBeanInstance(beanDefinition, instance);
    return instance;
  }

  /**
   * 对象初始化
   * @param beanDefinition bean定义信息
   * @param instance bean实例对象
   */
  private void initBeanInstance(BeanDefinition beanDefinition, Object instance) {
    String initMethod = beanDefinition.getInitMethod();
    if (initMethod == null || "".equals(initMethod)) {
      return;
    }
    // 通过反射区调用初始化方法
    Class<?> clazz = instance.getClass();
    try {
      Method method = clazz.getDeclaredMethod(initMethod);
      method.invoke(instance);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 填充bean实例属性值
   * @param beanDefinition bean定义
   * @param instance 需要被填充的实例对象
   */
  private void setPropertyValue(BeanDefinition beanDefinition, Object instance) {
    String beanClassName = beanDefinition.getBeanClassName();
    Class<?> clazz = null;
    try {
      clazz = Class.forName(beanClassName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    List<PropertyValue> propertyValueList = beanDefinition.getPropertyValueList();

    // 遍历标签并赋值
    for (PropertyValue propertyValue: propertyValueList) {
      // 获取属性名称
      String name = propertyValue.getName();
      // 获取属性值(未经过处理的值，比如TypeStringValue和RuntimeBeanReference
      Object value = propertyValue.getValue();
      // 被处理过的值 能赋值到实例对象中
      Object valueToApply = null;
      // 判断value类型并转换
      if (value instanceof TypeStringValue) {
        TypeStringValue typeStringValue = (TypeStringValue) value;
        valueToApply = resolveValueByType(name, typeStringValue, clazz);
      } else if (value instanceof RuntimeBeanReference) {
        String refName = ((RuntimeBeanReference)value).getRefName();
        valueToApply = getBean(refName);
      } else {
        valueToApply = value;
      }
      setField(instance, clazz, name, valueToApply);

    }
  }

  /**
   * 设置实例对象的字段
   * @param instance 实例对象
   * @param clazz 类对象
   * @param name 字段名
   * @param valueToApply 可以直接赋值的字段属性值
   */
  private void setField(Object instance, Class<?> clazz, String name, Object valueToApply) {
    Field field;
    try {
      field = clazz.getDeclaredField(name);
      field.setAccessible(true);
      field.set(instance, valueToApply);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 处理TypeStringValue的值
   * @param name 标签属性名
   * @param typeStringValue 标签中的值
   * @param clazz
   * @return 可用包装类型
   */
  private Object resolveValueByType(String name, TypeStringValue typeStringValue,
      Class<?> clazz) {
    Object convertValue = null;
    try {
      Field field = clazz.getDeclaredField(name);
      Class<?> type = field.getType();
      if (type.isAssignableFrom(String.class)) {
        return typeStringValue.getValue();
      }
      // 进行类型转换
      for (TypeConvert typeConvert: typeConvertList) {
        if (typeConvert.handleType(type)) {
          convertValue = typeConvert.convert(typeStringValue.getValue());
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return convertValue;
  }

  /**
   * 根据beanDefinition的beanClassName创建bean实例对象
   * @param beanDefinition bean定义
   * @return bean实例对象
   */
  private Object createBean(BeanDefinition beanDefinition) {
    // 获取beanDefinition的beanClassName
    String beanClassName = beanDefinition.getBeanClassName();
    Object instance = null;
    try {
      // 根据beanClassName反射创建bean
      Class<?> clazz = Class.forName(beanClassName);
      instance = clazz.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return instance;
  }

  @Override
  public Object getBean(Class<?> beanType) {
    return super.getBean(beanType);
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
