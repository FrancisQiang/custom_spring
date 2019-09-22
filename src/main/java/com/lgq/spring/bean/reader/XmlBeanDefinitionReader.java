package com.lgq.spring.bean.reader;

import com.lgq.spring.bean.factory.DefaultListableFactory;
import com.lgq.spring.bean.parser.BeanDefinitionParser;
import com.lgq.spring.bean.resource.ClasspathResource;
import java.io.InputStream;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 通过读取xml中的bean标签，获取BeanDefinition对象
 * @author lgq
 * @date 2019-09-21 20:02
 **/
public class XmlBeanDefinitionReader implements BeanDefinitionReader{

  public void loadBeanDefinitions(String path, DefaultListableFactory defaultListableFactory) {
    // 获取指定资源
    ClasspathResource classpathResource = new ClasspathResource(path);
    // 创建document阅读器
    InputStream inputStream = classpathResource.getInputStream();
    DocumentReader documentReader = new DefaultDocumentReader();
    // 通过document阅读器读取资源流获取Document对象
    Document document = documentReader.loadDocument(inputStream);
    // 通过Document对象获取更标签内容(这里封装为dom4j的Element对象)
    Element rootElement = document.getRootElement();
    // 创建BeanDefinition解析器
    BeanDefinitionParser parser = new BeanDefinitionParser(defaultListableFactory);
    // 通过BeanDefinition解析器解析根标签
    parser.parseRootElement(rootElement);
  }
}
